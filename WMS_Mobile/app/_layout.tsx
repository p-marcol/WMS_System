import { Stack } from "expo-router";
import { ServerProvider } from "@/providers/server";
import { AxiosProvider } from "@/providers/axios";
import { verifyInstallation } from "nativewind";
import { AuthProvider } from "@/providers/auth";
import en from "@/assets/i18n/en.json";
import "../assets/styles/global.css";

export default function RootLayout() {
	//! Verify installation
	verifyInstallation();

	return (
		<ServerProvider>
			<AuthProvider>
				<AxiosProvider>
					<Stack
						screenOptions={{
							headerShown: false,
						}}
					>
						<Stack.Screen name="index" />
						<Stack.Screen name="tabs" />
					</Stack>
				</AxiosProvider>
			</AuthProvider>
		</ServerProvider>
	);
}
