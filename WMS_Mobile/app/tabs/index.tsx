import { View, Text } from "react-native";
import { useLayoutEffect, useContext, useState } from "react";
import { axiosContext, AxiosContextType } from "@/providers/axios";
import { authContext, AuthContextType } from "@/providers/auth";
import { DateTime } from "luxon";

import type { ScheduleItemType } from "@/types/scheduleItem";
import React from "react";

export default function Index() {
	const { axios } = useContext(axiosContext)! as AxiosContextType;
	const { userInfo } = useContext(authContext)! as AuthContextType;

	const [loadingSchedule, setLoadingSchedule] = useState<boolean>(true);

	const [todayWork, setTodayWork] = useState<ScheduleItemType[] | null>(null);
	const [tomorrowWork, setTomorrowWork] = useState<ScheduleItemType[] | null>(
		null
	);

	const getTodaySchedule = async () => {
		setLoadingSchedule(true);
		const todayDate = DateTime.now();
		const today = todayDate.toISODate();
		const tomorrow = todayDate.plus({ days: 1 }).toISODate();
		console.log(today, tomorrow);
		try {
			await axios
				.get(`/schedule/getUser/${userInfo!.id!}/${today}/${tomorrow}`)
				.then((response) => {
					setTodayWork(
						response.data!.calendarListItems[
							today
						] as ScheduleItemType[]
					);
					setTomorrowWork(
						response.data!.calendarListItems[
							tomorrow
						] as ScheduleItemType[]
					);
				})
				.catch((error) => {
					console.error(error);
				})
				.finally(() => {
					setLoadingSchedule(false);
				});
		} catch (error) {}
	};

	useLayoutEffect(() => {
		getTodaySchedule();
	}, [userInfo]);

	const DayView = ({
		label,
		scheduleItem,
	}: {
		label: string;
		scheduleItem: ScheduleItemType[];
	}) => {
		return (
			<>
				<Text className="text-3xl font-bold">{label}</Text>
				{scheduleItem.map((item, i) => (
					<View key={i}>
						<Text>From: {item.startHour}</Text>
						<Text>To: {item.endHour}</Text>
						<Text>Unit: {item.unitName}</Text>
					</View>
				))}
			</>
		);
	};

	return (
		<View className="flex w-full h-full">
			<Text className="text-3xl font-bold">
				Hello, {userInfo?.shortName}
			</Text>
			<Text className="text-2xl">
				Today is:{" "}
				{DateTime.now()
					.setLocale("en")
					.toLocaleString(DateTime.DATE_FULL)}
			</Text>
			{!loadingSchedule && (
				<>
					{todayWork && (
						<DayView
							label="Today's work"
							scheduleItem={todayWork}
						/>
					)}
					{tomorrowWork && (
						<DayView
							label="Tomorrow's work"
							scheduleItem={tomorrowWork}
						/>
					)}
				</>
			)}
		</View>
	);
}
