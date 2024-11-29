import { createContext, ReactNode } from "react";
import { useState } from "react";

export type ServerContextType = {
	serverIp: string;
	serverPort: string;
	setServerIp: (ip: string) => void;
	setServerPort: (port: string) => void;
	getPath: () => string;
};

export const serverContext = createContext<ServerContextType | null>(null);

export function ServerProvider({ children }: { children: ReactNode }) {
	const [serverIp, setServerIp] = useState<string>("192.168.1.160");
	const [serverPort, setServerPort] = useState<string>("8080");

	const getPath = () => {
		return `http://${serverIp}:${serverPort}`;
	};

	const value = {
		serverIp,
		serverPort,
		setServerIp,
		setServerPort,
		getPath,
	} as ServerContextType;

	return (
		<serverContext.Provider value={value}>
			{children}
		</serverContext.Provider>
	);
}
