<script setup>
import ProgressSpinner from 'primevue/progressspinner'
import UnitEditDrawer from '@/components/drawer/unit/UnitEditDrawer.vue'
import UnitDetailsDrawer from '@/components/drawer/unit/UnitDetailsDrawer.vue'
</script>

<template>
    <template v-if="!loading">
        <UnitDetailsDrawer v-if="!edit" :unit="unit" />
        <UnitEditDrawer v-else :unit="unit" @save="this.$emit('reload')" />
    </template>
    <template v-else>
        <div class="wms-drawer-content">
            <ProgressSpinner style="justify-self: center; align-self: center" stroke-width="5" />
        </div>
    </template>
</template>

<script>
export default {
    props: {
        unitId: {
            type: Number,
            default: null,
        },
        edit: {
            type: Boolean,
            required: true,
        },
    },
    data() {
        return {
            unit: Object,
            loading: true,
        }
    },
    watch: {
        unitId: {
            async handler(newId) {
                this.loading = true
                if (newId) {
                    console.log(newId)
                    await this.axios
                        .get(`/unit/getDetails/${newId}`)
                        .then((response) => {
                            this.unit = {
                                ...response.data,
                                breadcrumbs: response.data.parentUnits.map((unit) => {
                                    return { label: unit.name, id: unit.id }
                                }),
                            }
                            // console.log(this.unit)
                        })
                        .catch((err) => {
                            console.warn(err)
                        })
                        .finally(() => {})
                } else {
                    this.unit = {}
                }
                this.loading = false
            },
            immediate: true,
        },
    },
}
</script>
