<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
    <RouterView />
</template>

<script>
export default {
    name: 'App',
    components: {
        RouterView,
    },
    beforeCreate() {
        console.log(this.$route.fullPath)
        this.axios
            .get('/auth/getMyInfo')
            .then((res) => {
                if (res.data) {
                    this.$router.push('/dashboard')
                }
            })
            .catch(() => {
                this.$router.push('/login')
            })
    },
}
</script>
