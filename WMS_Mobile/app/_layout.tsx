import { Stack } from "expo-router";
import { AxiosProvider } from "@/providers/axios";
import { AuthProvider } from "@/providers/auth";
import { StatusBar } from "expo-status-bar";
import { SafeAreaView } from "react-native-safe-area-context";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import "../assets/styles/global.css";

export default function RootLayout() {
	return (
		<GestureHandlerRootView>
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
		</GestureHandlerRootView>
	);
}
