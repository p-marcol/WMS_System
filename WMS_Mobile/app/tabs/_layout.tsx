import { useContext, useLayoutEffect } from "react";
import { Tabs } from "expo-router";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { serverContext, ServerContextType } from "@/providers/server";

export default function TabLayout() {
	const { getPath } = useContext(serverContext)! as ServerContextType;
	const { axios } = useContext(axiosContext)! as AxiosContextType;

	const whoami = async () => {
		axios
			.get(`${getPath()}/auth/getMyInfo`)
			.then((response) => {
				console.log(response.data);
			})
			.catch((error) => {
				console.error(error);
			});
	};

	useLayoutEffect(() => {
		whoami();
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
