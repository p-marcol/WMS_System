import { UserInfoType } from "@/types/userInfo";
import { View, Text } from "react-native";

export default function userInfoContainer({ user }: { user: UserInfoType }) {
	return (
		<View>
			<Text>{user.username}</Text>
			<Text>{user.email}</Text>
			<Text>{user.authority}</Text>
			<Text></Text>
		</View>
	);
}
