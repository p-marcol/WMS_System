<script setup>
import Dialog from 'primevue/dialog'
import InputContainer from '@/components/input/InputContainer.vue'
import DatePicker from 'primevue/datepicker'
import Message from 'primevue/message'
import SaveCancelButtons from '@/components/input/SaveCancelButtons.vue'
import { DateTime } from 'luxon'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">{{ $t('timesheet.changeDateRange') }}</h3>
        </template>

        <InputContainer label="Start Date">
            <DatePicker v-model="startDate" />
        </InputContainer>
        <InputContainer label="End Date">
            <DatePicker v-model="endDate" />
        </InputContainer>
        <Message v-if="dateRangeInvalid" severity="error" text="Invalid date range">
            {{ $t('timesheet.invalidDateRange') }}
        </Message>

        <template #footer>
            <SaveCancelButtons @save="update" @cancel="close" saveButton cancelButton />
        </template>
    </Dialog>
</template>

<script>
export default {
    name: 'ChangeDateRangeDialog',
    data() {
        return {
            visible: false,
            startDate: null,
            endDate: null,
            dateRangeInvalid: null,
        }
    },
    props: {
        dateRange: {
            type: Object,
            required: true,
        },
    },
    methods: {
        open() {
            this.visible = true
        },
        close() {
            this.visible = false
        },
        update() {
            this.$emit('update', {
                startDate: DateTime.fromJSDate(this.startDate),
                endDate: DateTime.fromJSDate(this.endDate),
            })
            this.close()
        },
    },
    watch: {
        visible() {
            if (this.visible && this.dateRange) {
                this.startDate = this.dateRange.startDate.toJSDate()
                this.endDate = this.dateRange.endDate.toJSDate()
            }
        },
    },
    expose: ['open'],
    emits: ['update'],
}
</script>

<style scoped></style>
