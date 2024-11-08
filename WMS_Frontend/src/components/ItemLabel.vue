<template>
    <span class="wms-input-container" :class="required && 'wms-required'">
        <label v-if="label" :for="labelFor" class="Label-P2">{{ label }}</label>
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
    },
}
</script>
