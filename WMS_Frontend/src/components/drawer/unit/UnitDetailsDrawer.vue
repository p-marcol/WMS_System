<script setup>
import ItemLabel from '@/components/ItemLabel.vue'
import Checkbox from 'primevue/checkbox'
import Breadcrumb from 'primevue/breadcrumb'
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
                        <Checkbox v-model="unit.isWorking" :binary="true" :readonly="true" />
                        {{ unit.isWorking ? $t('units.working') : $t('units.notWorking') }}
                    </div>
                </div>
            </ItemLabel>
        </div>
        <ItemLabel :label="$t('units.name')">
            <div class="Header-P4" id="name">{{ unit.name }}</div>
        </ItemLabel>
        <ItemLabel :label="$t('units.hierarchy')" id="hierarchy">
            <Breadcrumb
                :home="{ icon: HomeIcon, label: $t('br.home'), id: null }"
                :model="unit.breadcrumbs"
                :pt="{ item: { class: 'Text-P1' } }"
            >
                <template #item="{ item }">
                    <span class="">{{ item.label }}</span>
                </template>
            </Breadcrumb>
        </ItemLabel>
        <ItemLabel :label="$t('units.desc')">
            <span class="Text-P1">{{ unit.description }}</span>
        </ItemLabel>
    </div>
</template>

<script>
export default {
    data() {
        return {}
    },
    props: {
        unit: {
            type: Object,
            required: true,
        },
    },
}
</script>

<style scoped>
#hierarchy {
    overflow-y: auto;
}
</style>
