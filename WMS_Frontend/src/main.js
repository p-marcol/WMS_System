import './assets/css/main.css'

import { createApp } from 'vue'
import { createI18n } from 'vue-i18n'
import PrimeVue from 'primevue/config'
import Lara from '@primevue/themes/aura'
import App from './App.vue'
import i18nMesssages from './assets/localization/i18nMain'

const app = createApp(App)
const i18n = createI18n({
    locale: 'pl',
    fallbackLocale: 'en',
    messages: i18nMesssages,
})
const primeVueConfig = {
    theme: {
        preset: Lara,
        options: {
            darkModeSelector: '',
        },
    },
}

app.use(i18n)
app.use(PrimeVue, primeVueConfig)

app.mount('#app')
