import axios, { AxiosError, AxiosInstance, AxiosRequestConfig } from "axios";
import { createContext, useContext, ReactNode } from "react";
import { authContext, AuthContextType } from "./auth";
import { router } from "expo-router";

export type AxiosContextType = {
	axios: AxiosInstance;
};

export const axiosContext = createContext<AxiosContextType | null>(null);

export function AxiosProvider({ children }: { children: ReactNode }) {
	const auth = useContext(authContext)! as AuthContextType;

	// const serverAddress = process.env.EXPO_PUBLIC_SERVER_ADDRESS;
	const serverPort = process.env.EXPO_PUBLIC_SERVER_PORT;

	const serverAddress = "192.168.1.250";

	const axiosInstance = axios.create({
		baseURL: `http://${serverAddress}:${serverPort}`,
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
		(response) => {
			return response;
		},
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
						`http://${process.env.EXPO_PUBLIC_SERVER_ADDRESS}:${process.env.EXPO_PUBLIC_SERVER_PORT}/auth/refresh`,
						{},
						{
							headers: {
								Authorization: `Bearer ${auth.refreshToken}`,
							},
						}
					)
					.then((response) => {
						console.log(response.data);
						auth.setToken(response.data.token);
						auth.setRefreshToken(response.data.refreshToken);
						return axiosInstance(originalRequest);
					})
					.catch((error) => {
						console.error(error);
						auth.setToken("");
						auth.setRefreshToken("");
						router.replace("/");
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
