import React, { useState, useContext, useEffect } from "react";
import { Button, Text, View } from "react-native";
import NfcManager, { NfcTech, Ndef } from "react-native-nfc-manager";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { get } from "react-native/Libraries/TurboModule/TurboModuleRegistry";
import { UserInfoType } from "@/types/userInfo";

export default function Users() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;

	const [nfcSearching, setNfcSearching] = useState<boolean>(false);
	const [nfcUid, setNfcUid] = useState<string | null | undefined>(null);
	const [nfcUser, setNfcUser] = useState<UserInfoType | null | undefined>(
		undefined
	);

	useEffect(() => {
		getUserFromNfc();
	}, [nfcUid]);

	const scanNfc = async () => {
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
		// Fetch user from server
		alert(`Fetching user with NFC UID: ${nfcUid}`);
		axios
			.get(`/card/user/uid/${nfcUid}`)
			.then((response) => {
				alert(`User found: ${response.data}`);
				setNfcUser(response.data);
			})
			.catch((error) => {
				if (error.response?.status === 404) {
					alert(`User not found`);
					setNfcUser(null);
					return;
				}
				alert(`Failed to fetch user: ${error}`);
				console.error(error);
			});
	};

	return (
		<View>
			<Button
				title={nfcUid === null ? "Scan Tag" : "Scan new Tag"}
				onPress={scanNfc}
			/>
			<Text>{nfcSearching && "Scanning..."}</Text>
			<Text>{nfcUid}</Text>
			{nfcUid && !nfcUser && (
				<>
					<Text>User not found</Text>
					<Button
						title="Assign tag to user"
						onPress={() => alert("Assigning tag to user")}
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
