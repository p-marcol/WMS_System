import axios, { AxiosError, AxiosInstance, AxiosRequestConfig } from "axios";
import { createContext, useContext, ReactNode } from "react";
import { authContext, AuthContextType } from "./auth";
import { serverContext, ServerContextType } from "./server";

export type AxiosContextType = {
	axios: AxiosInstance;
};

export const axiosContext = createContext<AxiosContextType | null>(null);

export function AxiosProvider({ children }: { children: ReactNode }) {
	const auth = useContext(authContext)! as AuthContextType;
	const server = useContext(serverContext)! as ServerContextType;

	const axiosInstance = axios.create({
		baseURL: "",
	});

	axiosInstance.interceptors.request.use(
		(config) => {
			if (auth) {
				const token = auth.token;
				if (token) {
					config.headers.Authorization = `Bearer ${token}`;
				}
			}
			return config;
		},
		(error) => Promise.reject(error)
	);

	axiosInstance.interceptors.response.use(
		(response) => response,
		async (error: AxiosError) => {
			const originalRequest = error.config as AxiosRequestConfig & {
				_retry?: boolean;
			};
			originalRequest._retry = originalRequest._retry || false;
			if (
				error.response?.status === 401 &&
				!originalRequest._retry &&
				auth.refreshToken
			) {
				originalRequest._retry = true;
				await axios
					.post(
						`${server.getPath()}/auth/refresh`,
						{},
						{
							headers: {
								Authorization: `Bearer ${auth.refreshToken}`,
							},
						}
					)
					.then((response) => {
						auth.setToken(response.data.token);
						auth.setRefreshToken(response.data.refreshToken);
						return axiosInstance(originalRequest);
					})
					.catch((error) => {
						console.error(error);
						auth.setToken("");
						auth.setRefreshToken("");
						return Promise.reject(error);
					});
			}
			return Promise.reject(error);
		}
	);

	return (
		<axiosContext.Provider value={{ axios: axiosInstance }}>
			{children}
		</axiosContext.Provider>
	);
}
