import { useContext, useLayoutEffect, useState } from "react";
import { router, Tabs, usePathname } from "expo-router";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";
import Ionicons from "@expo/vector-icons/Ionicons";
import * as colorConfig from "@/assets/styles/colors";

export default function TabLayout() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;
	const { setUserInfo } = useContext(authContext)! as AuthContextType;

	const [isAdmin, setIsAdmin] = useState<boolean>(false);
	const [isManager, setIsManager] = useState<boolean>(false);

	let currentPath = usePathname();
	console.log(currentPath);

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

	const userAccent = isAdmin
		? colorConfig.adminAccent
		: isManager
		? colorConfig.managerAccent
		: colorConfig.userAccent;

	return (
		<Tabs
			screenOptions={{
				headerShown: false,
				tabBarActiveTintColor: "white",
				tabBarInactiveTintColor: "white",
				tabBarStyle: {
					backgroundColor: userAccent,
				},
			}}
		>
			<Tabs.Screen
				name="index"
				options={{
					title: "HOME",
					tabBarIcon: ({ color }) => (
						<Ionicons
							name={
								currentPath === "/tabs"
									? "home"
									: "home-outline"
							}
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="timesheet"
				options={{
					title: "TIMESHEET",
					href: "/tabs/timesheet",
					tabBarIcon: ({ color }) => (
						<Ionicons
							name={
								currentPath === "/tabs/timesheet"
									? "document-text"
									: "document-text-outline"
							}
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="users"
				options={{
					title: "USERS",
					href: isAdmin || isManager ? "/tabs/users" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name={
								currentPath === "/tabs/users"
									? "person"
									: "person-outline"
							}
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="units"
				options={{
					title: "UNITS",
					href: isAdmin ? "/tabs/units" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name={
								currentPath === "/tabs/units"
									? "cube"
									: "cube-outline"
							}
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="cards"
				options={{
					title: "CARDS",
					href: isAdmin ? "/tabs/cards" : null,
					tabBarIcon: ({ color }) => (
						<Ionicons
							name={
								currentPath === "/tabs/cards"
									? "id-card"
									: "id-card-outline"
							}
							size={24}
							color={color}
						/>
					),
				}}
			/>
			<Tabs.Screen
				name="settings"
				options={{
					title: "SETTINGS",
					tabBarIcon: ({ color }) => (
						<Ionicons
							name={
								currentPath === "/tabs/settings"
									? "settings"
									: "settings-outline"
							}
							size={24}
							color={color}
						/>
					),
				}}
			/>
		</Tabs>
	);
}
