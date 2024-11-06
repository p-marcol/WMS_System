<script setup>
import InputText from 'primevue/inputtext'
import { EyeIcon, EyeSlashIcon } from '@heroicons/vue/24/outline'
import InputContainer from './InputContainer.vue'
</script>

<template>
    <InputContainer
        :label="confirm ? $t('login.confirmPassword') : $t('login.password')"
        :label-for="id"
    >
        <InputText
            :id="id"
            :value="modelValue"
            @input="$emit('update:modelValue', $event.target.value)"
            :type="mask ? 'password' : 'text'"
            class="Input-text-P1"
        />
        <span id="eye" @click="() => (this.mask = !this.mask)">
            <EyeIcon v-if="mask" />
            <EyeSlashIcon v-else />
        </span>
    </InputContainer>
</template>

<script>
export default {
    components: {
        EyeIcon,
        EyeSlashIcon,
    },
    props: {
        modelValue: {
            type: String,
            required: true,
        },
        confirm: {
            type: Boolean,
            default: false,
            required: false,
        },
    },
    emits: ['update:modelValue'],
    data() {
        return {
            id: 'password',
            mask: true,
        }
    },
}
</script>

<style scoped>
.wms-input-container {
    position: relative;
    max-height: fit-content;
}

input {
    padding-right: 50px;
}

#eye {
    margin: 3px 10px;
    padding: 0;
    position: absolute;
    right: 0;
    bottom: 0;
    cursor: pointer;
}

#eye * {
    height: 2rem;
    color: var(--color-secondary);
}
</style>
