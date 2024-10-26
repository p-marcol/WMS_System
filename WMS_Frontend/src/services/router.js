import { createMemoryHistory, createRouter } from 'vue-router'

import pages from '@/pages/pageList'

const routes = [
    { path: '/', component: pages.emptyPage },
    { path: '/login', component: pages.loginPage },
    { path: '/dashboard', component: pages.dashboardPage },
    { path: '/users', component: pages.usersPage },
    { path: '/:pathMatch(.*)*', component: pages.notFound },
]

export default createRouter({
    history: createMemoryHistory(),
    routes,
})
