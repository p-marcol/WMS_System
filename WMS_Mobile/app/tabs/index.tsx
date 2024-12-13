import { View, Text } from "react-native";
import { useLayoutEffect, useContext, useState } from "react";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";

export default function Index() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;
	const { userInfo } = useContext(authContext)! as AuthContextType;

	return (
		<View>
			<Text>Tab Home</Text>
			<Text>{JSON.stringify(userInfo)}</Text>
			<Text>Hello, {userInfo?.shortName}</Text>
		</View>
	);
}
