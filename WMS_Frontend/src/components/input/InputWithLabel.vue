<template>
    <span class="wms-input-container">
        <label :for="id" class="Label-P2">{{ labelValue }}</label>
        <input
            :type="inputType"
            :id="id"
            :value="modelValue"
            @input="$emit('update:modelValue', $event.target.value)"
            :pattern="pattern"
            class="Input-text-P1"
        />
    </span>
</template>

<script>
export default {
    props: {
        label: {
            type: String,
            required: true,
        },
        modelValue: {
            type: String,
            required: true,
        },
        type: {
            type: String,
            default: 'text',
        },
        required: {
            type: Boolean,
            default: false,
        },
        pattern: {
            type: Object,
            required: false,
            default: null,
        },
    },
    emits: ['update:modelValue'],
    data() {
        return {
            labelValue: this.label,
            id: this.label.toLowerCase().replace(' ', '-'),
            inputType: this.type || 'text',
        }
    },
}
</script>
