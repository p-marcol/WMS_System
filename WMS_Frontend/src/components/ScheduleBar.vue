<template>
    <div class="bar" :style="style"></div>
</template>

<script>
export default {
    name: 'ScheduleBar',
    computed: {
        style() {
            const day = this.day == 0 ? 7 : this.day
            const start = this.roundDateTimeToQuarter(this.startHour)
            const end = this.roundDateTimeToQuarter(this.endHour)
            let borderRadius = '1rem'
            if (this.multiday) {
                if (end.gridVal == 96) {
                    borderRadius = '1rem 0 0 1rem'
                } else if (start.gridVal == 0) {
                    borderRadius = '0 1rem 1rem 0'
                }
            }
            const gridArea = `${day} / ${start.gridVal + 1} / span 1 / ${end.gridVal + 1}`
            return {
                gridArea: gridArea,
                borderRadius: borderRadius,
            }
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
    background-color: var(--color-secondary);
    height: 50%;
    z-index: 1;
    transition: box-shadow 0.2s ease-in-out;
}

.bar:hover {
    box-shadow: 0 0 0.5rem 0 var(--color-dark);
}
</style>
