<template>
    <div id="top-bar">
        <p class="Topbar-info">{{ teamName }}</p>
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
        team: {
            type: String,
            required: true,
        },
    },
    computed: {
        teamName() {
            if (this.team === null) {
                return ''
            }
            return this.team.unitName
        },
    },
    watch: {
        user() {
            this.userShortName = this.user.shortName
        },
    },
    data() {
        return {
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
