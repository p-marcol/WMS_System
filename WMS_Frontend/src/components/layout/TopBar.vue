<script setup>
import Select from 'primevue/select'
</script>

<template>
    <div id="top-bar">
        <Select v-model="selectedOption" :options="options" :optionLabel="value" />
        <p class="Topbar-user-P1">{{ this.userShortName }}</p>
    </div>
</template>

<script>
export default {
    name: 'TopBar',
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
    watch: {
        user() {
            this.userShortName = this.user.shortName
        },
    },
    data() {
        return {
            options: [
                { label: 'Option 1', value: 1 },
                { label: 'Option 2', value: 2 },
                { label: 'Option 3', value: 3 },
            ],
            selectedOption: null,
            userShortName: null,
        }
    },
}
</script>

<style scoped>
#top-bar {
    height: 80px;
    width: 100%;
    background-color: var(--color-white);
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.25);
    padding: 0 30px;
    z-index: 1000;
}
</style>
