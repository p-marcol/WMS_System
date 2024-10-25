import axios, { AxiosInstance } from "axios";
import { createContext, ReactNode } from "react";

export type AxiosContextType = {
	axios: AxiosInstance;
};

export const axiosContext = createContext<AxiosContextType | null>(null);

export function AxiosProvider({ children }: { children: ReactNode }) {
	const axiosInstance = axios.create({
		baseURL: "",
	});

	return (
		<axiosContext.Provider value={{ axios: axiosInstance }}>
			{children}
		</axiosContext.Provider>
	);
}
