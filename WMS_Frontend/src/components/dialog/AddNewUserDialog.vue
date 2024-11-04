<script setup>
import Dialog from 'primevue/dialog'
import InputContainer from '../input/InputContainer.vue'
import InputText from 'primevue/inputtext'
import SaveCancelButtons from '../input/SaveCancelButtons.vue'
import Message from 'primevue/message'
import { Form } from '@primevue/forms'
import * as v from 'valibot'
</script>

<template>
    <Dialog v-model:visible="visible" modal :draggable="false">
        <template #header>
            <h3 class="Header-P3">Add New User</h3>
        </template>

        <Form id="modalBody" @submit="submit">
            <InputContainer :label="$t('users.username')">
                <InputText v-model="username" :invalid="usernameInvalid" required />
                <Message v-if="errors.usernameInvalid" severity="error" text="Invalid username">{{
                    errors.usernameInvalid
                }}</Message>
            </InputContainer>
            <InputContainer :label="$t('users.email')">
                <InputText v-model="email" :invalid="emailInvalid" required />
                <Message v-if="errors.emailInvalid" severity="error" text="Invalid username">{{
                    errors.emailInvalid
                }}</Message>
            </InputContainer>
        </Form>

        <template #footer>
            <SaveCancelButtons @save="send" @cancel="close" />
        </template>
    </Dialog>
</template>

<script>
export default {
    name: 'AddNewUserDialog',
    data() {
        return {
            visible: false,
            username: '',
            email: '',
            errors: {
                usernameInvalid: null,
                emailInvalid: null,
            },
        }
    },
    updated() {
        this.reset()
    },
    methods: {
        reset() {
            this.username = ''
            this.email = ''
            this.errors = {
                usernameInvalid: null,
                emailInvalid: null,
            }
        },
        open() {
            this.visible = true
        },
        close() {
            this.visible = false
        },
        send() {
            const newUserSchema = v.object({
                email: v.pipe(v.string(), v.nonEmpty(), v.email()),
                username: v.pipe(v.string(), v.nonEmpty()),
            })
            console.log('send')
            const result = v.safeParse(newUserSchema, {
                email: this.email,
                username: this.username,
            })

            this.errors = {
                usernameInvalid: null,
                emailInvalid: null,
            }

            console.log(result)

            if (!result.success) {
                result.issues.map((issue) => {
                    const field = issue.path[0].key
                    const message = issue.message
                    this.errors[`${field}Invalid`] = message
                })
                return
            }
        },
    },
    expose: ['open'],
}
</script>

<style scoped>
#modalBody {
    display: grid;
    gap: 0.5rem;
}
</style>
