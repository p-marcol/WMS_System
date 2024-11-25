import { useState, useContext } from "react";
import { Button, Text, TextInput, View } from "react-native";
import NfcManager, { NfcTech, Ndef } from "react-native-nfc-manager";
import { axiosContext, AxiosContextType } from "@/providers/axios";

export default function Index() {
	const axios = useContext(axiosContext)!.axios;

	const [serverIp, setServerIp] = useState<string>("192.168.1.160");
	const [serverPort, setServerPort] = useState<string>("8080");
	const [nfcSearching, setNfcSearching] = useState<boolean>(false);
	const [login, setLogin] = useState<string>("");
	const [password, setPassword] = useState<string>("");

	const pingServer = async () => {
		axios
			.get(`http://${serverIp}:${serverPort}/connection/ping`)
			.then((response) => {
				alert(`Server responded with: ${response.data}`);
			})
			.catch((error) => {
				alert(`Failed to ping server: ${error}`);
				console.error(error);
			});
	};

	const scanNfc = async () => {
		if (nfcSearching) return;
		setNfcSearching(true);
		try {
			await NfcManager.requestTechnology(NfcTech.MifareUltralight);
			const tag = await NfcManager.getTag();
			const payload = Ndef.text.decodePayload(
				new Uint8Array(tag!.ndefMessage[0].payload)
			);
			console.log(tag?.id, payload);
		} catch (error) {
			console.warn(error);
		} finally {
			NfcManager.cancelTechnologyRequest();
			setNfcSearching(false);
		}
	};

	const writeNfc = async () => {
		if (nfcSearching) return;
		setNfcSearching(true);
		try {
			await NfcManager.requestTechnology(NfcTech.Ndef);

			const text = "Hello, World!" + Math.random();

			const bytes = Ndef.encodeMessage([Ndef.textRecord(text)]);

			console.log(text);

			if (bytes) {
				await NfcManager.ndefHandler.writeNdefMessage(bytes);
				alert("NFC tag written!");
			}
		} catch (error) {
			console.error(error);
		} finally {
			NfcManager.cancelTechnologyRequest();
			setNfcSearching(false);
		}
	};

	return (
		<View
			style={{
				flex: 1,
				justifyContent: "center",
				alignItems: "center",
			}}
			className="bg-blue-500"
		>
			<Text>Server IP:</Text>
			<TextInput
				className="bg-red-500"
				onChangeText={(text) => setServerIp(text)}
				value={serverIp}
			/>
			<Text>Server Port:</Text>
			<TextInput
				style={{
					borderWidth: 1,
					width: 200,
					textAlign: "center",
				}}
				onChangeText={(text) => setServerPort(text)}
				value={serverPort}
			/>
			<Text className="text-red-500">Login:</Text>
			<TextInput
				style={{
					borderWidth: 1,
					width: 200,
					textAlign: "center",
				}}
				onChangeText={(text) => setLogin(text)}
				value={login}
			/>
			{/* <Button
				title="Ping Server"
				onPress={pingServer}
			/>
			<Button
				title="SCAN NFC"
				onPress={scanNfc}
			/>
			<Button
				title="WRITE NFC"
				onPress={writeNfc}
			/> */}
		</View>
	);
}
