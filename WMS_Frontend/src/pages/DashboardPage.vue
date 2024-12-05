<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
import CardContainer from '@/components/CardContainer.vue'
// import LocaleChanger from '@/components/LocaleChanger.vue'
import ScheduleCard from '@/components/cards/ScheduleCard.vue'
import TeamCard from '@/components/cards/TeamCard.vue'
</script>

<template>
    <!-- <LocaleChanger /> -->
    <MainLayout>
        <TeamCard id="yourTeam" />
        <ScheduleCard id="schedule" />
        <CardContainer :title="$t('card.timesheet')" id="timesheet" :loading="true">
            <p>Card content</p>
        </CardContainer>
    </MainLayout>
</template>

<script>
export default {
    name: 'DashboardPage',
    data() {
        return {
            scheduleLoading: false,
        }
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
