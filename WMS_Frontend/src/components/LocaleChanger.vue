<script setup>
import i18nMesssages from '@/assets/localization/i18nMain'
</script>

<template>
    <div>
        <h3>Locale change</h3>
        <select v-model="$i18n.locale">
            <option v-for="(lang, i) in langs" :key="`Lang${i}`" :value="lang">
                {{ lang }}
            </option>
        </select>
    </div>
</template>

<script>
export default {
    created() {
        console.log(i18nMesssages)
        this.langs = Object.keys(i18nMesssages)
    },
    data() {
        return {
            langs: [],
        }
    },
}
</script>

<style lang="scss" scoped></style>
