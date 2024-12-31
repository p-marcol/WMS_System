<script setup>
import ItemLabel from '@/components/ItemLabel.vue'
import Checkbox from 'primevue/checkbox'
</script>

<!-- eslint-disable vue/no-mutating-props -->
<template>
    <div class="wms-drawer-content">
        <!-- <div class="wms-col-3">
            <ItemLabel :label="$t('users.id')">
                <div class="Header-P4" id="id">{{ user.id }}</div>
            </ItemLabel>
            <ItemLabel :label="$t('users.status')">
                <div class="Header-P4" id="archived">
                    <div class="wms-row">
                        <Checkbox
                            v-model="user.isArchived"
                            :true-value="false"
                            :false-value="true"
                            :binary="true"
                            :readonly="true"
                            @click="cantChangeHere"
                        />
                        {{ user.isArchived ? $t('users.archived') : $t('users.active') }}
                    </div>
                </div>
            </ItemLabel>
            <ItemLabel :label="$t('users.unit')">
                <div class="Header-P4" id="unit">{{ user.currentUnit }}</div>
            </ItemLabel>
        </div>
        <div class="wms-col-2">
            <ItemLabel :label="$t('users.username')">
                <div class="Header-P4" id="username">{{ user.username }}</div>
            </ItemLabel>
            <ItemLabel v-if="!user.isArchived" :label="$t('users.email')">
                <div class="Header-P4" id="email">{{ user.email }}</div>
            </ItemLabel>
        </div>
        <div class="wms-col-2">
            <ItemLabel :label="$t('users.firstName')">
                <div class="Header-P4" id="firstName">{{ user.firstName }}</div>
            </ItemLabel>
            <ItemLabel :label="$t('users.lastName')">
                <div class="Header-P4" id="lastName">{{ user.lastName }}</div>
            </ItemLabel>
        </div>
        <ItemLabel :label="$t('users.birthdate')">
            <div class="Header-P4" id="birthdate">{{ user.birthdate }}</div>
        </ItemLabel>
        <ItemLabel :label="$t('users.phone')">
            <div class="Header-P4" id="phone">{{ user.phone }}</div>
        </ItemLabel>
        <ItemLabel :label="$t('users.authority')">
            <div class="Header-P4" id="authority">{{ user.authorityName }}</div>
        </ItemLabel> -->
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
    methods: {},
}
</script>

<style scoped></style>
