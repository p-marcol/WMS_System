import './assets/css/main.css'

import { createApp } from 'vue'
import { createI18n } from 'vue-i18n'

import App from './App.vue'
import i18nMesssages from './assets/localization/i18nMain'

import axios from './services/axios'
import router from './services/router'
import VueAxios from 'vue-axios'

import PrimeVue from 'primevue/config'
import ToastService from 'primevue/toastservice'
import ConfirmationService from 'primevue/confirmationservice'
import Lara from '@primevue/themes/aura'

const app = createApp(App)

const locale = localStorage.getItem('locale') || 'en'

const i18n = createI18n({
    locale: locale,
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
app.use(ToastService)
app.use(ConfirmationService)
app.use(VueAxios, axios)
app.use(router)

app.mount('#app')
