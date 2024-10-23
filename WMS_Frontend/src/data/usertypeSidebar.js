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
            { index: 1, label: 'Home', icon: HomeIcon, route: '/dashboard' },
            { index: 2, label: 'Schedule', icon: CalendarDaysIcon, route: '/schedule' },
            { index: 3, label: 'Timesheet', icon: TableCellsIcon, route: '/timesheet' },
            { index: 4, label: 'Reports', icon: ClipboardDocumentIcon, route: '/reports' },
        ],
    },
    MANAGER: {
        colorClass: 'manager-accent',
        content: [
            { index: 1, label: 'Home', icon: HomeIcon, route: '/dashboard' },
            { index: 2, label: 'Schedule', icon: CalendarDaysIcon, route: '/schedule' },
            { index: 3, label: 'Timesheet', icon: TableCellsIcon, route: '/timesheet' },
            { index: 4, label: 'Reports', icon: ClipboardDocumentIcon, route: '/reports' },
            { index: 5, label: 'Teams', icon: UsersIcon, route: '/teams' },
        ],
    },
    ADMIN: {
        colorClass: 'admin-accent',
        content: [
            { index: 1, label: 'Home', icon: HomeIcon, route: '/dashboard' },
            { index: 2, label: 'Schedule', icon: CalendarDaysIcon, route: '/schedule' },
            { index: 3, label: 'Timesheet', icon: TableCellsIcon, route: '/timesheet' },
            { index: 4, label: 'Reports', icon: ClipboardDocumentIcon, route: '/reports' },
            { index: 5, label: 'Users', icon: UsersIcon, route: '/users' },
            { index: 6, label: 'Units', icon: CubeIcon, route: '/units' },
        ],
    },
}
