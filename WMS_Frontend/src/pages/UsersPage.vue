<script setup>
import { FilterMatchMode } from '@primevue/core/api'
import { FunnelIcon } from '@heroicons/vue/24/outline'
import Button from 'primevue/button'
import CardContainer from '@/components/CardContainer.vue'
import MainLayout from '@/components/layout/MainLayout.vue'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import InputText from 'primevue/inputtext'
import Tag from 'primevue/tag'
import Drawer from 'primevue/drawer'
</script>

<template>
    <MainLayout>
        <CardContainer id="card" :title="$t('users.users')">
            <template #header>
                <Button
                    type="button"
                    :label="$t('users.newUser')"
                    @click="openNewUserDrawer"
                    class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                    unstyled
                />
            </template>
            <DataTable
                :value="users"
                v-model:filters="filters"
                dataKey="id"
                :paginator="true"
                :rows="10"
                :loading="loading"
                showGridlines
                filterDisplay="menu"
                :globalFilterFields="['id', 'firstName', 'lastName', 'email', 'role']"
            >
                <template #header>
                    <div id="tableHeader">
                        <Button
                            type="button"
                            @click="clearFilter"
                            class="wms-small-button wms-small-button-secondary Small-button-secondary-P1"
                            unstyled
                        >
                            <FunnelIcon />
                            <p>{{ $t('filter.clear') }}</p>
                        </Button>
                        <InputText
                            id="search"
                            v-model="filters['global'].value"
                            class="wms-table-input Small-button-secondary-P1"
                            :placeholder="$t('filter.search') + '...'"
                            unstyled
                        />
                    </div>
                </template>
                <Column field="id" header="ID" style="width: 5rem" />
                <Column field="firstName" header="First name" />
                <Column field="lastName" header="Last name" />
                <Column field="email" header="Email" />
                <Column
                    field="role"
                    header="Role"
                    :filterMenuStyle="{ width: '14rem' }"
                    style="width: 6rem"
                >
                    <template #body="{ data }">
                        <Tag :value="data.role" :severity="data.severity" rounded />
                    </template>
                </Column>
                <Column header="Actions" style="width: 10rem">
                    <template #body="{ data }">
                        <p>Action column: {{ data.id }}</p>
                    </template>
                </Column>
            </DataTable>
        </CardContainer>
    </MainLayout>
    <Drawer v-model:visible="upsertUserDrawerOpen" header="right drawer" position="right"
        >drawer content</Drawer
    >
</template>

<script>
const severityMap = {
    user: 'warn',
    manager: 'success',
    admin: 'info',
}

export default {
    components: {
        CardContainer,
        DataTable,
        Column,
    },
    data() {
        return {
            users: null,
            loading: true,
            filters: null,
            error: false,
            upsertUserDrawerOpen: false,
        }
    },
    created() {
        this.initFilters()
    },
    mounted() {
        this.fetchUsers()
    },
    methods: {
        fetchUsers() {
            this.axios
                .get('/user/getAllUsers')
                .then((res) => {
                    this.users = res.data.map((user) => {
                        return {
                            id: user.id,
                            firstName: user.firstName,
                            lastName: user.lastName,
                            email: user.email,
                            role: user.authorities[0].authority,
                            severity: severityMap[user.authorities[0].authority.toLowerCase()],
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
        clearFilter() {
            this.initFilters()
        },
        initFilters() {
            this.filters = {
                global: {
                    value: null,
                    matchMode: FilterMatchMode.CONTAINS,
                },
            }
        },
        openNewUserDrawer() {
            this.upsertUserDrawerOpen = true
        },
    },
}
</script>

<style scoped>
#tableHeader {
    display: grid;
    grid-template-columns: auto auto;
    justify-content: space-between;
    gap: 0.5rem;
}
#card {
    grid-area: 13 / 1 / 1 / 13;
}
</style>
