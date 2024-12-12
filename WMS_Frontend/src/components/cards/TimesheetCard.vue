<script setup>
import CardContainer from '@/components/CardContainer.vue'
import { FaceFrownIcon } from '@heroicons/vue/24/outline'
import { DateTime } from 'luxon'
import Button from 'primevue/button'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import AddTimesheetRecordDialog from '../dialog/AddTimesheetRecordDialog.vue'
</script>

<template>
    <CardContainer :title="$t('card.timesheet')" id="timesheet" :loading="loading">
        <template #header>
            <div id="header">
                <span class="Text-P1-bold">{{ DateTime.now().toFormat('yyyy-MM-dd') }}</span>
                <div id="buttons">
                    <Button
                        type="button"
                        :label="$t('timesheet.addRecord')"
                        @click="openDialog"
                        class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                        unstyled
                    />
                    <Button
                        type="button"
                        :label="$t('timesheet.openTS')"
                        @click="
                            () => {
                                this.$router.push('/timesheet')
                            }
                        "
                        class="wms-small-button wms-small-button-secondary Small-button-primary-P1"
                        unstyled
                    />
                </div>
            </div>
        </template>
        <div v-if="!records.length" id="nothing">
            <div>
                <FaceFrownIcon class="faceIcon" />
                <span class="Label-P3">{{ $t('timesheet.nothingHere') }}</span>
            </div>
        </div>
        <p v-else>
            <DataTable :value="records" data-key="id">
                <Column field="date" :header="$t('table.date')" :style="{ width: '8rem' }" />
                <Column field="unit" :header="$t('table.unit')" :style="{ width: '8rem' }" />
                <Column field="description" :header="$t('table.description')" />
                <Column field="hours" :header="$t('table.duration')" :style="{ width: '4rem' }" />
                <Column
                    field="isApproved"
                    :header="$t('table.approved')"
                    :style="{ width: '4rem' }"
                />
            </DataTable>
        </p>
    </CardContainer>
    <AddTimesheetRecordDialog ref="addTimesheetRecordDialog" @refresh="refresh" />
</template>

<script>
export default {
    name: 'TimesheetCard',
    data() {
        return {
            records: [],
            loading: false,
        }
    },
    computed: {
        today() {
            return DateTime.now().toFormat('yyyy-MM-dd')
        },
    },
    mounted() {
        this.fetchRecords()
    },
    methods: {
        async fetchRecords() {
            this.loading = true
            await this.axios
                .get(`/timesheet/my/${this.today}`)
                .then((response) => {
                    this.records = response.data.map(
                        (record) =>
                            record && { isApproved: record.isApproved ? 'Yes' : 'No', ...record }
                    )
                    console.log(response.data)
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
        refresh() {
            this.fetchRecords()
        },
    },
}
</script>

<style scoped>
#header {
    display: grid;
    grid-template-columns: auto auto;
    align-items: center;
    gap: 1rem;
}
#buttons {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1rem;
}

#nothing {
    display: grid;
    place-items: center;
    height: 100%;
}

#nothing > div {
    display: grid;
    text-align: center;
}

.faceIcon {
    width: 100px;
    height: 100px;
    margin: 0 auto;
    color: var(--color-secondary);
}
</style>
