<template>
    <span class="wms-input-container" :class="required && 'wms-required'">
        <div id="header" class="Label-P2">
            <label v-if="label" :for="labelFor">{{ label }}</label>
            <slot name="header" />
        </div>
        <slot></slot>
    </span>
</template>

<script>
export default {
    props: {
        label: {
            type: String,
            required: false,
            default: null,
        },
        labelFor: {
            type: String,
            required: false,
            default: null,
        },
        required: {
            type: Boolean,
            required: false,
            default: false,
        },
    },
}
</script>

<style scoped>
#header {
    display: grid;
    grid-template-columns: auto;
    grid-auto-columns: auto;
    grid-auto-flow: column;
    justify-content: space-between;
}
</style>
