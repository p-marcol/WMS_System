<script setup>
import MainLayout from '@/components/layout/MainLayout.vue'
import CardContainer from '@/components/CardContainer.vue'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import AddTimesheetRecordDialog from '@/components/dialog/AddTimesheetRecordDialog.vue'
import ChangeDateRangeDialog from '@/components/dialog/ChangeDateRangeDialog.vue'
import { DateTime } from 'luxon'
import { XMarkIcon, CheckIcon } from '@heroicons/vue/24/outline'
</script>

<template>
    <MainLayout ref="mainLayoutRef" @user-fetched="updateView">
        <CardContainer id="card" :title="pendingMode ? $t('card.pending') : $t('title.timesheet')">
            <template #header>
                <div id="header">
                    <div v-if="!pendingMode">
                        <span class="Text-P1-bold">
                            {{ this.format(dateRange.startDate) }}
                        </span>
                        <span class="Text-P1-bold"> - {{ this.format(dateRange.endDate) }} </span>
                    </div>
                    <Button
                        v-if="!pendingMode"
                        type="button"
                        :label="$t('timesheet.addRecord')"
                        @click="openDialog"
                        class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                        unstyled
                    />
                    <Button
                        v-if="!pendingMode"
                        type="button"
                        :label="$t('timesheet.changeDateRange')"
                        @click="openDateDialog"
                        class="wms-small-button wms-small-button-secondary Small-button-primary-P1"
                        unstyled
                    />
                    <Button
                        v-if="pendingMode"
                        type="button"
                        :label="$t('timesheet.approveAll')"
                        @click="approveAll"
                        class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                        unstyled
                    />
                    <Button
                        v-if="showPendingButton"
                        type="button"
                        :label="pendingMode ? $t('timesheet.showMy') : $t('timesheet.showPending')"
                        @click="togglePendingMode"
                        class="wms-small-button wms-small-button-secondary Small-button-primary-P1"
                        unstyled
                    />
                </div>
            </template>
            <DataTable
                :value="timesheet"
                data-key="id"
                :paginator="true"
                :rows="10"
                :loading="loading"
            >
                <Column field="date" :header="$t('table.date')" style="width: 8rem" />
                <Column
                    v-if="pendingMode"
                    field="userShortName"
                    :header="$t('users.shortName')"
                    style="width: 8rem"
                />
                <Column field="unit" :header="$t('table.unit')" style="width: 6rem" />
                <Column field="hours" :header="$t('table.duration')" style="width: 6rem" />
                <Column field="description" :header="$t('table.description')" />
                <Column
                    v-if="!pendingMode"
                    field="isApproved"
                    :header="$t('table.approved')"
                    style="width: 6rem"
                />
                <Column
                    v-if="pendingMode"
                    field="actions"
                    :header="$t('table.actions')"
                    style="width: 6rem"
                >
                    <template #body="{ data }">
                        <CheckIcon
                            v-if="!data.state"
                            class="wms-table-icon wms-action-view"
                            @click="approveRecord(data)"
                        />
                        <XMarkIcon
                            v-if="!data.state"
                            class="wms-table-icon wms-action-view"
                            @click="rejectRecord(data)"
                        />
                        <span v-if="data.state === 'approved'">{{ $t('table.approved') }}</span>
                        <span v-if="data.state === 'rejected'">{{ $t('table.rejected') }}</span>
                    </template>
                </Column>
            </DataTable>
        </CardContainer>
        <ChangeDateRangeDialog
            ref="changeDateRangeDialog"
            :dateRange="dateRange"
            @update="changeDateRange"
        />
        <AddTimesheetRecordDialog ref="addTimesheetRecordDialog" @refresh="fetchData" />
    </MainLayout>
</template>

