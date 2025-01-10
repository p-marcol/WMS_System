import axios from 'axios'

const baseUrl = 'https://localhost:443'

const instance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-type': 'application/json',
    },
})

instance.interceptors.request.use(
    (config) => {
        if (config._retry) {
            config.headers.Authorization = localStorage.getItem('token')
                ? `Bearer ${localStorage.getItem('token')}`
                : null
        } else {
            config.headers.Authorization = localStorage.getItem('refresh')
                ? `Bearer ${localStorage.getItem('refresh')}`
                : null
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

instance.interceptors.response.use(
    (response) => {
        return response
    },
    async (error) => {
        // Add _retry property to the request config to prevent infinite loop
        const originalRequest = error.config
        originalRequest._retry = originalRequest._retry || false
        if (
            error.response.status === 401 && // Check if the error status is 401
            !originalRequest._retry && // Check if the request has already been retried
            localStorage.getItem('refresh') // Check if the refresh token is available
        ) {
            // TODO: refresh token
            originalRequest._retry = true
            await axios
                .post(
                    `${baseUrl}/auth/refresh`,
                    {},
                    {
                        headers: {
                            Authorization: `Bearer ${localStorage.getItem('refresh')}`,
                        },
                    }
                )
                .then((response) => {
                    localStorage.setItem('token', response.data.token)
                    localStorage.setItem('refresh', response.data.refreshToken)
                    // Retry the original request
                    return instance(originalRequest)
                })
                .catch(() => {
                    // Clear the local storage
                    localStorage.removeItem('token')
                    localStorage.removeItem('refresh')
                })
        }
        return Promise.reject(error)
    }
)

export default instance
