<script setup>
import InputText from 'primevue/inputtext'
import PasswordInput from '@/components/input/PasswordInput.vue'
import InputContainer from '@/components/input/InputContainer.vue'
import Message from 'primevue/message'
</script>

<template>
    <div id="bgContainer">
        <img id="bg-img" src="@/assets/images/login-bg.jpg" alt="snail image" />
        <!-- <div id="logoContainer">LOGO HERE</div> -->
    </div>
    <div id="loginPane" class="">
        <h1 class="Header-P1">{{ $t('login.login') }}</h1>
        <form action="#" @submit.prevent="login">
            <div>
                <InputContainer :label="$t('login.usernameOrEmail')" label-for="username">
                    <InputText id="username" class="Input-text-P1" v-model="username" />
                </InputContainer>
                <PasswordInput v-model="password" />
                <PasswordInput v-model="confirmPassword" v-if="noPassword" confirm />
            </div>
            <div id="formFooter">
                <Message class="Label-P3" v-if="error">{{ this.error }}</Message>
                <input type="submit" value="Login" class="wms-big-button Big-button-text-P1" />
                <a href="#" class="Link-text-P1" @click="nyi">{{ $t('login.forgotPassword') }}</a>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    name: 'LoginPage',
    components: {
        PasswordInput,
    },
    data() {
        return {
            username: '',
            password: '',
            confirmPassword: '',
            noPassword: false,
            error: null,
        }
    },
    methods: {
        async login() {
            if (this.noPassword && this.confirmPassword !== '') {
                if (this.password !== this.confirmPassword) {
                    this.error = this.$t('login.passwordMismatch')
                    return
                }
                await this.axios.post('/auth/setPassword', {
                    username: this.username,
                    password: this.password,
                    confirmPassword: this.confirmPassword,
                })
            }
            await this.axios
                .post('/auth/login', {
                    username: this.username,
                    password: this.password,
                })
                .then((response) => {
                    if (response.status === 206) {
                        this.noPassword = true
                        this.error = this.$t('login.noPassword')
                        return
                    }
                    localStorage.setItem('token', response.data.token)
                    localStorage.setItem('refresh', response.data.refreshToken)
                    this.$router.push('/dashboard')
                })
                .catch((error) => {
                    if (error.response) {
                        if (error.response.status === 401) {
                            this.error = this.$t('login.invalid')
                        } else {
                            this.$toast.add({
                                severity: 'error',
                                summary: 'Error',
                                detail: 'An error occurred',
                                life: 3000,
                            })
                        }
                    } else {
                        this.$toast.add({
                            severity: 'error',
                            summary: 'Error',
                            detail: 'Server is not responding',
                            life: 3000,
                        })
                    }
                })
        },
        nyi() {
            this.$toast.add({
                severity: 'info',
                summary: 'Not Yet Implemented',
                detail: 'This feature is not yet implemented',
                life: 3000,
            })
        },
    },
}
</script>

<style scoped>
#formHeader {
    display: grid;
    grid-template-rows: 1fr 1fr;
    gap: 1rem;
    align-items: center;
}

h1 {
    text-transform: uppercase;
    align-self: center;
}

form {
    display: grid;
    align-items: center;
    width: 80%;
    grid-template-rows: auto auto 0.4fr 3fr 1fr;
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
