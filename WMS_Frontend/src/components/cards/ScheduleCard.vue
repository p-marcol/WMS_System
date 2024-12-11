<script setup>
import { DateTime } from 'luxon'
import CardContainer from '../CardContainer.vue'
import ScheduleBar from '../ScheduleBar.vue'
</script>

<template>
    <CardContainer :title="$t('card.schedule')" :loading="loading">
        <template #header>
            <span class="Text-P1-bold">{{ this.startDate }} - {{ this.endDate }} </span>
        </template>
        <div class="scheduleContainer">
            <div id="weekdays" class="weekgrid">
                <span class="day Label-P1">
                    {{ $t('weekday.monday') }}
                </span>
                <span class="day Label-P1">
                    {{ $t('weekday.tuesday') }}
                </span>
                <span class="day Label-P1">
                    {{ $t('weekday.wednesday') }}
                </span>
                <span class="day Label-P1">
                    {{ $t('weekday.thursday') }}
                </span>
                <span class="day Label-P1">
                    {{ $t('weekday.friday') }}
                </span>
                <span class="day Label-P1">
                    {{ $t('weekday.saturday') }}
                </span>
                <span class="day Label-P1">
                    {{ $t('weekday.sunday') }}
                </span>
            </div>
            <div id="scheduleGrid" class="weekgrid">
                <div id="background">
                    <div v-for="i in 24" :key="i" :style="{ gridColumn: i }">
                        <span class="hour Label-P3">{{ (i - 1) % 8 === 0 ? i - 1 : '' }}</span>
                    </div>
                    <div class="label24">
                        <span class="Label-P3 hour">24</span>
                    </div>
                </div>
                <ScheduleBar
                    v-for="scheduleItem in schedule"
                    :key="scheduleItem.id"
                    :day="scheduleItem.day"
                    :startHour="scheduleItem.startHour"
                    :endHour="scheduleItem.endHour"
                    :multiday="scheduleItem.isMultiDay"
                />
            </div>
        </div>
    </CardContainer>
</template>

<script>
export default {
    name: 'ScheduleCard',
    data() {
        return {
            loading: false,
            schedule: [],
            startDate: '',
            endDate: '',
        }
    },
    inject: {
        user: {
            default: null,
        },
    },
    emits: ['loading'],
    mounted() {
        const [start, end] = this.getWeekStartAndEnd()
        this.startDate = start
        this.endDate = end
    },
    methods: {
        getWeekStartAndEnd() {
            const start = DateTime.local().startOf('week').toISODate()
            const end = DateTime.local().endOf('week').toISODate()
            return [start, end]
        },
        async fetchSchedule() {
            this.loading = true
            await this.axios
                .get(`/schedule/getUser/${this.user.id}/${this.startDate}/${this.endDate}`)
                .then((res) => {
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
                    this.schedule = scheduleArray
                })
                .catch((err) => {
                    console.error(err)
                })
            this.loading = false
        },
    },
    watch: {
        user: {
            immediate: true,
            async handler() {
                this.w = true
                if (this.user) {
                    await this.fetchSchedule()
                }
            },
        },
    },
}
</script>

<style scoped>
.scheduleContainer {
    padding-top: 1.2rem;
    display: grid;
    grid-template-columns: auto 1fr;
    gap: 0.5rem;
    height: 100%;
    width: 100%;
}

.hour {
    position: absolute;
    transform: translate(-50%, -100%);
    top: 0;
}

.label24 {
    position: absolute;
    top: 0;
    left: 100%;
    border: none !important;
}

#weekdays {
    grid-template-columns: 1fr;
    grid-area: 'weekdays';
}

#scheduleGrid {
    margin: 0 0.5rem 0 0.35rem;
    grid-area: 'scheduleGrid';
    position: relative;
    flex-grow: 1;
    grid-template-columns: auto repeat(96, 1fr);
    gap: 2px;
}

#background {
    position: absolute;
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-columns: repeat(1fr, 24);
    grid-template-rows: 1fr;
}

#background > * {
    border-right: 1px solid black;
}

#background > *:nth-child(1) {
    border-left: 1px solid black;
}

.weekgrid {
    display: grid;
    grid-template-rows: repeat(7, 1fr);
    align-items: center;
}
</style>
