import { useContext, useLayoutEffect, useState } from "react";
import { Tabs } from "expo-router";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";
import Ionicons from "@expo/vector-icons/Ionicons";

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
					tabBarIcon: ({ color }) => (
						<Ionicons
							name="home-outline"
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="timesheet"
				options={{
					title: "Timesheet",
					href: isAdmin ? "/tabs/timesheet" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name="document-text-outline"
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="users"
				options={{
					title: "Users",
					href: isAdmin || isManager ? "/tabs/users" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name="person-outline"
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="units"
				options={{
					title: "Units",
					href: isAdmin ? "/tabs/units" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name="cube-outline"
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="cards"
				options={{
					title: "Cards",
					href: isAdmin ? "/tabs/cards" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name="id-card-outline"
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="settings"
				options={{
					title: "Settings",
					tabBarIcon: ({ color }) => (
						<Ionicons
							name="settings-outline"
							size={24}
							color={color}
						/>
					),
				}}
			/>
		</Tabs>
	);
}
