<script setup>
import Button from 'primevue/button'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import CardContainer from '@/components/CardContainer.vue'
import MainLayout from '@/components/layout/MainLayout.vue'
import AddNewUnitDialog from '@/components/dialog/AddNewUnitDialog.vue'
import ShowUnitUsersDialog from '@/components/dialog/ShowUnitUsersDialog.vue'
import Breadcrumb from 'primevue/breadcrumb'
import UnitDrawer from '@/components/drawer/unit/UnitDrawer.vue'
import Drawer from 'primevue/drawer'
import {
    HomeIcon,
    XMarkIcon,
    PencilSquareIcon,
    MagnifyingGlassCircleIcon,
    UsersIcon,
    RectangleStackIcon,
} from '@heroicons/vue/24/outline'
</script>

<template>
    <MainLayout>
        <CardContainer id="card" :title="$t('title.units')">
            <template #header>
                <Breadcrumb
                    :home="{ icon: HomeIcon, label: $t('br.home'), id: null }"
                    :model="breadcrumbs"
                >
                    <template #item="{ item }">
                        <span class="br-item" @click="changeCurrentUnitId(item.id)">{{
                            item.label
                        }}</span>
                    </template>
                </Breadcrumb>
                <Button
                    type="button"
                    :label="$t('units.newUnit')"
                    @click="openAddNewUnitDialog"
                    class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                    unstyled
                />
            </template>
            <DataTable
                :value="subunits"
                data-key="id"
                :paginator="true"
                :rows="10"
                :loading="loading"
                showGridlines
            >
                <Column field="id" header="ID" style="width: 5rem" />
                <Column field="name" :header="$t('units.name')" style="width: 10rem" />
                <Column field="description" :header="$t('units.description')" />
                <Column
                    field="workerCount"
                    :header="$t('units.workersCount')"
                    style="width: 5rem"
                />
                <Column
                    field="subunitCount"
                    :header="$t('units.subunitCount')"
                    style="width: 5rem"
                />
                <Column :header="$t('table.actions')" style="width: 10rem">
                    <template #body="{ data }">
                        <div class="wms-action-column">
                            <MagnifyingGlassCircleIcon
                                class="wms-table-icon wms-action-view"
                                @click="openUnitDrawer(data.id, false)"
                            />
                            <PencilSquareIcon
                                class="wms-table-icon wms-action-edit"
                                @click="openUnitDrawer(data.id, true)"
                            />
                            <RectangleStackIcon
                                v-if="data.subunitCount > 0"
                                class="wms-table-icon wms-action-archive"
                                @click="changeCurrentUnitId(data.id)"
                            />
                            <UsersIcon
                                class="wms-table-icon wms-action-delete"
                                @click="openShowUnitUsersDialog(data.id)"
                            />
                        </div>
                    </template>
                </Column>
            </DataTable>
        </CardContainer>
    </MainLayout>
    <Drawer v-model:visible="upsertUnitDrawerOpen" position="right">
        <template #container="{ closeCallback }">
            <div class="wms-drawer">
                <div class="wms-drawer-header">
                    <h3 class="Header-P3">
                        {{ editUnit ? $t('units.editUnit') : $t('units.unitDetails') }}
                    </h3>
                    <XMarkIcon @click="closeCallback" />
                </div>
                <div class="wms-drawer-body">
                    <UnitDrawer
                        @close="closeCallback"
                        :unitId="selectedUnitId"
                        :edit="editUnit"
                        @reload="fetchData"
                    />
                </div>
            </div>
        </template>
    </Drawer>
    <AddNewUnitDialog ref="addNewUnitDialogRef" @reload="fetchData" />
    <ShowUnitUsersDialog ref="showUnitUsersDialogRef" @reload="fetchData" />
</template>

<script>
export default {
    name: 'UnitsPage',
    components: {
        CardContainer,
        MainLayout,
        Breadcrumb,
        DataTable,
    },
    data() {
        return {
            loading: true,
            currentUnitId: null,
            selectedUnitId: null,
            breadcrumbs: [],
            subunits: null,
            upsertUnitDrawerOpen: false,
            editUnit: false,
            unitUsersDialogOpen: false,
        }
    },
    async mounted() {
        await this.fetchData()
    },
    methods: {
        async fetchData() {
            this.loading = true
            await this.getUnits()
            this.getParentUnits()
            this.loading = false
        },
        async getParentUnits() {
            if (!this.currentUnitId) {
                this.breadcrumbs = []
                return
            }
            this.axios.get(`/unit/parentUnits/${this.currentUnitId}`).then((response) => {
                this.breadcrumbs = response.data.map((unit) => {
                    return { label: unit.name, id: unit.id }
                })
            })
        },
        async getUnits() {
            console.log('getUnits')
            await this.axios.get(`/unit/subunits/${this.currentUnitId || ''}`).then((response) => {
                // console.log(response.data)
                this.subunits = response.data
            })
        },
        async changeCurrentUnitId(id) {
            this.loading = true
            this.currentUnitId = id
            await this.getUnits()
            await this.getParentUnits()
            this.loading = false
        },
        openUnitDrawer(id, edit) {
            this.editUnit = edit
            this.selectedUnitId = id
            this.upsertUnitDrawerOpen = true
        },
        openAddNewUnitDialog() {
            this.$refs.addNewUnitDialogRef.open()
        },
        openShowUnitUsersDialog(id) {
            this.$refs.showUnitUsersDialogRef.open(id)
        },
    },
}
</script>

<style scoped>
#card {
    grid-area: 13 / 1 / 1 / 13;
}
.br-item {
    cursor: pointer;
}
</style>
