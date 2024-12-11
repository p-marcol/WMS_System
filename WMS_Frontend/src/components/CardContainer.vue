<script setup>
import ProgressSpinner from 'primevue/progressspinner'
</script>

<template>
    <div :id="id" class="card">
        <div id="cardHeader">
            <h1 class="Header-P2">{{ this.title }}</h1>
            <slot name="header" />
        </div>
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
    padding: 0.5rem 1rem;
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
    display: grid;
    grid-template-rows: auto 1fr;
    overflow: auto;
}

.card > #cardHeader {
    display: grid;
    grid-template-columns: auto;
    grid-auto-columns: auto;
    grid-auto-flow: column;
    align-items: center;
    justify-content: space-between;
}

* {
    scrollbar-width: auto;
    scrollbar-color: var(--color-secondary) rgba(0, 0, 0, 0);
}

*::-webkit-scrollbar {
    width: 10px;
}

*::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0);
}

*::-webkit-scrollbar-thumb {
    background-color: var(--color-secondary);
    border-radius: 10px;
    border: 4px solid rgba(0, 0, 0, 0);
}
</style>
