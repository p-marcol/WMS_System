<script setup>
import DatePicker from 'primevue/datepicker'
import InputContainer from '@/components/input/InputContainer.vue'
import ItemLabel from '@/components/ItemLabel.vue'
import SaveCancelButtons from '@/components/input/SaveCancelButtons.vue'
import InputText from 'primevue/inputtext'
import Checkbox from 'primevue/checkbox'
import Dropdown from 'primevue/dropdown'
</script>

<template>
    <div class="wms-drawer-content">
        <div class="wms-col-2">
            <ItemLabel :label="$t('users.id')">
                <div class="Header-P4" id="id">{{ user.id }}</div>
            </ItemLabel>
            <ItemLabel :label="$t('users.status')">
                <div class="Header-P4" id="archived">
                    <div class="wms-row">
                        <Checkbox
                            v-model="editUser.isArchived"
                            :true-value="false"
                            :false-value="true"
                            :binary="true"
                            :readonly="true"
                            @click="cantChangeHere"
                        />
                        {{ user.isArchived ? $t('users.archived') : $t('users.active') }}
                    </div>
                </div>
            </ItemLabel>
        </div>
        <div class="wms-col-2">
            <ItemLabel :label="$t('users.username')">
                <div class="Header-P4" id="username">{{ user.username }}</div>
            </ItemLabel>
        </div>
        <div class="wms-col-2">
            <InputContainer :label="$t('users.firstName')" label-for="firstName">
                <InputText
                    id="firstName"
                    v-model="editUser.firstName"
                    :readonly="user.isArchived"
                />
            </InputContainer>
            <InputContainer :label="$t('users.lastName')" label-for="lastName">
                <InputText id="lastName" v-model="editUser.lastName" :readonly="user.isArchived" />
            </InputContainer>
        </div>
        <InputContainer v-if="!user.isArchived" :label="$t('users.email')" required>
            <InputText id="email" v-model="editUser.email" :readonly="user.isArchived" />
        </InputContainer>
        <InputContainer :label="$t('users.birthdate')" :label-for="birthdate">
            <DatePicker id="birthdate" v-model="editUser.birthdate" :readonly="user.isArchived" />
        </InputContainer>
        <InputContainer :label="$t('users.phone')" :label-for="phone">
            <InputText id="phone" v-model="editUser.phone" :readonly="user.isArchived" />
        </InputContainer>
        <InputContainer :label="$t('users.authority')" :label-for="authority">
            <Dropdown
                v-model="editUser.authorityId"
                :options="authorities"
                option-label="authority"
                option-value="id"
            />
        </InputContainer>
        <SaveCancelButtons @save="save" saveButton />
    </div>
</template>

<script>
export default {
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
    data() {
        return {
            editUser: Object,
            userActive: false,
            authorities: [],
        }
    },
    beforeMount() {
        this.axios
            .get('/authority/all')
            .then((res) => {
                this.authorities = res.data
            })
            .catch((err) => {
                console.warn(err)
            })
    },
    methods: {
        cantChangeHere() {
            this.$toast.add({
                severity: 'warn',
                summary: this.$t('form.cantChangeHere'),
                life: 3000,
            })
        },
        save() {
            this.axios
                .post('/user/upsertDetails', {
                    userId: this.user.id,
                    email: this.editUser.email,
                    firstName: this.editUser.firstName,
                    lastName: this.editUser.lastName,
                    phoneNumber: this.editUser.phone,
                    dateOfBirth: this.editUser.birthdate,
                    authorityId: this.editUser.authorityId,
                })
                .then(() => {
                    this.$toast.add({
                        severity: 'success',
                        summary: this.$t('form.saved'),
                        life: 3000,
                    })
                    this.$emit('refresh')
                })
                .catch(() => {
                    this.$toast.add({
                        severity: 'error',
                        summary: this.$t('form.error'),
                        life: 3000,
                    })
                })
        },
    },
    watch: {
        user: {
            handler(newUser) {
                this.editUser = newUser
            },
            immediate: true,
        },
    },
    emits: ['refresh'],
}
</script>

<style scoped></style>
