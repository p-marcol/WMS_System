import { useContext } from "react";
import { View, Text, Button } from "react-native";
import { authContext, AuthContextType } from "@/providers/auth";
import { router } from "expo-router";

export default function Settings() {
	const { logout } = useContext(authContext)! as AuthContextType;

	return (
		<View>
			<Text>Tab Settings</Text>
			<Button
				title="Logout"
				onPress={() => {
					logout();
					router.replace("/");
				}}
			/>
		</View>
	);
}
