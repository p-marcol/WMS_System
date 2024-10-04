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
    props: ['label', 'modelValue', 'type'],
    emits: ['update:modelValue'],
    data() {
        return {
            labelValue: this.label,
            id: this.label.toLowerCase(),
            inputType: this.type || 'text',
        }
    },
}
</script>
