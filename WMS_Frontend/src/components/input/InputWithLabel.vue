<template>
    <div class="wms-input-container">
        <label :for="id" class="Label-P2">{{ labelValue }}</label>
        <input
            :type="inputType"
            :id="id"
            :value="modelValue"
            @input="$emit('update:modelValue', $event.target.value)"
            class="Input-text-P1"
        />
    </div>
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
        error: {
            type: String,
            default: '',
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
