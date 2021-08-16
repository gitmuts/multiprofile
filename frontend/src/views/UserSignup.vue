<template>
  <v-container>
    <v-form v-model="valid" ref="form" lazy-validation>
      <v-row>
          <v-col cols="12" sm="8">
              <v-text-field label="Username" v-model="user.username" name="username" prepend-icon="mdi-account" type="text" readonly></v-text-field>
          </v-col>
          <v-col cols="12" sm="8">
              <v-text-field
                label="Password"
                name="password"
                prepend-icon="mdi-lock"
                v-model="user.password"
                type="password"
              ></v-text-field>
          </v-col>
      </v-row>
      <v-row>
        <v-col cols="6" sm="3">
         <v-btn color="primary" @click="triggerCompleteReg" :loading="loading"> Submit </v-btn>
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import router from '../router'

export default {
  name: 'UserSignup',
  data: () => ({
    valid: true,
    token: '',
    loading: false
  }),
  mounted () {
    this.token = this.$route.params.token
    this.getUserInfo(this.token)
  },
  computed: mapState({
    user: (state) => state.user.user
  }),
  methods: {
    ...mapActions({ getUserInfo: 'user/getUserInfo', completeRegistration: 'user/completeRegistration' }),
    triggerCompleteReg () {
      this.loading = true
      if (this.$refs.form.validate()) {
        this.user.token = this.token
        this.completeRegistration(this.user).then(response => {
          this.loading = false
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
          router.push('/login')
        }).catch(error => {
          this.loading = false
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    },
    cancel () {
      router.push('/login')
    }
  }
}
</script>
