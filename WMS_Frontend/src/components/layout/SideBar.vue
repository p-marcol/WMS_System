<script setup>
import IconWithLabel from '@/components/IconWithLabel.vue'
import { ArrowRightStartOnRectangleIcon, Cog6ToothIcon } from '@heroicons/vue/24/outline'
import userType from '@/data/usertypeSidebar'
import SettingsDialog from '../dialog/SettingsDialog.vue'
</script>

<template>
    <div id="sidebar" :class="userTypeColorClass">
        <div class="upper bar">
            <IconWithLabel
                :label="$t(`sidebar.${item.label}`)"
                :route="item.route"
                v-for="item in sidebarContent"
                :key="item.index"
            >
                <component :is="item.icon" />
            </IconWithLabel>
        </div>
        <div class="lower bar">
            <IconWithLabel :label="$t('sidebar.settings')" @click="openSettingsDialog">
                <Cog6ToothIcon />
            </IconWithLabel>
            <IconWithLabel :label="$t('sidebar.logout')" @click="logout">
                <ArrowRightStartOnRectangleIcon />
            </IconWithLabel>
        </div>
    </div>
    <SettingsDialog ref="settingsDialog" />
</template>

<script>
export default {
    name: 'SideBar',
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
    watch: {
        user() {
            const userTypeName = this.user.role
            if (!userType[userTypeName]) {
                return
            }
            this.sidebarContent = userType[userTypeName].content
            this.userTypeColorClass = userType[userTypeName].colorClass
            this.sidebarContent = userType[this.user.role].content
            this.userTypeColorClass = userType[this.user.role].colorClass
        },
    },
    methods: {
        logout() {
            localStorage.removeItem('token')
            localStorage.removeItem('refresh')
            this.$router.push('/login')
        },
        openSettingsDialog() {
            this.$refs.settingsDialog.open()
        },
    },
    data() {
        return {
            sidebarContent: [],
            userTypeColorClass: null,
        }
    },
}
</script>

<style scoped>
#sidebar {
    height: 100vh;
    width: 90px;
    display: grid;
    grid-template-rows: auto auto;
    padding: 10px 0;
    align-content: space-between;
    box-shadow: 4px 0 10px 0 rgba(0, 0, 0, 0.25);
    z-index: 1000;
}

.manager-accent {
    background: var(--color-manager-accent);
}

.user-accent {
    background: var(--color-user-accent);
}

.admin-accent {
    background: var(--color-admin-accent);
}

.bar {
    display: grid;
    grid-template-columns: 1fr;
    gap: 10px;
}

.upper {
    overflow: auto;
}

.lower:before {
    content: '';
    display: block;
    height: 2px;
    border-radius: 1px;
    background: var(--color-white);
    margin: 0 10px;
}
</style>
