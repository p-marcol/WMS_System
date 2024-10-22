<script setup>
import Sidebar from '@/components/layout/SideBar.vue'
import TopBar from '@/components/layout/TopBar.vue'
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
    async beforeMount() {
        await this.getUserData()
    },
    methods: {
        async getUserData() {
            await this.axios
                .get('/auth/getMyInfo')
                .then((res) => {
                    console.log(res.data)
                    if (res.data) {
                        this.user = {
                            shortName: res.data.shortName,
                            username: res.data.username,
                            role: res.data.role[0].authority,
                            email: res.data.email,
                        }
                        this.$router.push('/dashboard')
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
    padding: 0.5rem;
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
