import { useState, useContext } from "react";
import { Button, Text, TextInput, View } from "react-native";
import NfcManager, { NfcTech, Ndef } from "react-native-nfc-manager";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";
import { router } from "expo-router";

export default function Index() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;
	const auth = useContext(authContext)! as AuthContextType;

	const [nfcSearching, setNfcSearching] = useState<boolean>(false);
	const [login, setLogin] = useState<string>("admin");
	const [password, setPassword] = useState<string>("admin");

	const pingServer = async () => {
		axios
			.get(`/connection/ping`)
			.then((response) => {
				alert(`Server responded with: ${response.data}`);
			})
			.catch((error) => {
				alert(`Failed to ping server: ${error}`);
				console.error(error);
			});
	};

	const Login = async () => {
		axios
			.post(`/auth/login`, {
				username: login,
				password: password,
			})
			.then((response) => {
				auth.setToken(response.data.token);
				auth.setRefreshToken(response.data.refreshToken);

				//! Change to replace on release
				router.push({ pathname: "/tabs" });
			})
			.catch((error) => {
				alert(`Login failed: ${error}`);
				console.error(error);
			});
	};

	const scanNfc = async () => {
		if (nfcSearching) return;
		setNfcSearching(true);
		try {
			await NfcManager.requestTechnology(NfcTech.Ndef);
			const tag = await NfcManager.getTag();
			console.log(tag?.id);
		} catch (error) {
			console.warn(error);
		} finally {
			NfcManager.cancelTechnologyRequest();
			setNfcSearching(false);
		}
	};

	return (
		<View className="bg-gray-userAccent flex-1 justify-center items-center">
			<Button
				title="Ping Server"
				onPress={pingServer}
			/>
			<Text className="text-adminAccent">Login:</Text>
			<TextInput
				className="text-center border w-[60vw]"
				onChangeText={(text) => setLogin(text)}
				value={login}
			/>
			<Text className="text-red-500">Password:</Text>
			<TextInput
				className="text-center border w-[60vw]"
				onChangeText={(text) => setPassword(text)}
				value={password}
				secureTextEntry={true}
			/>
			<Button
				title="Login"
				onPress={Login}
			/>
			<Button
				title="SCAN NFC"
				onPress={scanNfc}
			/>
		</View>
	);
}
