import {
    HomeIcon,
    CalendarDaysIcon,
    TableCellsIcon,
    ClipboardDocumentIcon,
    UsersIcon,
    CubeIcon,
} from '@heroicons/vue/24/outline'

export default {
    USER: {
        colorClass: 'user-accent',
        content: [
            { index: 1, label: 'home', icon: HomeIcon, route: '/dashboard' },
            { index: 2, label: 'schedule', icon: CalendarDaysIcon, route: '/schedule' },
            { index: 3, label: 'timesheet', icon: TableCellsIcon, route: '/timesheet' },
            { index: 4, label: 'reports', icon: ClipboardDocumentIcon, route: '/reports' },
        ],
    },
    MANAGER: {
        colorClass: 'manager-accent',
        content: [
            { index: 1, label: 'home', icon: HomeIcon, route: '/dashboard' },
            { index: 2, label: 'schedule', icon: CalendarDaysIcon, route: '/schedule' },
            { index: 3, label: 'timesheet', icon: TableCellsIcon, route: '/timesheet' },
            { index: 4, label: 'reports', icon: ClipboardDocumentIcon, route: '/reports' },
            { index: 5, label: 'teams', icon: UsersIcon, route: '/teams' },
        ],
    },
    ADMIN: {
        colorClass: 'admin-accent',
        content: [
            { index: 1, label: 'home', icon: HomeIcon, route: '/dashboard' },
            { index: 2, label: 'schedule', icon: CalendarDaysIcon, route: '/schedule' },
            { index: 3, label: 'timesheet', icon: TableCellsIcon, route: '/timesheet' },
            { index: 4, label: 'reports', icon: ClipboardDocumentIcon, route: '/reports' },
            { index: 5, label: 'users', icon: UsersIcon, route: '/users' },
            { index: 6, label: 'units', icon: CubeIcon, route: '/units' },
        ],
    },
}
