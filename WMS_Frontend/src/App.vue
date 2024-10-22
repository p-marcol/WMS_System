<script setup>
import { RouterView } from 'vue-router'
import Toast from 'primevue/toast'
</script>

<template>
    <Toast position="bottom-right" />
    <RouterView />
</template>

<script>
export default {
    name: 'App',
    components: {
        RouterView,
    },
    async mounted() {
        if (this.$route.path === '/login') return
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
}
</script>
