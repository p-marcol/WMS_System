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
import UserDrawer from '@/components/drawer/UserDrawer.vue'
import {
    XMarkIcon,
    PencilSquareIcon,
    MagnifyingGlassCircleIcon,
    LockClosedIcon,
    LockOpenIcon,
    TrashIcon,
} from '@heroicons/vue/24/outline'
import AddNewUserDialog from '@/components/dialog/AddNewUserDialog.vue'
import ConfirmDialog from 'primevue/confirmdialog'
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
                :globalFilterFields="['id', 'concatName', 'email', 'authority']"
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
                    field="authority"
                    :header="$t('users.role')"
                    :filterMenuStyle="{ width: '14rem' }"
                    style="width: 9rem"
                >
                    <template #body="{ data }">
                        <Tag
                            class="wms-text-bold"
                            :value="data.authority"
                            :severity="data.severity"
                            rounded
                        />
                    </template>
                </Column>
                <Column :header="$t('table.actions')" style="width: 10rem">
                    <template #body="{ data }">
                        <div class="wms-action-column">
                            <MagnifyingGlassCircleIcon
                                class="wms-table-icon wms-action-view"
                                @click="openUserDrawer(data.id, false)"
                            />
                            <PencilSquareIcon
                                v-if="data.isEditable"
                                class="wms-table-icon wms-action-edit"
                                @click="openUserDrawer(data.id, true)"
                            />
                            <LockClosedIcon
                                v-if="!data.isArchived && data.isArchivable"
                                class="wms-table-icon wms-action-archive"
                                @click="archiveUser(data.id)"
                            />
                            <LockOpenIcon
                                v-if="data.isArchived && data.isArchivable"
                                class="wms-table-icon wms-action-archive"
                                @click="activateUser(data.id)"
                            />
                            <TrashIcon
                                v-if="data.isDeletable"
                                class="wms-table-icon wms-action-delete"
                                @click="deleteUser(data.id)"
                            />
                        </div>
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
                        {{ editUser ? $t('users.editUser') : $t('users.userDetails') }}
                    </h3>
                    <XMarkIcon @click="closeCallback" />
                </div>
                <div class="wms-drawer-body">
                    <UserDrawer @close="closeCallback" :userId="currentUserId" :edit="editUser" />
                </div>
            </div>
        </template>
    </Drawer>
    <AddNewUserDialog ref="addNewUserDialogRef" @refresh="fetchUsers" />
    <ConfirmDialog :draggable="false"> </ConfirmDialog>
</template>

<script>
const severityMap = {
    user: 'warn',
    manager: 'success',
    admin: 'info',
    'new user': 'secondary',
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
            editUser: false,
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
                    this.users = res.data
                    this.users.forEach((user) => {
                        user.severity = user.isArchived
                            ? 'secondary'
                            : severityMap[user.authority.toLowerCase()]
                        user.concatName = `${user.firstName} ${user.lastName}`
                    })
                    console.log(this.users)
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
        openUserDrawer(id, edit) {
            this.editUser = edit
            this.currentUserId = id
            this.upsertUserDrawerOpen = true
        },
        archiveUser(id) {
            this.$confirm.require({
                message: this.$t('users.confirm.archive'),
                header: this.$t('users.confirm.archiveHeader', { id: id }),
                rejectProps: {
                    label: this.$t('form.cancel'),
                    severity: 'secondary',
                    outlined: true,
                },
                acceptProps: {
                    label: this.$t('form.archive'),
                },
                accept: () => {
                    this.axios
                        .put(`/user/archiveUser/${id}`)
                        .then(() => {
                            this.fetchUsers()
                        })
                        .catch(() => {
                            this.$toast.add({
                                severity: 'error',
                                summary: this.$t('error'),
                                detail: this.$t('users.error.archive'),
                                life: 3000,
                            })
                        })
                },
                reject: () => {},
            })
        },
        activateUser(id) {
            this.axios
                .put(`/user/activateUser/${id}`)
                .then(() => {
                    this.fetchUsers()
                })
                .catch(() => {
                    this.$toast.add({
                        severity: 'error',
                        summary: this.$t('error'),
                        detail: this.$t('users.error.activate'),
                        life: 3000,
                    })
                })
        },
        deleteUser(id) {
            this.$confirm.require({
                message: this.$t('users.confirm.delete'),
                header: this.$t('users.confirm.deleteHeader', { id: id }),
                rejectProps: {
                    label: this.$t('form.cancel'),
                    severity: 'secondary',
                    outlined: true,
                },
                acceptProps: {
                    label: this.$t('form.delete'),
                    severity: 'danger',
                },
                accept: () => {
                    this.axios
                        .delete(`/user/deleteUser/${id}`)
                        .then(() => {
                            this.$toast.add({
                                severity: 'success',
                                summary: this.$t('success'),
                                detail: this.$t('users.success.delete'),
                                life: 3000,
                            })
                            this.fetchUsers()
                        })
                        .catch(() => {
                            this.$toast.add({
                                severity: 'error',
                                summary: this.$t('error'),
                                detail: this.$t('users.error.delete'),
                                life: 3000,
                            })
                        })
                },
                reject: () => {},
            })
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
