<script>
import DashboardPage from './pages/DashboardPage.vue'
import LoginPage from './pages/LoginPage.vue'
import NotFound from './pages/NotFound.vue'

const routes = {
    '/': LoginPage,
    '/dashboard': DashboardPage,
}

export default {
    name: 'App',
    data() {
        return {
            currentPath: window.location.hash,
        }
    },
    computed: {
        currentView() {
            return routes[this.currentPath.slice(1) || '/'] || NotFound
        },
    },
    mounted() {
        window.addEventListener('hashchange', () => {
            this.currentPath = window.location.hash
        })
    },
}
</script>

<template>
    <component :is="currentView" />
</template>
