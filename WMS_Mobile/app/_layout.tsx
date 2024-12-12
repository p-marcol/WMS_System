import { Stack } from "expo-router";
import { AxiosProvider } from "@/providers/axios";
import { AuthProvider } from "@/providers/auth";
import { StatusBar } from "expo-status-bar";
import { SafeAreaView } from "react-native-safe-area-context";
import "../assets/styles/global.css";

export default function RootLayout() {
	return (
		<AuthProvider>
			<AxiosProvider>
				<SafeAreaView style={{ flex: 1 }}>
					<StatusBar style="dark" />
					<Stack
						screenOptions={{
							headerShown: false,
						}}
					>
						<Stack.Screen name="index" />
						<Stack.Screen name="tabs" />
					</Stack>
				</SafeAreaView>
			</AxiosProvider>
		</AuthProvider>
	);
}
