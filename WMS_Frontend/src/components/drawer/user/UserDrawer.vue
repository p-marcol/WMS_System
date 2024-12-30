<script setup>
import ProgressSpinner from 'primevue/progressspinner'
import UserEditDrawer from '@/components/drawer/user/UserEditDrawer.vue'
import UserDetailsDrawer from '@/components/drawer/user/UserDetailsDrawer.vue'
</script>

<template>
    <template v-if="!loading">
        <UserDetailsDrawer v-if="!edit" :user="user" />
        <UserEditDrawer v-else :user="user" />
    </template>
    <template v-else>
        <div class="wms-drawer-content">
            <ProgressSpinner style="justify-self: center; align-self: center" stroke-width="5" />
        </div>
    </template>
</template>

<script>
export default {
    props: {
        userId: {
            type: Number,
            default: null,
        },
        edit: {
            type: Boolean,
            required: true,
        },
    },
    data() {
        return {
            user: Object,
            loading: {
                type: Boolean,
                default: true,
            },
        }
    },
    watch: {
        userId: {
            async handler(newId) {
                this.loading = true
                if (newId) {
                    await this.axios
                        .get(`/user/getDetails/${newId}`)
                        .then((response) => {
                            console.log(response.data)
                            this.user = response.data
                        })
                        .catch((err) => {
                            console.warn(err)
                        })
                        .finally(() => {})
                } else {
                    this.user = {}
                }
                this.loading = false
            },
            immediate: true,
        },
    },
}
</script>
