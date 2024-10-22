<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
import CardContainer from '@/components/CardContainer.vue'
</script>

<template>
    <MainLayout>
        <CardContainer :title="$t('card.yourTeam')" id="yourTeam" :loading="false">
            <p>Card content</p>
            <button @click="show">Show Toast</button>
        </CardContainer>
        <CardContainer :title="$t('card.schedule')" id="schedule" :loading="false">
            <p>Card content</p>
        </CardContainer>
        <CardContainer :title="$t('card.timesheet')" id="timesheet" :loading="true">
            <p>Card content</p>
        </CardContainer>
    </MainLayout>
</template>

<script>
export default {
    name: 'DashboardPage',
    methods: {
        show() {
            this.$toast.add({
                severity: 'success',
                summary: 'Test Message',
                detail: 'Message Content',
                life: 3000,
            })
        },
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
