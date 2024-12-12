import { useContext, useLayoutEffect, useState } from "react";
import { Tabs } from "expo-router";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";

export default function TabLayout() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;
	const { setUserInfo } = useContext(authContext)! as AuthContextType;

	const [isAdmin, setIsAdmin] = useState<boolean>(false);
	const [isManager, setIsManager] = useState<boolean>(false);

	useLayoutEffect(() => {
		axios
			.get(`/auth/getMyInfo`)
			.then((response) => {
				setUserInfo(response.data);
				setIsAdmin(response.data.authority === "ADMIN");
				setIsManager(response.data.authority === "MANAGER");
			})
			.catch((error) => {
				console.error(error);
			});
	}, []);

	return (
		<Tabs screenOptions={{ headerShown: false }}>
			<Tabs.Screen
				name="index"
				options={{
					title: "Index",
					// tabBarIcon: HomeIcon,
				}}
			/>
			<Tabs.Screen
				name="users"
				options={{
					title: "Users",
					// icon: "person",
					href: isAdmin || isManager ? "/tabs/users" : null,
				}}
			/>
			<Tabs.Screen
				name="cards"
				options={{
					title: "Cards",
					// icon: "credit-card",
					href: isAdmin ? "/tabs/cards" : null,
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
