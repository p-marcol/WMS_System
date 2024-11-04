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
import UserDrawer from '@/components/drawer/EditUserDrawer.vue'
import { XMarkIcon, PencilSquareIcon } from '@heroicons/vue/24/outline'
import AddNewUserDialog from '@/components/dialog/AddNewUserDialog.vue'
</script>

<template>
    <MainLayout>
        <CardContainer id="card" :title="$t('users.users')">
            <template #header>
                <Button
                    type="button"
                    :label="$t('users.newUser')"
                    @click="openAddNewUserDialog"
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
                <Column field="username" :header="$t('users.username')" />
                <Column field="firstName" :header="$t('users.firstName')" />
                <Column field="lastName" :header="$t('users.lastName')" />
                <Column field="email" :header="$t('users.email')" />
                <Column
                    field="role"
                    :header="$t('users.role')"
                    :filterMenuStyle="{ width: '14rem' }"
                    style="width: 6rem"
                >
                    <template #body="{ data }">
                        <Tag
                            class="wms-text-bold"
                            :value="data.role"
                            :severity="data.severity"
                            rounded
                        />
                    </template>
                </Column>
                <Column :header="$t('table.actions')" style="width: 10rem">
                    <template #body="{ data }">
                        <PencilSquareIcon
                            class="wms-table-icon"
                            @click="openEditUserDrawer(data.id)"
                        />
                    </template>
                </Column>
            </DataTable>
        </CardContainer>
    </MainLayout>
    <Drawer v-model:visible="upsertUserDrawerOpen" position="right">
        <template #container="{ closeCallback }">
            <div class="wms-drawer">
                <div class="wms-drawer-header">
                    <h3 class="Header-P3">
                        {{ currentUserId ? $t('users.editUser') : $t('users.newUser') }}
                    </h3>
                    <XMarkIcon @click="closeCallback" />
                </div>
                <div class="wms-drawer-body">
                    <UserDrawer @close="closeCallback" :userId="currentUserId" />
                </div>
            </div>
        </template>
    </Drawer>
    <AddNewUserDialog ref="addNewUserDialogRef" />
</template>

<script>
const severityMap = {
    user: 'warn',
    manager: 'success',
    admin: 'info',
}

export default {
    name: 'UsersPage',
    components: {
        CardContainer,
        DataTable,
        Column,
        InputText,
        Tag,
        AddNewUserDialog,
    },
    data() {
        return {
            users: null,
            loading: true,
            filters: null,
            error: false,
            upsertUserDrawerOpen: false,
            showDialog: false,
            currentUserId: null,
            addNewUserDialogRef: AddNewUserDialog,
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
            this.loading = true
            this.axios
                .get('/user/getAllUsers')
                .then((res) => {
                    this.users = res.data.map((user) => {
                        return {
                            id: user.id,
                            username: user.username,
                            firstName: user.firstName,
                            lastName: user.lastName,
                            email: user.email,
                            role: user.authority,
                            severity: user.isArchived
                                ? 'secondary'
                                : severityMap[user.authority.toLowerCase()],
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
        openAddNewUserDialog() {
            this.$refs.addNewUserDialogRef.open()
        },
        openEditUserDrawer(id) {
            this.currentUserId = id
            this.upsertUserDrawerOpen = true
        },
        openUserDrawer() {
            this.currentUserId = null
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
