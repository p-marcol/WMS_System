<script setup>
import CardContainer from '../CardContainer.vue'
import TeamCardUserView from '../TeamCardUserView.vue'
</script>

<template>
    <CardContainer :title="$t('card.yourTeam')" :loading="loading">
        <div id="teamContainer">
            <TeamCardUserView v-for="user in team" :key="user.id" :user="user" />
        </div>
    </CardContainer>
</template>

<script>
export default {
    data() {
        return {
            loading: true,
            team: [],
        }
    },
    async mounted() {
        await this.getMyTeam()
    },
    methods: {
        async getMyTeam() {
            this.loading = true
            await this.axios
                .get('/unit/my/workers')
                .then((res) => {
                    this.team = res.data.workers.sort((a) => (a.position === 'MANAGER' ? -1 : 1))
                })
                .catch((err) => {
                    console.error(err)
                })
                .finally(() => {
                    this.loading = false
                })
        },
    },
}
</script>

<style scoped>
#teamContainer {
    display: flex;
    gap: 0.7rem;
    flex-direction: column;
}
</style>