<script>
export default {
    name: 'TimesheetPage',
    data() {
        return {
            pendingMode: false,
            loading: true,
            timesheet: [],
            showPendingButton: false,
            dateRange: {
                startDate: DateTime.now().startOf('week'),
                endDate: DateTime.now().endOf('week'),
            },
        }
    },
    computed: {
        today() {
            return DateTime.now()
        },
        user() {
            return this.$refs.mainLayoutRef.user
        },
    },
    async mounted() {
        await this.fetchData()
    },
    methods: {
        changeDateRange(dateRange) {
            this.dateRange = dateRange
            this.fetchData()
        },
        openDateDialog() {
            this.$refs.changeDateRangeDialog.open()
        },
        format(date) {
            return date.toFormat('yyyy-MM-dd')
        },
        updateView() {
            if (this.user.role === 'ADMIN' || this.user.role === 'MANAGER') {
                this.showPendingButton = true
            }
        },
        async fetchData() {
            if (this.pendingMode) {
                await this.fetchPending()
            } else {
                await this.fetchMy()
            }
        },
        async fetchMy() {
            this.loading = true
            await this.axios
                .get(
                    `/timesheet/my/${this.format(this.dateRange.startDate)}/${this.format(this.dateRange.endDate)}`
                )
                .then((response) => {
                    console.log(response.data)
                    this.timesheet = response.data.map((record) => {
                        let isApproved
                        if (record.approved) {
                            isApproved = 'Yes'
                        } else if (!record.approved && !record.rejected) {
                            isApproved = 'Pending'
                        } else {
                            isApproved = 'No'
                        }
                        return { ...record, isApproved: isApproved }
                    })
                })
                .catch((error) => {
                    console.error(error)
                })
                .finally(() => {
                    this.loading = false
                })
        },
        async fetchPending() {
            this.loading = true
            await this.axios
                .get(`/timesheet/all/pending`)
                .then((response) => {
                    this.timesheet = response.data.map((record) => {
                        return { ...record }
                    })
                    if (this.timesheet.length === 0) {
                        this.$toast.add({
                            life: 5000,
                            severity: 'info',
                            summary: this.$t('info.timesheet.nothing'),
                        })
                    }
                })
                .catch((error) => {
                    console.error(error)
                })
                .finally(() => {
                    this.loading = false
                })
        },
        openDialog() {
            this.$refs.addTimesheetRecordDialog.open()
        },
        async togglePendingMode() {
            this.pendingMode = !this.pendingMode
            await this.fetchData()
        },
        async approveRecord(record) {
            await this.axios
                .put(`/timesheet/approve/${record.id}`)
                .then(() => {
                    record.state = 'approved'
                })
                .catch((error) => {
                    this.$toast.add({
                        life: 5000,
                        severity: 'error',
                        summary: this.$t('error.timesheet.approve'),
                        detail: error.response.data.message,
                    })
                })
        },
        async rejectRecord(record) {
            await this.axios
                .put(`/timesheet/reject/${record.id}`)
                .then(() => {
                    record.state = 'rejected'
                })
                .catch((error) => {
                    this.$toast.add({
                        life: 5000,
                        severity: 'error',
                        summary: this.$t('error.timesheet.reject'),
                        detail: error.response.data.message,
                    })
                })
        },
        async approveAll() {
            await this.axios
                .put(`/timesheet/approve/all`)
                .then(() => {
                    this.$toast.add({
                        life: 5000,
                        severity: 'success',
                        summary: this.$t('success.timesheet.approveAll'),
                    })
                    this.togglePendingMode()
                })
                .catch((error) => {
                    this.$toast.add({
                        life: 5000,
                        severity: 'error',
                        summary: this.$t('error.timesheet.approveAll'),
                        detail: error.response.data.message,
                    })
                })
        },
    },
}
</script>

<style scoped>
#card {
    grid-area: 13 / 1 / 1 / 13;
}

#header {
    display: grid;
    grid-auto-flow: column;
    align-items: center;
    gap: 1rem;
}
</style>
