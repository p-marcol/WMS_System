import { Stack } from "expo-router";
import { AxiosProvider } from "@/providers/axios";
import { AuthProvider } from "@/providers/auth";
import "../assets/styles/global.css";

export default function RootLayout() {
	return (
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
	);
}
