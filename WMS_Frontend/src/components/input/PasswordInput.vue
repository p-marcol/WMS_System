<script setup>
import { EyeIcon, EyeSlashIcon } from '@heroicons/vue/24/outline'
</script>

<template>
    <div class="wms-input-container">
        <label :for="id" class="Label-P2">{{ $t('login.password') }}</label>
        <input
            :id="id"
            :value="modelValue"
            @input="$emit('update:modelValue', $event.target.value)"
            :type="mask ? 'password' : 'text'"
            class="Input-text-P1"
            unstyled
        />
        <span @click="() => (this.mask = !this.mask)">
            <EyeIcon v-if="mask" />
            <EyeSlashIcon v-else />
        </span>
    </div>
</template>

<script>
export default {
    components: {
        EyeIcon,
        EyeSlashIcon,
    },
    props: ['modelValue'],
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

span {
    margin: 6px 10px;
    padding: 0;
    position: absolute;
    right: 0;
    bottom: 0;
    cursor: pointer;
}

span * {
    height: 2rem;
    color: var(--color-secondary);
}
</style>
