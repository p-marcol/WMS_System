<script setup>
import InputWithLabel from '@/components/input/InputWithLabel.vue'
import PasswordInput from '@/components/input/PasswordInput.vue'
</script>

<template>
    <div id="bgContainer">
        <img id="bg-img" src="@/assets/images/login-bg.jpg" alt="snail image" />
        <!-- <div id="logoContainer">LOGO HERE</div> -->
    </div>
    <div id="loginPane" class="">
        <h1 class="Header-P1">{{ $t('login.login') }}</h1>
        <form action="#" @submit.prevent="login">
            <InputWithLabel :label="$t('login.usernameOrEmail')" v-model="username" />
            <PasswordInput v-model="password" />
            <div id="formFooter">
                <input type="submit" value="Login" class="wms-big-button Big-button-text-P1" />
                <a href="#" class="Link-text-P1">{{ $t('login.forgotPassword') }}</a>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    name: 'LoginPage',
    components: {
        InputWithLabel,
        PasswordInput,
    },
    data() {
        return {
            username: '',
            password: '',
        }
    },
    methods: {
        async login() {
            await this.axios
                .post('/auth/login', {
                    username: this.username,
                    password: this.password,
                })
                .then((response) => {
                    console.log(response)
                    localStorage.setItem('token', response.data.token)
                    localStorage.setItem('refresh', response.data.refreshToken)
                    this.$router.push('/dashboard')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
    },
}
</script>

<style scoped>
h1 {
    text-transform: uppercase;
    align-self: center;
}

form {
    display: grid;
    align-items: center;
    width: 80%;
    grid-template-rows: auto auto 3fr 1fr;
    gap: 1rem;
    align-items: center;
}

#formFooter {
    display: grid;
    grid-template-rows: 1fr 1fr;
    gap: 0.5rem;
}

#formFooter a {
    justify-self: center;
}

#bgContainer {
    width: 100vw;
    height: 100vh;
    cursor: default;
}

#bg-img {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    z-index: 0;
}

#logoContainer {
    position: absolute;
    width: 5vw;
    height: 5vw;
    background-color: var(--color-secondary);
    bottom: 75px;
    left: 75px;
}

#loginPane {
    background-color: var(--color-bg);
    position: absolute;
    top: 0;
    right: 0;
    width: 41vw;
    height: 100vh;
    z-index: 100;
    box-shadow: -4px 0 50px 0 rgba(0, 0, 0, 0.25);
    border-radius: 50px 0 0 50px;
    display: grid;
    grid-template-rows: 2fr 4fr;
    justify-items: center;
}
</style>
