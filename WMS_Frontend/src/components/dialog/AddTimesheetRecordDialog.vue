<script setup>
import SaveCancelButtons from '../input/SaveCancelButtons.vue'
import Select from 'primevue/select'
import Dialog from 'primevue/dialog'
import { Form } from '@primevue/forms'
import InputContainer from '../input/InputContainer.vue'
import InputNumber from 'primevue/inputnumber'
import Message from 'primevue/message'
import DatePicker from 'primevue/datepicker'
import Textarea from 'primevue/textarea'
import * as v from 'valibot'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">{{ $t('timesheet.addRecord') }}</h3>
        </template>
        <Form id="modalBody" @submit="submit">
            <InputContainer :label="$t('table.unit')" required>
                <Select
                    v-model="selectedUnit"
                    :options="units"
                    optionLabel="name"
                    optionValue="id"
                    placeholder="Select a unit"
                    editable
                    required
                />
                <Message v-if="errors.unitInvalid" severity="error" text="Invalid unit">
                    {{ errors.unitInvalid }}
                </Message>
            </InputContainer>
            <InputContainer :label="$t('table.duration')" required>
                <InputNumber
                    v-model="hours"
                    :invalid="hoursInvalid"
                    mode="decimal"
                    :min="0.25"
                    :max="24"
                    :step="0.25"
                    showButtons
                    required
                />
                <Message v-if="errors.hoursInvalid" severity="error" text="Invalid hours">
                    {{ errors.hoursInvalid }}
                </Message>
            </InputContainer>
            <InputContainer :label="$t('table.date')" required>
                <DatePicker v-model="date" dateFormat="yy-mm-dd" required />
                <Message v-if="errors.dateInvalid" severity="error" text="Invalid date">
                    {{ errors.dateInvalid }}
                </Message>
            </InputContainer>
            <InputContainer :label="$t('table.description')" required>
                <template #header>
                    <span>{{ desc.length }}/{{ maxDescLength }}</span>
                </template>
                <Textarea v-model="desc" rows="5" autoResize required />
                <Message
                    v-if="errors.descriptionInvalid"
                    severity="error"
                    text="Invalid description"
                >
                    {{ errors.descriptionInvalid }}
                </Message>
            </InputContainer>
        </Form>
        <template #footer>
            <SaveCancelButtons @save="send" @cancel="close" />
        </template>
    </Dialog>
</template>

<script>
export default {
    name: 'AddTimesheetRecordDialog',
    data() {
        return {
            maxDescLength: 255,
            selectedUnit: null,
            visible: false,
            units: null,
            hours: 1,
            desc: '',
            date: new Date(),
            errors: {
                hoursInvalid: null,
            },
        }
    },
    mounted() {
        this.axios
            .get('/unit/all')
            .then((res) => {
                this.units = res.data
            })
            .catch((error) => {
                console.error(error)
            })
    },
    methods: {
        resetForm() {
            this.selectedUnit = null
            this.hours = 1
            this.desc = ''
            this.date = new Date()
        },
        clearErrors() {
            Object.keys(this.errors).forEach((key) => {
                this.errors[key] = null
            })
        },
        open() {
            this.visible = true
        },
        close() {
            this.visible = false
        },
        async send() {
            const newRecordSchema = v.object({
                unit: v.required(v.number()),
                hours: v.required(v.pipe(v.number(), v.minValue(0.25), v.maxValue(24))),
                date: v.required(v.date()),
                description: v.required(v.pipe(v.string(), v.nonEmpty(), v.maxLength(255))),
            })

            const result = v.safeParse(newRecordSchema, {
                unit: this.selectedUnit,
                hours: this.hours,
                date: this.date,
                description: this.desc,
            })

            this.clearErrors()

            if (!result.success) {
                result.issues.map((issue) => {
                    const field = issue.path[0].key
                    this.errors[`${field}Invalid`] = issue.message
                })
                console.log(this.errors)
                return
            }

            // TODO: send data to the server

            this.visible = false
            this.resetForm()
            this.$emit('refresh')
        },
    },
    expose: ['open'],
    emits: ['refresh'],
}
</script>

<style scoped>
#modalBody {
    display: grid;
    gap: 0.5rem;
}
</style>
