<script setup>
import Sidebar from '@/components/layout/SideBar.vue'
import TopBar from '@/components/layout/TopBar.vue'
import { computed } from 'vue'
</script>

<template>
    <Sidebar :user="user" />
    <main>
        <TopBar :user="user" />
        <div id="dashboard">
            <slot />
        </div>
    </main>
</template>

<script>
export default {
    name: 'MainLayout',
    data() {
        return {
            user: null,
        }
    },
    provide() {
        return {
            user: computed(() => this.user),
        }
    },
    async created() {
        await this.getUserData()
    },
    methods: {
        async getUserData() {
            await this.axios
                .get('/auth/getMyInfo')
                .then((res) => {
                    if (res.data) {
                        this.user = {
                            id: res.data.id,
                            username: res.data.username,
                            firstName: res.data.firstName,
                            lastName: res.data.lastName,
                            shortName: res.data.shortName,
                            email: res.data.email,
                            role: res.data.authority,
                        }
                    }
                })
                .catch(() => {
                    this.$router.push('/login')
                })
        },
    },
}
</script>

<style scoped>
main {
    height: 100vh !important;
    display: grid;
    grid-template-rows: auto 1fr;
}

#dashboard {
    padding: 1rem;
    width: 100%;
    height: 100%;
    min-width: 0;
    min-height: 0;
    display: grid;
    grid-template-columns: repeat(12, 1fr);
    grid-template-rows: repeat(10, 1fr);
    gap: 1rem;
}
</style>
