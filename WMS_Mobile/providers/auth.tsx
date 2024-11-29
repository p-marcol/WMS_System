import { createContext, ReactNode } from "react";
import { useState } from "react";

export type AuthContextType = {
	token: string;
	refreshToken: string;
	setToken: (token: string) => void;
	setRefreshToken: (refreshToken: string) => void;
};

export const authContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
	const [token, setToken] = useState<string>("");
	const [refreshToken, setRefreshToken] = useState<string>("");

	const value = {
		token: token,
		refreshToken: refreshToken,
		setToken: setToken,
		setRefreshToken: setRefreshToken,
	} as AuthContextType;

	return (
		<authContext.Provider value={value}>{children}</authContext.Provider>
	);
}
