import React, { useState, useContext, useEffect } from "react";
import { Button, Modal, Text, View } from "react-native";
import NfcManager, { NfcTech } from "react-native-nfc-manager";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { UserInfoType } from "@/types/userInfo";
import AssignToUserModal from "@/components/modals/assignToUser";

export default function Users() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;

	const [hasNfc, setHasNfc] = useState<boolean | null>(null);
	const [nfcEnabled, setNfcEnabled] = useState<boolean | null>(null);

	const [assignModalVisible, setAssignModalVisible] =
		useState<boolean>(false);

	const [nfcSearching, setNfcSearching] = useState<boolean>(false);
	const [nfcUid, setNfcUid] = useState<string | null | undefined>(null);
	const [nfcUser, setNfcUser] = useState<UserInfoType | null | undefined>(
		undefined
	);

	(async function () {
		setHasNfc(await NfcManager.isSupported());
		setNfcEnabled(await NfcManager.isEnabled());
	})();

	useEffect(() => {
		getUserFromNfc();
	}, [nfcUid]);

	const scanNfc = async () => {
		setNfcUid(null);
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
			console.warn(error);
		} finally {
			NfcManager.cancelTechnologyRequest();
			setNfcSearching(false);
		}
	};

	const getUserFromNfc = async () => {
		if (!nfcUid) return;
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
			});
	};

	if (hasNfc === null) {
		return null;
	} else if (hasNfc === false) {
		return <Text>This feature requires NFC</Text>;
	}

	return (
		<View>
			<Button
				title={nfcUid === null ? "Scan Tag" : "Scan new Tag"}
				onPress={scanNfc}
			/>
			{nfcSearching && <Text>Scanning...</Text>}
			<Text>{nfcUid}</Text>
			{nfcUid && !nfcUser && (
				<>
					<Text>User not found</Text>
					<Button
						title="Assign tag to user"
						onPress={() => setAssignModalVisible(true)}
					/>
					<AssignToUserModal
						visible={assignModalVisible}
						setVisible={setAssignModalVisible}
					/>
				</>
			)}
			{nfcUid && nfcUser && (
				<>
					<Text>User found!</Text>
					<Text>{JSON.stringify(nfcUser)}</Text>
					<Button
						title="Assign new tag"
						onPress={() => setNfcUid(null)}
					/>
					<Button
						title="Delete tag"
						onPress={() => setNfcUid(null)}
					/>
				</>
			)}
		</View>
	);
}
