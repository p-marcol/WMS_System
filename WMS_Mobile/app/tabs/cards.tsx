import React, { useState, useContext, useEffect } from "react";
import { Pressable, Text, View } from "react-native";
import NfcManager, { NfcTech } from "react-native-nfc-manager";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { UserInfoType } from "@/types/userInfo";
import AssignToUserModal from "@/components/modals/assignToUser";

export default function Users() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;

	const [hasNfc, setHasNfc] = useState<boolean | null>(null);
	const [loading, setLoading] = useState<boolean>(false);

	const [assignModalVisible, setAssignModalVisible] =
		useState<boolean>(false);

	const [nfcSearching, setNfcSearching] = useState<boolean>(false);
	const [nfcUid, setNfcUid] = useState<string | null | undefined>(null);
	const [nfcUser, setNfcUser] = useState<UserInfoType | null | undefined>(
		undefined
	);
	const [selectedUser, setSelectedUser] = useState<UserInfoType | null>(null);

	(async function () {
		setHasNfc(await NfcManager.isSupported());
	})();

	useEffect(() => {
		getUserFromNfc();
	}, [nfcUid]);

	const scanNfc = async () => {
		resetState();
		if ((await NfcManager.isEnabled()) === false) {
			alert("NFC is disabled");
			return;
		}
		if (nfcSearching) return;
		setNfcSearching(true);
		try {
			await NfcManager.requestTechnology(NfcTech.Ndef);
			const tag = await NfcManager.getTag();
			console.log(tag?.id);
			setNfcUid(tag?.id);
		} catch (error) {
			// console.warn(error);
		} finally {
			NfcManager.cancelTechnologyRequest();
			setNfcSearching(false);
		}
	};

	const getUserFromNfc = async () => {
		if (!nfcUid) return;
		setLoading(true);
		axios
			.get(`/card/user/uid/${nfcUid}`)
			.then((response) => {
				setNfcUser(response.data);
			})
			.catch((error) => {
				if (error.response?.status === 404) {
					setNfcUser(null);
					return;
				}
				alert(`Failed to fetch user: ${error}`);
				console.error(error);
			})
			.finally(() => {
				setLoading(false);
			});
	};

	const assignNewTag = () => {};

	const deleteTag = () => {
		if (!nfcUid) return;
		setLoading(true);
		axios
			.delete(`/card/delete/${nfcUid}`)
			.then(() => {
				alert("Tag deleted successfully");
				resetState();
			})
			.catch((error) => {
				alert(`Failed to delete tag: ${error}`);
				console.error(error);
			})
			.finally(() => {
				setLoading(false);
			});
	};

	const resetState = () => {
		setNfcUid(null);
		setNfcUser(null);
		setSelectedUser(null);
	};

	const assignCard = async () => {
		if (!selectedUser || !nfcUid) return;
		axios
			.put(`/card/assign/${selectedUser.id}/${nfcUid}`)
			.then(() => {
				alert("Tag assigned successfully");
				resetState();
			})
			.catch((error) => {
				alert(`Failed to assign tag: ${error}`);
				console.error(error);
			});
	};

	if (hasNfc === null) {
		return null;
	} else if (hasNfc === false) {
		return <Text>This feature requires NFC</Text>;
	}

	try {
		NfcManager.cancelTechnologyRequest();
	} catch (error) {}

	return (
		<View className="flex justify-around items-center w-full h-full ">
			<View
				className={`${
					!nfcUid && "hidden"
				} flex flex-col items-center gap-2`}
			>
				{loading && (
					<Text className="text-warning text-4xl">Loading...</Text>
				)}
				{!loading && nfcUid && !nfcUser && (
					<>
						<Text className="text-4xl text-danger font-bold">
							User not found!
						</Text>
						<Pressable
							className="bg-secondary rounded-xl p-2"
							onPress={() => setAssignModalVisible(true)}
						>
							<Text className="text-white font-bold text-lg">
								Select user
							</Text>
						</Pressable>
						<AssignToUserModal
							visible={assignModalVisible}
							setVisible={setAssignModalVisible}
							setSelectedUser={setSelectedUser}
						/>
						{selectedUser && (
							<>
								<Text>
									Selected user: {selectedUser.shortName}
								</Text>
								<Pressable
									className="bg-secondary rounded-xl p-2"
									onPress={assignCard}
								>
									<Text className="text-white font-bold text-lg">
										Assign
									</Text>
								</Pressable>
							</>
						)}
					</>
				)}
				{!loading && nfcUid && nfcUser && (
					<>
						<Text className="text-4xl text-success font-bold">
							User found!
						</Text>
						<Text>This tag belongs to: #{nfcUser.id}</Text>
						<Text>{nfcUser.shortName}</Text>
						<Text>{nfcUser.email}</Text>
						<Pressable
							className="bg-danger rounded-xl p-2"
							onPress={deleteTag}
						>
							<Text className="text-white font-bold text-lg">
								Delete tag
							</Text>
						</Pressable>
					</>
				)}
			</View>
			<Pressable
				className={`bg-secondary aspect-square ${
					nfcUid ? "w-[40%]" : "w-[80%]"
				} rounded-full flex justify-center items-center`}
				onPress={scanNfc}
			>
				<Text
					className={`color-white ${
						nfcUid ? "text-2xl" : "text-4xl"
					} font-bold`}
				>
					{nfcUid === null ? "Scan Tag" : "Scan new Tag"}
				</Text>
				{nfcSearching && (
					<Text className={`color-white text-3xl`}>Scanning...</Text>
				)}
			</Pressable>
		</View>
	);
}
