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
                <DataTable :value="cards" v-if="cards.length" :paginator="true" :rows="10">
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
                {{ $t('users.accesses') }}
            </AccordionHeader>
            <AccordionContent>
                <DataTable v-if="accesses.length" :value="accesses" :paginator="true" :rows="10">
                    <Column field="id" :header="$t('id')" />
                    <Column field="deviceSymbol" :header="$t('device.symbol')" />
                    <Column field="at" :header="$t('cards.at')" />
                </DataTable>
                <span v-else>{{ $t('users.noAccesses') }}</span>
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
        accesses: {
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
                accept: () => this.deleteCardById(data.cardUid),
                reject: () => {},
            })
            this.$emit('refresh')
        },
        async deleteCardById(uid) {
            this.axios
                .delete(`card/delete/${uid}`)
                .then(() => {
                    this.$toast.add({
                        severity: 'success',
                        summary: this.$t('cards.success.delete'),
                        life: 3000,
                    })
                })
                .catch((error) => {
                    console.error(error)
                    this.$toast.add({
                        severity: 'error',
                        summary: this.$t('cards.error.delete'),
                        life: 3000,
                    })
                })
                .finally(() => {
                    this.$emit('refresh')
                })
        },
    },
    emits: ['refresh'],
}
</script>

<style scoped></style>
