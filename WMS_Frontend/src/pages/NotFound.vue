<template>
    <main>
        <p class="Header-P1">404, page not found :&lt;</p>
        <RouterLink to="/dashboard" class="Header-P2">&gt; get back to dashboard</RouterLink>
    </main>
</template>

<script>
export default {
    name: 'NotFound',
}
</script>

<style scoped>
main {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    width: 100vw;
}
</style>
