import React, { useContext, useEffect, useState } from "react";
import {
	View,
	Text,
	Button,
	StyleSheet,
	Modal,
	ScrollView,
	Pressable,
	FlatList,
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
import Entypo from "@expo/vector-icons/Entypo";

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

	const UserContainer = ({ item }: { item: UserInfoType }) => {
		const tap = Gesture.Tap()
			.maxDuration(250)
			.runOnJS(true)
			.onEnd(() => {
				handleAssign(item);
			});
		return (
			<GestureDetector
				gesture={tap}
				key={item.id}
			>
				<View className="border p-2 bg-white mx-2 my-1 rounded-lg">
					<UserInfoContainer user={item} />
				</View>
			</GestureDetector>
		);
	};

	return (
		<Modal
			visible={visible}
			animationType="slide"
		>
			<GestureHandlerRootView>
				<View className="flex-row justify-between items-center py-2 px-4 border-b-2 border-black">
					<Text className="font-bold text-4xl">Assign to User</Text>
					<Pressable onPress={() => setVisible(false)}>
						<Entypo
							name="cross"
							size={50}
							color="black"
						/>
					</Pressable>
				</View>
				<FlatList
					data={filteredUserList}
					keyExtractor={(item) => item.id.toString()}
					renderItem={({ item }) => <UserContainer item={item} />}
					className="flex-1 bg-blue-500 pt-2"
				/>
			</GestureHandlerRootView>
		</Modal>
	);
}
