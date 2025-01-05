<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
import CardContainer from '@/components/CardContainer.vue'
import TeamCardUserView from '@/components/TeamCardUserView.vue'
import {
    XMarkIcon,
    ChevronLeftIcon,
    ChevronRightIcon,
    Cog6ToothIcon,
} from '@heroicons/vue/24/outline'
import Button from 'primevue/button'
import { DateTime } from 'luxon'
import ScheduleBarV from '@/components/ScheduleBarV.vue'
</script>

<template>
    <MainLayout ref="mainLayoutRef" @user-fetched="updateView">
        <CardContainer
            id="schedule"
            :class="!showManagement && 'w-full'"
            :title="cardTitle"
            :loading="loading"
        >
            <template #header>
                <div id="datePicker">
                    <ChevronLeftIcon class="icon" @click="weekBack" />
                    <span class="Label-P3-SemiBold dates">{{ startDate }} - {{ endDate }}</span>
                    <ChevronRightIcon class="icon" @click="weekForward" />
                </div>
                <div id="headerRight">
                    <Cog6ToothIcon class="icon" v-if="showManagement" />
                    <Button
                        type="button"
                        :label="showTeamSchedule ? $t('schedule.showMy') : $t('schedule.showTeam')"
                        @click="toggleTeamSchedule"
                        class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                        unstyled
                    />
                </div>
            </template>

            <div class="scheduleContainer">
                <div id="weekdays" class="weekgrid">
                    <span class="day Label-P1">{{ $t('weekday.short.monday') }}</span>
                    <span class="day Label-P1">{{ $t('weekday.short.tuesday') }}</span>
                    <span class="day Label-P1">{{ $t('weekday.short.wednesday') }}</span>
                    <span class="day Label-P1">{{ $t('weekday.short.thursday') }}</span>
                    <span class="day Label-P1">{{ $t('weekday.short.friday') }}</span>
                    <span class="day Label-P1">{{ $t('weekday.short.saturday') }}</span>
                    <span class="day Label-P1">{{ $t('weekday.short.sunday') }}</span>
                </div>
                <div id="scheduleGrid" class="weekgrid">
                    <div id="background">
                        <div v-for="i in 24" :key="i" :style="{ gridRow: i }">
                            <span class="hour Label-P4">
                                {{ DateTime.fromObject({ hour: i - 1 }).toFormat('HH:mm') }}
                            </span>
                        </div>
                    </div>
                    <div class="label24">
                        <span class="hour Label-P4">24:00</span>
                    </div>
                    <ScheduleBarV
                        v-for="scheduleItem in schedule"
                        :key="scheduleItem.id"
                        :day="scheduleItem.day"
                        :startHour="scheduleItem.startHour"
                        :endHour="scheduleItem.endHour"
                        :multiday="scheduleItem.isMultiDay"
                        :scheduleItem="scheduleItem"
                    />
                </div>
            </div>
        </CardContainer>
        <CardContainer
            v-if="showManagement"
            id="team"
            :title="$t('card.yourTeam')"
            :loading="loadingTeam"
        >
            <div class="userContainer" v-for="user in team" :key="user.userId">
                <TeamCardUserView :user="user" @click="setSelectedUser(user.userId)" />
                <div v-if="selectedUserId === user.userId">
                    <Cog6ToothIcon class="icon" />
                    <XMarkIcon class="icon" @click="setSelectedUser(null)" />
                </div>
            </div>
        </CardContainer>
    </MainLayout>
</template>

