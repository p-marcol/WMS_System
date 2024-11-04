<script setup>
import Button from 'primevue/button'
</script>

<template>
    <div>
        <Button
            type="button"
            @click="save"
            :label="$t('form.save')"
            class="wms-small-button wms-small-button-save Small-button-primary-P1"
            unstyled
        />
        <Button
            type="button"
            @click="cancel"
            :label="$t('form.cancel')"
            class="wms-small-button wms-small-button-cancel Small-button-secondary-P1"
            unstyled
        />
    </div>
</template>

<script>
export default {
    name: 'SaveCancelButtons',
    methods: {
        save() {
            this.$emit('save')
        },
        cancel() {
            this.$emit('cancel')
        },
    },
    emits: ['save', 'cancel'],
}
</script>

<style scoped>
div {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 0.5rem;
}
</style>
