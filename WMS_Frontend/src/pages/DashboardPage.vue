<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
import CardContainer from '@/components/CardContainer.vue'
</script>

<template>
    <MainLayout>
        <CardContainer :title="$t('card.yourTeam')"> <p>Card content</p> </CardContainer>
        <CardContainer :title="$t('card.schedule')"> <p>Card content</p> </CardContainer>
        <CardContainer :title="$t('card.timesheet')"> <p>Card content</p> </CardContainer>
    </MainLayout>
</template>

<script>
export default {
    name: 'DashboardPage',
}
</script>

<style>
#app {
    display: grid;
    grid-template-columns: 90px auto;
}
</style>

<style scoped></style>
