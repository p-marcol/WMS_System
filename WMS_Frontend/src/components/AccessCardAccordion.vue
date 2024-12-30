<script setup>
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Accordion from 'primevue/accordion'
import AccordionPanel from 'primevue/accordionpanel'
import AccordionHeader from 'primevue/accordionheader'
import AccordionContent from 'primevue/accordioncontent'
import { TrashIcon } from '@heroicons/vue/24/outline'
</script>

<template>
    <Accordion>
        <AccordionPanel value="0">
            <AccordionHeader>
                {{ $t('users.cards') }}
            </AccordionHeader>
            <AccordionContent>
                <DataTable :value="cards" v-if="cards.length">
                    <Column field="cardUid" :header="$t('cards.uid')" />
                    <Column field="type" :header="$t('cards.type')" />
                    <Column field="description" :header="$t('cards.description')" />
                    <Column :header="$t('table.actions')">
                        <template #body="{ data }">
                            <div>
                                <TrashIcon class="wms-table-icon" @click="deleteCard(data)" />
                            </div>
                        </template>
                    </Column>
                </DataTable>
                <span v-else>{{ $t('cards.noContent') }}</span>
            </AccordionContent>
        </AccordionPanel>
        <AccordionPanel value="1">
            <AccordionHeader>
                <!-- TODO! -->
                {{ $t('users.accesses') }}
            </AccordionHeader>
            <AccordionContent>
                <span>{{ $t('users.noAccesses') }}</span>
            </AccordionContent>
        </AccordionPanel>
    </Accordion>
</template>

<script>
export default {
    props: {
        cards: {
            type: Array,
            required: true,
        },
    },
    methods: {
        deleteCard(data) {
            console.log(data)
            this.$confirm.require({
                message: this.$t('cards.confirm.delete'),
                header: this.$t('cards.confirm.deleteHeader', { uid: data.uid }),
                accept: () => this.deleteCardById(data.id),
                reject: () => {},
            })
            this.$emit('refresh')
        },
        async deleteCardById(id) {
            console.log(id)
        },
    },
    emits: ['refresh'],
}
</script>

<style scoped></style>
