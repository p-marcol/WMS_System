<script setup>
import Dialog from 'primevue/dialog'
import InputContainer from '../input/InputContainer.vue'
import DatePicker from 'primevue/datepicker'
import SaveCancelButtons from '../input/SaveCancelButtons.vue'
import { DateTime } from 'luxon'
import { PlusCircleIcon, MinusCircleIcon } from '@heroicons/vue/24/outline'
import Select from 'primevue/select'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">
                {{
                    unitId
                        ? $t('schedule.addToUnit', { id: unitId })
                        : $t('schedule.addToUser', { id: userId })
                }}
            </h3>
        </template>

        <div :class="userId && 'wms-col-2'">
            <InputContainer :label="$t('schedule.startDate')" required>
                <DatePicker v-model="startDate" />
            </InputContainer>

            <InputContainer v-if="userId" :label="$t('schedule.endDate')">
                <DatePicker v-model="endDate" />
            </InputContainer>
        </div>
        <div class="block" v-for="block in blocks" :key="block.id">
            <div class="block_content">
                <InputContainer :label="$t('schedule.startDay')" required>
                    <Select v-model="block.startDay" :options="days" optionLabel="label" />
                </InputContainer>
                <InputContainer :label="$t('schedule.startTime')" required>
                    <DatePicker v-model="block.startHour" timeOnly />
                </InputContainer>
                <InputContainer :label="$t('schedule.endDay')" required>
                    <Select v-model="block.endDay" :options="days" optionLabel="label" />
                </InputContainer>
                <InputContainer :label="$t('schedule.endTime')" required>
                    <DatePicker v-model="block.endHour" timeOnly />
                </InputContainer>
                <InputContainer class="unit_selector" :label="$t('schedule.unit')" required>
                    <Select
                        v-model="block.unitId"
                        :options="units"
                        optionLabel="name"
                        optionValue="id"
                        placeholder="Select a unit"
                        showClear
                    />
                </InputContainer>
            </div>
            <MinusCircleIcon
                v-if="blocks.length > 1"
                class="icon"
                @click="blocks.splice(blocks.indexOf(block), 1)"
            />
        </div>
        <div id="addBlock" class="block">
            <PlusCircleIcon id="addBlock" class="icon" @click="addBlock" />
        </div>
        <template #footer>
            <SaveCancelButtons @save="send" @cancel="close" saveButton cancelButton />
        </template>
    </Dialog>
</template>

<script>
export default {
    name: 'AddScheduleDialog',
    data() {
        return {
            visible: false,
            unitId: null,
            userId: null,
            startDate: null,
            endDate: null,
            blocks: [],
            units: [],
        }
    },
    computed: {
        days() {
            return [
                { label: this.$t('weekday.sunday'), value: 0 },
                { label: this.$t('weekday.monday'), value: 1 },
                { label: this.$t('weekday.tuesday'), value: 2 },
                { label: this.$t('weekday.wednesday'), value: 3 },
                { label: this.$t('weekday.thursday'), value: 4 },
                { label: this.$t('weekday.friday'), value: 5 },
                { label: this.$t('weekday.saturday'), value: 6 },
            ]
        },
    },
    methods: {
        addBlock() {
            this.blocks.push({
                startDay: this.days[1],
                startHour: new Date(),
                endDay: this.days[1],
                endHour: DateTime.now().plus({ hours: 1 }).toJSDate(),
                unitId: null,
            })
        },
        reset() {
            this.startDate = DateTime.now().plus({ days: 1 }).toJSDate()
            this.endDate = null
            this.blocks = [
                {
                    startDay: this.days[1],
                    startHour: new Date(),
                    endDay: this.days[1],
                    endHour: DateTime.now().plus({ hours: 1 }).toJSDate(),
                    unitId: null,
                },
            ]
        },
        async fetchAllUnits() {
            this.axios
                .get('/unit/all')
                .then((response) => {
                    this.units = response.data
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        open(id, setUser) {
            if (setUser) {
                this.userId = id
                this.unitId = null
            } else {
                this.unitId = id
                this.userId = null
            }
            this.reset()
            this.visible = true
            this.fetchAllUnits()
        },
        close() {
            this.visible = false
        },
        async send() {
            const result = {
                startDate: this.startDate,
                endDate: this.endDate,
                userId: this.userId,
                unitId: this.unitId,
                scheduleBlocks: this.blocks.map((block) => {
                    return {
                        startDay: block.startDay.value,
                        startHour: DateTime.fromJSDate(block.startHour).toFormat('HH:mm'),
                        endDay: block.endDay.value,
                        endHour: DateTime.fromJSDate(block.endHour).toFormat('HH:mm'),
                        unitId: block.unitId,
                    }
                }),
            }
            console.log(result)
            this.axios
                .put('/schedule/create', result)
                .then(() => {
                    console.log('success')
                    this.$toast.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Schedule created successfully',
                        life: 3000,
                    })
                    this.close()
                })
                .catch((error) => {
                    console.error(error)
                    this.$toast.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to create schedule',
                        life: 3000,
                    })
                })
        },
    },
    expose: ['open'],
}
</script>

<style scoped>
.block {
    margin-top: 1rem;
    padding-top: 1rem;
    border-top: 3px dashed var(--color-primary);
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
}

.block_content {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1rem;
}

.unit_selector {
    grid-column: span 2;
}

.icon {
    height: 3rem;
    color: var(--color-black);
    cursor: pointer;
}
</style>
