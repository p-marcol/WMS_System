import React, { useContext, useEffect, useState } from "react";
import {
	View,
	Text,
	Button,
	StyleSheet,
	Modal,
	ScrollView,
} from "react-native";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { UserInfoType } from "@/types/userInfo";
import UserInfoContainer from "../userInfoContainer";
import { SearchBar } from "@rneui/themed";
import {
	Gesture,
	GestureDetector,
	GestureHandlerRootView,
} from "react-native-gesture-handler";

export default function AssignToUserModal({
	visible,
	setVisible,
	setSelectedUser,
}: {
	visible: boolean;
	setVisible: (visible: boolean) => void;
	setSelectedUser: (user: UserInfoType) => void;
}) {
	const { axios } = useContext(axiosContext)! as AxiosContextType;

	const [userList, setUserList] = useState<UserInfoType[]>([]);
	const [search, setSearch] = useState<string>("");

	const updateSearch = (search: string) => {
		setSearch(search);
	};

	const filteredUserList = userList.filter(
		(user) =>
			user.username.toLowerCase().includes(search.toLowerCase()) ||
			user.email.toLowerCase().includes(search.toLowerCase()) ||
			user.authority.toLowerCase().includes(search.toLowerCase())
	);

	const handleAssign = (user: UserInfoType) => {
		setSelectedUser(user);
		setVisible(false);
	};

	useEffect(() => {
		if (visible) {
			// Fetch all users
			axios
				.get("/user/getAllUsers")
				.then((response) => {
					setUserList(
						response.data.sort(
							(a: UserInfoType, b: UserInfoType) => a.id - b.id
						)
					);
					updateSearch("");
				})
				.catch((error) => {
					console.error(error);
				});
		}
	}, [visible]);

	return (
		<Modal
			visible={visible}
			animationType="slide"
		>
			<GestureHandlerRootView>
				<ScrollView>
					<Text className="font-bold text-3xl">Assign to User</Text>
					<SearchBar
						placeholder="Search for a user"
						onChangeText={updateSearch}
						value={search}
						lightTheme
					/>
					<Button
						title="Cancel"
						onPress={() => setVisible(false)}
					/>
					{filteredUserList.map((user) => {
						const tap = Gesture.Tap()
							.maxDuration(250)
							.runOnJS(true)
							.onEnd(() => {
								handleAssign(user);
							});
						return (
							<GestureDetector
								gesture={tap}
								key={user.id}
							>
								<View className="border p-2 bg-white m-4 rounded-lg">
									<UserInfoContainer user={user} />
								</View>
							</GestureDetector>
						);
					})}
				</ScrollView>
			</GestureHandlerRootView>
		</Modal>
	);
}
