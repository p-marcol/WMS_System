import { createContext, ReactNode } from "react";
import { useState } from "react";

export type AuthContextType = {
	token: string;
	setToken: (token: string) => void;
	refreshToken: string;
	setRefreshToken: (refreshToken: string) => void;
	authority: string | null;
	setAuthority: (authority: string | null) => void;
	userInfo: Object | null;
	setUserInfo: (userInfo: Object | null) => void;
};

export const authContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
	const [token, setToken] = useState<string>("");
	const [refreshToken, setRefreshToken] = useState<string>("");
	const [authority, setAuthority] = useState<string | null>(null);
	const [userInfo, setUserInfo] = useState<Object | null>(null);

	const value = {
		token: token,
		refreshToken: refreshToken,
		setToken: setToken,
		setRefreshToken: setRefreshToken,
		authority: authority,
		setAuthority: setAuthority,
		userInfo: userInfo,
		setUserInfo: setUserInfo,
	} as AuthContextType;

	return (
		<authContext.Provider value={value}>{children}</authContext.Provider>
	);
}
