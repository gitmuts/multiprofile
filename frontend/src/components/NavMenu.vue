<template>
<div>
  <v-app-bar color="#FFFFFF" dense dark>
  <v-app-bar-nav-icon></v-app-bar-nav-icon>
  <v-toolbar-title></v-toolbar-title>
  <v-spacer></v-spacer>
  <v-btn icon @click="selectAccountDialog=true"><v-icon color="primary"> switch_account </v-icon></v-btn>
  <v-menu
    left
    bottom
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        icon
        v-bind="attrs"
        v-on="on"
      >
        <v-icon color="accent">mdi-dots-vertical</v-icon>
      </v-btn>
    </template>

    <v-list>
      <v-list-item @click="logoutDialog = true"
      >
        <v-list-item-title>Log out</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
 </v-app-bar>
  <v-navigation-drawer
    v-model="drawer"
    clipped
    app
    :permanent="$vuetify.breakpoint.mdAndUp"
    dense
  >
    <v-list class="mt-12">
      <v-list-item to="/admin">
        <v-list-item-icon>
          <v-icon>mdi-home</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Home</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item to="/session">
        <v-list-item-icon>
          <v-icon>mdi-table-settings</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Sessions</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-group prepend-icon="mdi-cog-outline">
        <v-list-item to="/user" class="ml-4">
          <v-list-item-icon>
            <v-icon>mdi-account-circle</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Users</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title>Other Settings</v-list-item-title>
          </v-list-item-content>
        </template>
        <v-list-group :value="true" no-action sub-group>
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>Access Control</v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item link to="/role">
            <v-list-item-title>Roles</v-list-item-title>
            <v-list-item-icon>
              <v-icon>mdi-cog-outline</v-icon>
            </v-list-item-icon>
          </v-list-item>
          <v-list-item link to="/permission">
            <v-list-item-title>Permissions</v-list-item-title>
            <v-list-item-icon>
              <v-icon>mdi-cog-outline</v-icon>
            </v-list-item-icon>
          </v-list-item>
        </v-list-group>
      </v-list-group>
    </v-list>
  </v-navigation-drawer>
  <v-dialog v-model="logoutDialog" persistent max-width="600">
  <v-card>
    <v-card-title class="headline">Logout</v-card-title>
    <v-card-text>
      <h4 class="mt-2"> Are you sure you want to logout? </h4> </v-card-text>
    <v-card-actions>
      <v-btn color="primary" text @click="logoutDialog = false">Cancel</v-btn>
      <v-spacer></v-spacer>
      <v-btn color="secondary" @click="logout">Logout</v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>
<v-dialog v-model="selectAccountDialog" persistent max-width="600">
  <v-card raised class="rounded-lg" min-height="100%" :loading="loading">
    <v-card-title class="headline mb-5">
      <v-row>
        <v-col cols="12" md="6">
          Select Account
        </v-col>
        <v-spacer></v-spacer>
        <v-col cols="12" md="1">
          <v-btn icon @click="selectAccountDialog = false"><v-icon color="black">mdi-close</v-icon></v-btn>
        </v-col>
      </v-row>
      </v-card-title>
    <v-card-text>
      <v-row justify="center" v-for="org in user.organizations" :key="org.id">
        <v-col cols="12" md="8">
          <v-card v-if="org.id !== user.selectedOrganization.id" elevation="3" class="rounded-xl mb-10" ripple @click="triggerSwitchAccount(org)">
            <v-card-text> <h4 class="ml-4"> {{ org.name }} </h4></v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</v-dialog>
</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'NavMenu',
  data: function () {
    return {
      drawer: null,
      item: 0,
      logoutDialog: false,
      selectAccountDialog: false,
      loading: false
    }
  },
  computed: mapState({
    user: state => state.session.user
  }),
  methods: {
    ...mapActions({ logout: 'session/logout', switchAccount: 'session/switchAccount' }),
    logoutUser () {
      this.logout()
    },
    triggerSwitchAccount (org) {
      this.loading = true
      this.switchAccount(org).then(response => {
        this.loading = false
        this.selectAccountDialog = false
        this.$router.push('/role')
        this.$store.dispatch('session/setUser', response.data.user)
        this.$store.dispatch('session/setToken', response.data.token)
        if (window.localStorage) {
          window.localStorage.setItem('user', JSON.stringify(response.data.user))
          window.localStorage.setItem('token', response.data.token)
        }
        this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'success', message: 'Account switched' }, { root: true })
      }).catch(error => {
        console.log(error)
        this.loading = false
        this.selectAccountDialog = false
        if (error.response && error.response.status === 401) {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        } else {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response ? error.response.data.message : 'Failed to switch' }, { root: true })
        }
      })
    }
  }
}
</script>
<style>
</style>
