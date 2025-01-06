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
            v-if="saveButton"
            unstyled
        />
        <Button
            type="button"
            @click="cancel"
            :label="$t('form.cancel')"
            class="wms-small-button wms-small-button-cancel Small-button-secondary-P1"
            v-if="cancelButton"
            unstyled
        />
    </div>
</template>

<script>
export default {
    name: 'SaveCancelButtons',
    props: {
        saveButton: {
            type: Boolean,
            default: false,
        },
        cancelButton: {
            type: Boolean,
            default: false,
        },
    },
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
    grid-auto-columns: 1fr;
    grid-auto-flow: column;
    gap: 0.5rem;
}

div > button {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
