<script setup>
import Sidebar from '@/components/layout/SideBar.vue'
import CardContainer from '@/components/layout/CardContainer.vue'
import TopBar from '@/components/layout/TopBar.vue'
</script>

<template>
    <Sidebar />
    <main>
        <TopBar />
        <div id="dashboard">
            <CardContainer> <p>Card content</p> </CardContainer>
        </div>
    </main>
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

<style scoped>
#dashboard {
    width: 100dvw;
    height: 100dvh;
}
</style>
