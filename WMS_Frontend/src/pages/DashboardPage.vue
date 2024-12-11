<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
// import LocaleChanger from '@/components/LocaleChanger.vue'
import ScheduleCard from '@/components/cards/ScheduleCard.vue'
import TeamCard from '@/components/cards/TeamCard.vue'
import TimesheetCard from '@/components/cards/TimesheetCard.vue'
</script>

<template>
    <!-- <LocaleChanger /> -->
    <MainLayout>
        <TeamCard id="yourTeam" />
        <ScheduleCard id="schedule" />
        <TimesheetCard id="timesheet" />
    </MainLayout>
</template>

<script>
export default {
    name: 'DashboardPage',
    data() {
        return {}
    },
}
</script>

<style>
#app {
    display: grid;
    grid-template-columns: 90px auto;
}

#yourTeam {
    grid-column: 1 / span 4;
    grid-row: 1 / span 6;
}

#schedule {
    grid-column: 5 / span 8;
    grid-row: 1 / span 6;
}

#timesheet {
    grid-column: 1 / span 12;
    grid-row: 7 / span 4;
}
</style>

<style scoped></style>
