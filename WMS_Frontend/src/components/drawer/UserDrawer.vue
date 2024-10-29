<script setup>
import InputWithLabel from '@/components/input/InputWithLabel.vue'
import ProgressSpinner from 'primevue/progressspinner'
import regexRules from '@/data/regexRules'
</script>

<template>
    <div class="wms-drawer-content">
        <template v-if="!loading">
            <div class="wms-row-2">
                <InputWithLabel
                    label="First name"
                    :modelValue="user.firstName"
                    class="wms-required"
                />
                <InputWithLabel
                    label="Last name"
                    :modelValue="user.lastName"
                    class="wms-required"
                />
            </div>
            <InputWithLabel
                label="Email"
                :modelValue="user.email"
                class="wms-required"
                :pattern="regexRules.email"
            />
        </template>
        <template v-else>
            <ProgressSpinner style="justify-self: center; align-self: center" stroke-width="5" />
        </template>
    </div>
</template>

<script>
export default {
    props: {
        userId: {
            type: Number,
            default: null,
        },
    },
    data() {
        return {
            sourceUser: Object,
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
                            this.user = response.data
                            this.sourceUser = response.data
                        })
                        .catch((err) => {
                            console.warn(err)
                        })
                        .finally(() => {})
                } else {
                    this.user = {}
                    this.sourceUser = {}
                }
                this.loading = false
                console.log(this.user)
            },
            immediate: true,
        },
    },
}
</script>
