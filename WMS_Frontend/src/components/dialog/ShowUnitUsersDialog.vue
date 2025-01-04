<script setup>
import Dialog from 'primevue/dialog'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import { ArrowLeftStartOnRectangleIcon } from '@heroicons/vue/24/outline'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">{{ $t('units.unitUsers') }}</h3>
        </template>

        <DataTable :value="users" :paginator="true" :rows="10" :rowsPerPageOptions="[5, 10, 20]">
            <Column field="shortName" :header="$t('users.shortName')"></Column>
            <Column field="position" :header="$t('users.position')"></Column>
            <Column :header="$t('table.actions')">
                <template #body="{ data }">
                    <ArrowLeftStartOnRectangleIcon
                        class="wms-table-icon"
                        @click="deleteUser(data.id)"
                    />
                </template>
            </Column>
        </DataTable>
    </Dialog>
</template>

<script>
export default {
    name: 'ShowUnitUsersDialog',
    data() {
        return {
            id: null,
            visible: false,
            users: [],
            loading: false,
        }
    },
    methods: {
        fetchUnitUsers() {
            this.loading = true
            this.axios
                .get(`/unit/${this.id}/workers`)
                .then((res) => {
                    console.log(res.data.workers)
                    this.users = res.data.workers
                })
                .catch((err) => {
                    console.error(err)
                })
                .finally(() => {
                    this.loading = false
                })
        },
        open(id) {
            this.id = id
            this.visible = true
        },
        close() {
            this.visible = false
        },
    },
    watch: {
        visible(value) {
            if (value) {
                this.fetchUnitUsers()
            } else {
                this.users = []
            }
        },
    },
    expose: ['open'],
}
</script>

<style scoped></style>
