<script setup>
import { RouterLink } from 'vue-router'
</script>

<template>
    <RouterLink :to="route">
        <slot></slot>
        <div class="Sidebar-P1 label">{{ label }}</div>
    </RouterLink>
</template>

<script>
export default {
    name: 'IconWithLabel',
    props: {
        label: {
            type: String,
            required: true,
        },
        route: {
            type: String,
            default: '',
        },
    },
}
</script>

<style scoped>
a {
    color: var(--color-white);
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
    user-select: none;
}

a > :not(.label) {
    width: 60px;
    height: 60px;
}
</style>
