import { Stack } from "expo-router";
import { AxiosProvider } from "@/providers/axios";

export default function RootLayout() {
	return (
		<AxiosProvider>
			<Stack>
				<Stack.Screen name="index" />
			</Stack>
		</AxiosProvider>
	);
}
