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
            { index: 1, label: 'Home', icon: HomeIcon, route: null },
            { index: 2, label: 'Schedule', icon: CalendarDaysIcon, route: null },
            { index: 3, label: 'Timesheet', icon: TableCellsIcon, route: null },
            { index: 4, label: 'Reports', icon: ClipboardDocumentIcon, route: null },
        ],
    },
    MANAGER: {
        colorClass: 'manager-accent',
        content: [
            { index: 1, label: 'Home', icon: HomeIcon, route: null },
            { index: 2, label: 'Schedule', icon: CalendarDaysIcon, route: null },
            { index: 3, label: 'Timesheet', icon: TableCellsIcon, route: null },
            { index: 4, label: 'Reports', icon: ClipboardDocumentIcon, route: null },
            { index: 5, label: 'Teams', icon: UsersIcon, route: null },
        ],
    },
    ADMIN: {
        colorClass: 'admin-accent',
        content: [
            { index: 1, label: 'Home', icon: HomeIcon, route: null },
            { index: 2, label: 'Schedule', icon: CalendarDaysIcon, route: null },
            { index: 3, label: 'Timesheet', icon: TableCellsIcon, route: null },
            { index: 4, label: 'Reports', icon: ClipboardDocumentIcon, route: null },
            { index: 5, label: 'Users', icon: UsersIcon, route: null },
            { index: 6, label: 'Units', icon: CubeIcon, route: null },
        ],
    },
}
