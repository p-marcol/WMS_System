<script setup>
import Button from 'primevue/button'
import CardContainer from '@/components/CardContainer.vue'
import MainLayout from '@/components/layout/MainLayout.vue'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
</script>

<template>
    <MainLayout>
        <CardContainer id="card" :title="$t('users.users')">
            <DataTable
                :value="users"
                v-model:filters="filters"
                dataKey="id"
                :paginator="true"
                :rows="10"
                :loading="loading"
                showGridlines
            >
                <template #header>
                    <div id="tableHeader">
                        <Button
                            type="button"
                            :label="$t('filter.clear')"
                            @click="clearFilter"
                            class="wms-small-button-secondary Small-button-secondary-P1"
                            unstyled
                        />
                    </div>
                </template>
                <Column field="id" header="ID" />
                <Column field="firstName" header="First name" />
                <Column field="lastName" header="Last name" />
                <Column field="email" header="Email" />
                <Column field="role" header="Role">
                    <!-- <template #body="data">
                        <p>{{ data }}</p>
                    </template> -->
                </Column>
                <Column header="Actions">
                    <template #body>
                        <p>Action column</p>
                    </template>
                </Column>
            </DataTable>
        </CardContainer>
    </MainLayout>
</template>

<script>
export default {
    components: {
        CardContainer,
        DataTable,
        Column,
    },
    async mounted() {
        await this.axios
            .get('/user/getAllUsers')
            .then((res) => {
                this.users = res.data.map((user) => {
                    return {
                        id: user.id,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        email: user.email,
                        role: user.authorities[0].authority,
                    }
                })
            })
            .catch(() => {
                this.error = true
            })
            .finally(() => {
                this.loading = false
            })
    },
    data() {
        return {
            loading: true,
            error: false,
            users: [],
        }
    },
}
</script>

<style scoped>
#card {
    grid-area: 13 / 1 / 1 / 13;
}
</style>
