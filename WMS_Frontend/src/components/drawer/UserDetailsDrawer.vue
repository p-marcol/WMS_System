<template>
    <div>Details here</div>
    <div>{{ user }}</div>
</template>

<script>
export default {
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped></style>
