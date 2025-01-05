<template>
    <div class="bar" :style="style">
        <span class="Label-P3-SemiBold">
            {{ startHourFormatted }}
        </span>
        <span class="Label-P1-Bold">
            {{ scheduleItem.unitName }}
        </span>
        <span class="Label-P3-SemiBold">
            {{ endHourFormatted }}
        </span>
    </div>
</template>

<script>
export default {
    name: 'ScheduleBarV',
    computed: {
        style() {
            const day = this.day == 0 ? 7 : this.day
            const start = this.roundDateTimeToQuarter(this.startHour)
            const end = this.roundDateTimeToQuarter(this.endHour)
            let borderRadius = '1rem'
            if (this.multiday) {
                if (end.gridVal == 96) {
                    borderRadius = '1rem 1rem 0 0'
                } else if (start.gridVal == 0) {
                    borderRadius = '0 0 1rem 1rem'
                }
            }
            const gridArea = ` ${start.gridVal + 1} / ${day} / ${end.gridVal + 1} / span 1`
            return {
                gridArea: gridArea,
                borderRadius: borderRadius,
            }
        },
        startHourFormatted() {
            const start = this.startHour.toFormat('HH:mm')
            if (this.multiday) {
                return start != '00:00' ? start : ''
            }
            return start
        },
        endHourFormatted() {
            const end = this.endHour.toFormat('HH:mm')
            if (this.multiday) {
                return end != '23:59' ? end : ''
            }
            return end
        },
    },
    props: {
        day: {
            type: Number,
            required: true,
        },
        startHour: {
            type: Object,
            required: true,
        },
        endHour: {
            type: Object,
            required: true,
        },
        multiday: {
            type: Boolean,
            default: false,
        },
        scheduleItem: {
            type: Object,
            default: null,
        },
    },
    methods: {
        roundDateTimeToQuarter(time) {
            const hours = time.hour
            const minutes = time.minute
            var m = parseInt((minutes + 7.5) / 15) % 4
            var h = minutes > 52 ? hours + 1 : hours
            const gridVal = h * 4 + m
            return { h, m, gridVal }
        },
    },
}
</script>

<style scoped>
.bar {
    color: var(--color-white);
    display: grid;
    grid-template-rows: repeat(3, auto);
    width: 90%;
    height: 100%;
    align-content: space-between;
    background-color: var(--color-secondary);
    z-index: 1;
    transition: box-shadow 0.2s ease-in-out;
    justify-self: center;
}

.bar > * {
    padding: 0.2rem;
    text-align: center;
}

.bar:hover {
    box-shadow: 0 0 0.5rem 0 var(--color-dark);
}
</style>
