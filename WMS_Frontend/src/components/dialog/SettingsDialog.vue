<script setup>
import Dialog from 'primevue/dialog'
import LocaleChanger from '../LocaleChanger.vue'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">{{ $t('settings.settings') }}</h3>
        </template>
        <LocaleChanger />
    </Dialog>
</template>

<script>
export default {
    name: 'SettingsDialog',
    data() {
        return {
            visible: false,
        }
    },
    methods: {
        open() {
            console.log('open')
            this.visible = true
        },
        close() {
            this.visible = false
        },
    },
    expose: ['open'],
}
</script>

<style scoped></style>
