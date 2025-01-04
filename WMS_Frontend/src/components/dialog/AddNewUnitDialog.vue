<script setup>
import Dialog from 'primevue/dialog'
import * as v from 'valibot'
import InputContainer from '@/components/input/InputContainer.vue'
import InputText from 'primevue/inputtext'
import SaveCancelButtons from '@/components/input/SaveCancelButtons.vue'
import Message from 'primevue/message'
import { Form } from '@primevue/forms'
import Select from 'primevue/select'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">{{ $t('units.addNewUnit') }}</h3>
        </template>

        <Form id="modalBody" @submit="submit">
            <InputContainer :label="$t('units.name')" required>
                <InputText v-model="unitName" :invalid="unitNameInvalid" required />
                <Message v-if="errors.unitNameInvalid" severity="error" text="Invalid unit name">
                    {{ errors.unitNameInvalid }}
                </Message>
            </InputContainer>
            <InputContainer :label="$t('units.description')" required>
                <InputText v-model="unitDescription" :invalid="unitDescriptionInvalid" required />
                <Message
                    v-if="errors.unitDescriptionInvalid"
                    severity="error"
                    text="Invalid unit description"
                >
                    {{ errors.unitDescriptionInvalid }}
                </Message>
            </InputContainer>
            <InputContainer :label="$t('units.parentUnit')">
                <Select
                    v-model="selectedUnitId"
                    :options="units"
                    optionLabel="name"
                    optionValue="id"
                    placeholder="Select a parent unit"
                    showClear
                />
            </InputContainer>
        </Form>

        <template #footer>
            <SaveCancelButtons @save="send" @cancel="close" saveButton cancelButton />
        </template>
    </Dialog>
</template>

<script>
export default {
    name: 'AddNewUnitDialog',
    data() {
        return {
            visible: false,
            unitName: '',
            unitDescription: '',
            units: [],
            selectedUnitId: null,
            managers: [],
            selectedManagers: [],
            errors: {
                unitNameInvalid: null,
                unitDescriptionInvalid: null,
            },
        }
    },
    updated() {
        this.reset()
    },
    mounted() {
        this.fetchAllUnits()
    },
    methods: {
        fetchAllUnits() {
            this.axios
                .get('/unit/all')
                .then((response) => {
                    this.units = response.data
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        reset() {
            this.unitName = ''
            this.unitDescription = ''
            this.errors = {
                unitNameInvalid: null,
                unitDescriptionInvalid: null,
            }
        },
        open() {
            this.visible = true
        },
        close() {
            this.visible = false
        },
        async send() {
            const newUnitSchema = v.object({
                unitName: v.pipe(v.string(), v.nonEmpty(this.$t('form.fieldRequired'))),
                unitDescription: v.pipe(v.string(), v.nonEmpty(this.$t('form.fieldRequired'))),
            })

            const result = v.safeParse(newUnitSchema, {
                unitName: this.unitName,
                unitDescription: this.unitDescription,
            })

            this.errors = {
                unitNameInvalid: null,
                unitDescriptionInvalid: null,
            }

            console.log(result)

            console.log({
                name: this.unitName,
                description: this.unitDescription,
                parentUnitId: this.selectedUnitId,
            })

            if (!result.success) {
                result.issues.map((issue) => {
                    if (issue.path[0] === 'unitName') {
                        this.errors.unitNameInvalid = issue.message
                    }
                    if (issue.path[0] === 'unitDescription') {
                        this.errors.unitDescriptionInvalid = issue.message
                    }
                })
                return
            }

            this.axios
                .post('/unit/add', {
                    name: this.unitName,
                    description: this.unitDescription,
                    parentUnitId: this.selectedUnitId,
                })
                .then(() => {
                    this.close()
                    this.$emit('reload')
                })
                .catch(() => {
                    this.$toast.add({
                        severity: 'error',
                        summary: this.$t('form.error'),
                        life: 3000,
                    })
                })
        },
    },
    watch: {
        visible(val) {
            if (val) {
                this.fetchAllUnits()
            }
        },
    },
    expose: ['open'],
}
</script>

<style scoped></style>
