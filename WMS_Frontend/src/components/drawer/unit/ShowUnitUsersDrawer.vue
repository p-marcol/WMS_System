<script setup>
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import { ArrowLeftStartOnRectangleIcon } from '@heroicons/vue/24/outline'
import Drawer from 'primevue/drawer'
import { XMarkIcon } from '@heroicons/vue/24/outline'
import ConfirmDialog from 'primevue/confirmdialog'
import ItemLabel from '@/components/ItemLabel.vue'
import Button from 'primevue/button'
import AddUnitWorkersDialog from '@/components/dialog/AddUnitWorkersDialog.vue'
</script>

<template>
    <Drawer v-model:visible="visible" position="right">
        <template #container="{ closeCallback }">
            <div class="wms-drawer">
                <div class="wms-drawer-header">
                    <h3 class="Header-P3">{{ $t('units.unitUsers') }}</h3>
                    <XMarkIcon @click="closeCallback" />
                </div>
                <div class="wms-drawer-body">
                    <div class="wms-col-3">
                        <ItemLabel :label="$t('units.id')">
                            <span class="Header-P4">{{ id }}</span>
                        </ItemLabel>
                        <ItemLabel :label="$t('units.name')">
                            <span class="Header-P4">{{ unitName }}</span>
                        </ItemLabel>
                        <div class="wms-row">
                            <Button
                                type="button"
                                :label="$t('units.addWorkers')"
                                @click="openAddWorkersDialog"
                                class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                                unstyled
                            />
                        </div>
                    </div>
                    <DataTable
                        :value="users"
                        :paginator="true"
                        :rows="10"
                        :rowsPerPageOptions="[5, 10, 20]"
                    >
                        <Column field="shortName" :header="$t('users.shortName')"></Column>
                        <Column field="position" :header="$t('users.position')"></Column>
                        <Column :header="$t('table.actions')">
                            <template #body="{ data }">
                                <ArrowLeftStartOnRectangleIcon
                                    class="wms-table-icon"
                                    @click="deleteUser(data.userId)"
                                />
                            </template>
                        </Column>
                    </DataTable>
                </div>
            </div>
        </template>
    </Drawer>
    <ConfirmDialog :draggable="false" />
    <AddUnitWorkersDialog
        :unitId="id"
        :currentWorkers="users"
        ref="addUnitWorkersDialogRef"
        @refresh="reload"
    />
</template>

<script>
export default {
    name: 'ShowUnitUsersDialog',
    data() {
        return {
            id: null,
            visible: false,
            users: [],
            loading: false,
            unitName: '',
        }
    },
    methods: {
        reload() {
            this.fetchUnitUsers()
            this.$emit('refresh')
        },
        openAddWorkersDialog() {
            this.$refs.addUnitWorkersDialogRef.open()
        },
        fetchUnitUsers() {
            this.loading = true
            this.axios
                .get(`/unit/${this.id}/workers`)
                .then((res) => {
                    console.log(res.data.workers)
                    this.unitName = res.data.unitName
                    this.users = res.data.workers
                })
                .catch((err) => {
                    console.error(err)
                })
                .finally(() => {
                    this.loading = false
                })
        },
        open(id) {
            this.id = id
            this.visible = true
        },
        close() {
            this.visible = false
        },
        deleteUser(id) {
            this.$confirm.require({
                message: this.$t('units.confirm.delete'),
                header: this.$t('units.confirm.deleteHeader', { id: id }),
                rejectProps: {
                    label: this.$t('form.cancel'),
                    severity: 'secondary',
                    outlined: true,
                },
                acceptProps: {
                    label: this.$t('form.delete'),
                    severity: 'danger',
                    outlined: true,
                },
                accept: () => {
                    this.axios
                        .delete(`/position/drop/${id}`)
                        .then(() => {})
                        .catch(() => {
                            this.$toast.add({
                                severity: 'error',
                                summary: this.$t('error'),
                                detail: this.$t('error.delete'),
                                life: 3000,
                            })
                        })
                    this.fetchUnitUsers()
                },
                reject: () => {},
            })
        },
    },
    watch: {
        visible(value) {
            if (value) {
                this.fetchUnitUsers()
            } else {
                this.users = []
            }
        },
    },
    expose: ['open'],
}
</script>

<style scoped>
.wms-col-2 {
    padding-bottom: 0.5rem;
    border-bottom: 1px solid var(--color-black);
    margin-bottom: 0.5rem;
}
</style>
