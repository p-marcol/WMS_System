<script setup>
import i18nMesssages from '@/assets/localization/i18nMain'
</script>

<template>
    <select @change="updateLocale" v-model="$i18n.locale">
        <option v-for="(locale, i) in locales" :key="`locale-${i}`" :value="locale">
            {{ locale.toUpperCase() }}
        </option>
    </select>
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
    methods: {
        updateLocale() {
            localStorage.setItem('locale', this.$i18n.locale)
        },
    },
}
</script>

<style lang="scss" scoped></style>
