import { useContext, useLayoutEffect, useState } from "react";
import { Tabs } from "expo-router";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";

export default function TabLayout() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;
	const { setUserInfo } = useContext(authContext)! as AuthContextType;

	useLayoutEffect(() => {
		axios
			.get(`/auth/getMyInfo`)
			.then((response) => {
				setUserInfo(response.data);
			})
			.catch((error) => {
				console.error(error);
			});
	}, []);

	return (
		<Tabs>
			<Tabs.Screen
				name="index"
				options={{
					title: "Index",
					// tabBarIcon: HomeIcon,
				}}
			/>
			<Tabs.Screen
				name="settings"
				options={{
					title: "Settings",
					// icon: "settings",
				}}
			/>
		</Tabs>
	);
}
