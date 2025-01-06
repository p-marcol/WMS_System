<script setup>
import InputContainer from '@/components/input/InputContainer.vue'
import ItemLabel from '@/components/ItemLabel.vue'
import SaveCancelButtons from '@/components/input/SaveCancelButtons.vue'
import InputText from 'primevue/inputtext'
import Checkbox from 'primevue/checkbox'
</script>

<!-- eslint-disable vue/no-mutating-props -->
<template>
    <div class="wms-drawer-content">
        <div class="wms-col-2">
            <ItemLabel :label="$t('units.id')">
                <div class="Header-P4" id="id">{{ unit.id }}</div>
            </ItemLabel>
            <ItemLabel :label="$t('units.status')">
                <div class="Header-P4" id="archived">
                    <div class="wms-row">
                        <Checkbox
                            v-model="unit.isWorking"
                            :binary="true"
                            :readonly="true"
                            @click="cantChangeHere"
                        />
                        {{ unit.isWorking ? $t('units.working') : $t('units.notWorking') }}
                    </div>
                </div>
            </ItemLabel>
        </div>
        <InputContainer :label="$t('units.name')">
            <InputText v-model="editUnit.name" :readonly="!unit.isWorking" />
        </InputContainer>
        <InputContainer :label="$t('units.description')">
            <InputText v-model="editUnit.description" :readonly="!unit.isWorking" />
        </InputContainer>
        <SaveCancelButtons @save="save" saveButton />
    </div>
</template>

<script>
export default {
    props: {
        unit: {
            type: Object,
            required: true,
        },
    },
    data() {
        return {
            editUnit: Object,
            unitActive: false,
        }
    },
    methods: {
        cantChangeHere() {
            this.$toast.add({
                severity: 'warn',
                summary: this.$t('form.cantChangeHere'),
                life: 3000,
            })
        },
        save() {
            this.axios
                .post('/unit/upsertDetails', {
                    id: this.editUnit.id,
                    name: this.editUnit.name,
                    description: this.editUnit.description,
                })
                .then((res) => {
                    this.$toast.add({
                        severity: 'success',
                        summary: this.$t('form.saved'),
                        life: 3000,
                    })
                    this.$emit('save')
                    console.log(res)
                })
                .catch(() => {
                    this.$toast.add({
                        severity: 'error',
                        summary: this.$t('form.error'),
                        life: 3000,
                    })
                })
        },
        cancel() {
            const dirty = this.isDirty()
            this.$toast.add({
                severity: 'info',
                summary: this.$t('form.canceled'),
                detail: dirty ? 'Changes not saved' : '',
                life: 3000,
            })
        },
    },
    watch: {
        unit: {
            handler(newUnit) {
                console.log(newUnit)
                this.editUnit = newUnit
            },
            immediate: true,
        },
    },
}
</script>

<style scoped></style>
