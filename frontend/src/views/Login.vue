<template>
  <v-container fluid  class="fill-height">
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-form>
        <v-card class="elevation-12">
          <v-toolbar color="#043775" dark flat>
            <v-toolbar-title>Login</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field label="Username" v-model="user.username" name="login" prepend-icon="mdi-account" type="text"></v-text-field>
              <v-text-field
                id="password"
                label="Password"
                name="password"
                prepend-icon="mdi-lock"
                v-model="user.password"
                type="password"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="#043775" class="white--text" v-on:keyup.enter="login" @click="login">Login</v-btn>
          </v-card-actions>
        </v-card>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import router from '../router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'Login',
  data: () => ({
    user: {},
    valid: true
  }),
  computed: {
    ...mapState({
      loginError: state => state.session.loginError,
      color: state => state.alert.color,
      timeout: state => state.alert.timeout,
      message: state => state.alert.message
    }),
    snackbar: {
      get: function () {
        return this.$store.state.alert.snackbar
      },
      set: function (newValue) {
        this.$store.state.alert.snackbar = newValue
      }
    },
    containerStyles () {
      return {
        height: `${this.y}px`,
        width: `${this.x}px`
      }
    }
  },
  methods: {
    ...mapActions({
      getToken: 'session/getToken'
    }),
    login () {
      this.loading = true
      this.$store.dispatch('session/resetLoginError', {})
      this.getToken(this.user).then(response => {
        const token = response.data.token
        this.$store.dispatch('session/setUser', response.data.user)
        this.$store.dispatch('session/setToken', token)
        if (window.localStorage) {
          window.localStorage.setItem('user', JSON.stringify(response.data.user))
          window.localStorage.setItem('token', token)
        }
        this.$store.dispatch('session/setLoggedIn', true)
        this.loading = false
        router.push(response.data.redirect ? response.data.redirect : '/admin')
      }).catch(error => {
        console.log(error)
        this.loading = false
        if (error.response && error.response.status === 401) {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        } else {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response ? error.response.data.message : 'Failed to connect to backend' }, { root: true })
        }
      })
    }
  }
}
</script>
