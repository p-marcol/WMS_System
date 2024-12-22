import { UserInfoType } from "@/types/userInfo";
import { View, Text } from "react-native";

export default function userInfoContainer({ user }: { user: UserInfoType }) {
	return (
		<View className="flex flex-col">
			<View className="flex flex-row justify-between">
				<Text className="font-bold text-xl">{user.username}</Text>
				<Text>{user.shortName}</Text>
			</View>
			<View className="flex flex-row justify-between">
				<Text>{user.email}</Text>
				<Text>{user.authority}</Text>
			</View>
		</View>
	);
}
