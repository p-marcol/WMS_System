<template>
    <div class="container">
        <!-- { "userId": 3, "firstName": null, "lastName": null, "shortName": "admin", "position": "MANAGER" } -->

        <!-- Profile image -->
        <img src="https://picsum.photos/150" alt="Profile image" />
        <div id="userData">
            <p :class="this.position === 'manager' ? 'Label-P1-Bold' : 'Label-P1'" id="userName">
                {{ name }}
            </p>
            <p class="Label-P3-SemiBold" id="position">{{ position }}</p>
        </div>
    </div>
</template>

<script>
export default {
    computed: {
        name() {
            if (this.user.firstName === null || this.user.lastName === null) {
                return this.user.shortName
            }
            return `${this.user.firstName} ${this.user.lastName}`
        },
        position() {
            return this.user.position.toLowerCase()
        },
    },
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
}
</script>

<style scoped>
.container {
    display: grid;
    grid-template-columns: auto 1fr;
    align-items: center;
    gap: 1rem;
    max-height: 5rem;
}

.container img {
    padding: 0.2rem;
    width: 4.5rem;
    border-radius: 50%;
}

#userData {
    display: flex;
    flex-direction: column;
}

#userName {
    justify-self: end;
}

#position {
    text-transform: capitalize;
    color: var(--color-dark);
    justify-self: start;
}
</style>
