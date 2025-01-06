<script setup>
import Dialog from 'primevue/dialog'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import Stepper from 'primevue/stepper'
import StepList from 'primevue/steplist'
import StepPanels from 'primevue/steppanels'
import Step from 'primevue/step'
import StepPanel from 'primevue/steppanel'
import AutoComplete from 'primevue/autocomplete'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">{{ $t('units.addNewUnit') }}</h3>
        </template>
        <Stepper value="1">
            <StepList>
                <Step value="1">{{ $t('units.selectWorkers') }}</Step>
                <Step value="2">{{ $t('units.setPositions') }}</Step>
            </StepList>
            <StepPanels>
                <StepPanel v-slot="{ activateCallback }" value="1">
                    <DataTable
                        :value="users"
                        data-key="id"
                        :paginator="true"
                        :rows="10"
                        :rowsPerPageOptions="[5, 10, 20]"
                        :loading="loading"
                        showGridlines
                        selection-mode="multiple"
                        v-model:selection="selectedUsers"
                    >
                        <Column field="shortName" :header="$t('users.shortName')"></Column>
                        <Column field="position" :header="$t('users.currentPosition')"></Column>
                        <Column field="unit" :header="$t('units.unitName')"></Column>
                    </DataTable>
                    <div class="footer">
                        <Button
                            type="button"
                            :label="$t('form.next')"
                            @click="activateCallback('2')"
                            class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                            unstyled
                        />
                    </div>
                </StepPanel>
                <StepPanel value="2">
                    <DataTable
                        :value="selectedUsers"
                        data-key="id"
                        :paginator="true"
                        :rows="10"
                        :rowsPerPageOptions="[5, 10, 20]"
                        :loading="loading"
                        showGridlines
                    >
                        <Column field="shortName" :header="$t('users.shortName')"></Column>
                        <Column field="position" :header="$t('users.position')">
                            <template #body="{ data }">
                                <AutoComplete v-model="data.position" :suggestions="positions" />
                            </template>
                        </Column>
                    </DataTable>
                    <div class="footer">
                        <Button
                            type="button"
                            :label="$t('form.save')"
                            @click="send"
                            class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                            unstyled
                        />
                    </div>
                </StepPanel>
            </StepPanels>
        </Stepper>
    </Dialog>
</template>

<script>
export default {
    name: 'AddUnitWorkersDialog',
    data() {
        return {
            loading: true,
            visible: false,
            users: [],
            selectedUsers: [],
            positions: [],
        }
    },
    props: {
        unitId: {
            type: Number,
            required: true,
        },
        currentWorkers: {
            type: Array,
            required: true,
        },
    },
    methods: {
        async fetchUsers() {
            this.loading = true
            this.axios
                .get(`/user/getAllUsers`)
                .then((response) => {
                    this.users = response.data
                })
                .catch((error) => {
                    this.$refs.confirmDialogRef.open({
                        severity: 'error',
                        summary: 'Error',
                        detail: error.response.data.message,
                    })
                })
                .finally(() => {
                    this.loading = false
                })
            this.loading = false
        },
        async fetchPositions() {
            this.axios
                .get(`/position/all`)
                .then((response) => {
                    this.positions = response.data
                })
                .catch((error) => {
                    this.$refs.confirmDialogRef.open({
                        severity: 'error',
                        summary: 'Error',
                        detail: error.response.data.message,
                    })
                })
        },
        open() {
            this.visible = true
        },
        close() {
            this.visible = false
        },
        async send() {
            const positions = this.selectedUsers.map((user) => {
                return {
                    id: user.id,
                    name: user.position,
                }
            })
            console.log(positions)
            this.axios
                .post('/position/setUsers', {
                    unitId: this.unitId,
                    positionList: positions,
                })
                .then(() => {
                    this.$emit('refresh')
                    this.visible = false
                })
                .catch(() => {
                    this.$toast.add({
                        severity: 'error',
                        summary: this.$t('error'),
                        detail: this.$t('error.save'),
                        life: 3000,
                    })
                })
        },
    },
    watch: {
        async visible(val) {
            if (val) {
                this.selectedUsers = []
                this.fetchPositions()
                await this.fetchUsers()
            }
        },
    },
    expose: ['open'],
}
</script>

<style scoped>
.footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 1rem;
}
</style>
