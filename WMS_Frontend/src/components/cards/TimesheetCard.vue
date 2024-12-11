<script setup>
import CardContainer from '@/components/CardContainer.vue'
import { FaceFrownIcon } from '@heroicons/vue/24/outline'
import { DateTime } from 'luxon'
import Button from 'primevue/button'
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
                        @click="() => {}"
                        class="wms-small-button wms-small-button-primary Small-button-primary-P1"
                        unstyled
                    />
                    <Button
                        type="button"
                        :label="$t('timesheet.openTS')"
                        @click="() => {}"
                        class="wms-small-button wms-small-button-secondary Small-button-primary-P1"
                        unstyled
                    />
                </div>
            </div>
        </template>
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
