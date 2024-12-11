<script setup>
import CardContainer from '@/components/CardContainer.vue'
import { FaceFrownIcon } from '@heroicons/vue/24/outline'
</script>

<template>
    <CardContainer :title="$t('card.timesheet')" id="timesheet" :loading="loading">
        <template #header> Header </template>
        <div v-if="records.length === 0" id="nothing">
            <div>
                <FaceFrownIcon class="faceIcon" />
                <span class="Label-P3">{{ $t('timesheet.nothingHere') }}</span>
            </div>
        </div>
        <p v-else>Card content</p>
    </CardContainer>
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
}
</script>

<style scoped>
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
