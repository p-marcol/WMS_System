<script setup>
import i18nMesssages from '@/assets/localization/i18nMain'
import InputContainer from './ItemLabel.vue'
import Select from 'primevue/select'
</script>

<template>
    <InputContainer :label="$t('settings.language')">
        <Select
            v-model="$i18n.locale"
            :options="locales"
            @onChange="(locale) => localStorage.setItem('locale', locale)"
        >
            <template #option="{ option }">
                <span>{{ option.toUpperCase() }}</span>
            </template>
        </Select>
    </InputContainer>
</template>

<script>
export default {
    name: 'LocaleChanger',
    data() {
        return {
            locales: [],
        }
    },
    created() {
        this.locales = Object.keys(i18nMesssages)
    },
    mounted() {
        if (sessionStorage.getItem('locale')) {
            this.$i18n.locale = localStorage.getItem('locale')
        } else {
            localStorage.setItem('locale', this.$i18n.locale)
        }
    },
}
</script>

<style lang="scss" scoped></style>
