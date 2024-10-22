<script setup>
import ProgressSpinner from 'primevue/progressspinner'
</script>

<template>
    <div :id="id" class="card">
        <h1 class="Header-P2">{{ this.title }}</h1>
        <ProgressSpinner
            v-if="loading"
            style="justify-self: center; align-self: center"
            stroke-width="5"
        />
        <div v-else>
            <slot />
        </div>
    </div>
</template>

<script>
export default {
    name: 'CardContainer',
    props: {
        title: {
            type: String,
            required: true,
        },
        id: {
            type: String,
            default: '',
        },
        loading: {
            type: Boolean,
            default: false,
        },
    },
}
</script>

<style scoped>
.card {
    background-color: var(--color-white);
    border-radius: 10px;
    padding: 1rem;
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
    display: grid;
    grid-template-rows: auto 1fr;
}
</style>