<script>
export default {
    data() {
        return {
            loading: false,
            loadingTeam: true,
            selectedUserId: null,
            schedule: [],
            team: [],
            showTeamSchedule: false,
            startDate: DateTime.now().startOf('week').toISODate(),
            endDate: DateTime.now().endOf('week').toISODate(),
            unitId: null,
            showManagement: false,
        }
    },
    computed: {
        cardTitle() {
            if (this.loading) return this.$t('card.loading')
            if (this.showTeamSchedule) return this.$t('card.teamSchedule')
            if (this.selectedUserId) return this.$t('card.userSchedule')
            return this.$t('card.mySchedule')
        },
        user() {
            return this.$refs.mainLayoutRef.user
        },
    },
    async mounted() {
        await this.getMyTeam()
    },
    methods: {
        updateView() {
            if (this.user.role === 'ADMIN' || this.user.role === 'MANAGER') {
                this.showManagement = true
            }
            this.fetchSchedule()
        },
        weekBack() {
            this.startDate = DateTime.fromISO(this.startDate)
                .minus({ weeks: 1 })
                .startOf('week')
                .toISODate()
            this.endDate = DateTime.fromISO(this.endDate)
                .minus({ weeks: 1 })
                .endOf('week')
                .toISODate()
            this.fetchSchedule()
        },
        weekForward() {
            this.startDate = DateTime.fromISO(this.startDate)
                .plus({ weeks: 1 })
                .startOf('week')
                .toISODate()
            this.endDate = DateTime.fromISO(this.endDate)
                .plus({ weeks: 1 })
                .endOf('week')
                .toISODate()
            this.fetchSchedule()
        },
        async toggleTeamSchedule() {
            this.showTeamSchedule = !this.showTeamSchedule
            await this.fetchSchedule()
        },
        async getMyTeam() {
            this.loadingTeam = true
            await this.axios
                .get('/unit/my/workers')
                .then((res) => {
                    this.unitId = res.data.unitId
                    this.team = res.data.workers.sort((a) => (a.position === 'MANAGER' ? -1 : 1))
                })
                .catch((err) => {
                    console.error(err)
                })
                .finally(() => {
                    this.loadingTeam = false
                })
        },
        setSelectedUser(id) {
            this.selectedUserId = id
        },
        async fetchSchedule() {
            this.loading = true
            if (this.showTeamSchedule) {
                await this.fetchTeamSchedule()
            } else {
                await this.fetchUserSchedule()
            }
            console.log(this.schedule)
            this.loading = false
        },
        async fetchUserSchedule() {
            if (!this.user) return
            const id = this.selectedUserId || this.user.id
            await this.axios
                .get(`/schedule/getUser/${id}/${this.startDate}/${this.endDate}`)
                .then((res) => {
                    console.log(res.data)
                    this.schedule = this.processResponse(res)
                })
                .catch((err) => {
                    console.error(err)
                })
        },
        async fetchTeamSchedule() {
            await this.axios
                .get(`/schedule/getUnit/${this.unitId}/${this.startDate}/${this.endDate}`)
                .then((res) => {
                    console.log(res.data)
                    this.schedule = this.processResponse(res)
                })
                .catch((err) => {
                    console.error(err)
                })
        },
        processResponse(res) {
            let scheduleArray = []
            Object.entries(res.data.calendarListItems).forEach(([key, value]) => {
                value.forEach((item) => {
                    const [beginStart, endStart] = item.startHour.split(':')
                    item.startHour = DateTime.fromObject({
                        hour: beginStart,
                        minute: endStart,
                    })
                    const [beginEnd, endEnd] = item.endHour.split(':')
                    item.endHour = DateTime.fromObject({
                        hour: beginEnd,
                        minute: endEnd,
                    })
                    item.day = DateTime.fromISO(key).weekday
                    scheduleArray.push(item)
                })
            })
            return scheduleArray
        },
    },
    watch: {
        selectedUserId: {
            immediate: true,
            handler() {
                this.fetchSchedule()
            },
        },
    },
}
</script>

<style scoped>
#headerRight {
    display: grid;
    grid-template-columns: 1fr auto;
    align-items: center;
    gap: 0.5rem;
}
#datePicker {
    display: grid;
    grid-template-columns: repeat(3, auto);
    align-items: center;
    justify-content: space-between;
}

#schedule {
    grid-area: 13 / 1 / 1 / 9;
}

#team {
    grid-area: 13 / 9 / 1 / 13;
}

#scheduleGrid {
    margin: 1rem 0.5rem 1rem 0rem;
    position: relative;
    flex-grow: 1;
    grid-template-rows: repeat(96, 1fr);
    gap: 2px;
}

#background {
    position: absolute;
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-rows: repeat(1fr, 24);
    grid-template-columns: 1fr;
}

#background > * {
    position: relative;
    border-bottom: 1px solid black;
}

#background > *:nth-child(1) {
    border-top: 1px solid black;
}

.w-full {
    grid-area: 13 / 1 / 1 / 13 !important;
}

.hour {
    position: absolute;
    transform: translate(-120%, -55%);
    top: 0;
}

.weekgrid {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    align-items: center;
}

.scheduleContainer {
    display: grid;
    grid-template-rows: auto 1fr;
    height: 100%;
    width: 100%;
    padding-left: 3.5rem;
}

.userContainer {
    display: grid;
    grid-template-columns: 1fr auto auto;
    align-items: center;
}

.label24 {
    position: absolute;
    grid-row: 24;
    left: 0;
    bottom: 0 !important;
    border: none !important;
}

.icon {
    height: 3rem;
    color: var(--color-secondary);
    cursor: pointer;
}

.dates {
    cursor: default;
}
</style>
