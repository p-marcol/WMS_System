<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
import CardContainer from '@/components/CardContainer.vue'
</script>

<template>
    <MainLayout>
        <CardContainer id="schedule"> </CardContainer>
        <CardContainer id="team"> </CardContainer>
    </MainLayout>
</template>

<script>
export default {
    data() {
        return {
            schedule: [],
        }
    },
    created() {},
    methods: {},
}
</script>

<style scoped>
#schedule {
    grid-area:;
}
</style>
