<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title>
            <v-btn @click="addItem"> New User </v-btn>
            <v-spacer></v-spacer>
            <v-col cols="12" sm="4">
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            </v-col>
          </v-card-title>
          <v-data-table :headers="headers" :items="users" :search="search">
            <template v-slot:item.actions="{ item }">
              <v-icon small class="mr-2" @click="editItem(item)">
                mdi-pencil
              </v-icon>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
    <v-dialog v-model="newDialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Create User</span>
        </v-card-title>
        <v-card-text>
         <v-form v-model="valid" ref="form" lazy-validation>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="user.name"
                  label="Name"
                  :rules="nameRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="user.username"
                  label="Email Address"
                  :rules="emailRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-autocomplete
                    :items="roles"
                    item-text="name"
                    item-value="id"
                    label="Role"
                    v-model="user.roleId"
                    :rules="roleRules"
                    ></v-autocomplete>
              </v-col>
            </v-row>
          </v-container>
         </v-form>
        </v-card-text>

        <v-card-actions>
          <v-btn color="blue darken-1" text @click="newDialog=false"> Cancel </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="triggerCreateUser"> Create </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editDialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Edit User</span>
        </v-card-title>
        <v-card-text>
         <v-form v-model="editvalid" ref="editform" lazy-validation>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="user.name"
                  label="Name"
                  :rules="nameRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="user.username"
                  label="Email Address"
                  :rules="emailRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="user.username"
                  label="Email Address"
                  :rules="emailRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-autocomplete
                    :items="roles"
                    item-text="name"
                    item-value="id"
                    label="Role"
                    v-model="user.roleId"
                    :rules="roleRules"
                    ></v-autocomplete>
              </v-col>
            </v-row>
          </v-container>
         </v-form>
        </v-card-text>

        <v-card-actions>
          <v-btn color="blue darken-1" text @click="editDialog = false"> Cancel </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="triggerUpdateUser"> Update </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'Users',
  data: () => ({
    search: '',
    headers: [
      { text: 'Id', value: 'id' },
      { text: 'Name', value: 'name' },
      { text: 'Email Address', value: 'username' },
      { text: 'Role', value: 'role.name' },
      { text: 'Active', value: 'active' },
      { text: 'Created At', value: 'createdAt' },
      { text: 'Actions', value: 'actions', sortable: false }
    ],
    newDialog: false,
    editDialog: false,
    user: {},
    nameRules: [v => !!v || 'Name is required'],
    emailRules: [v => !!v || 'Email is required'],
    roleRules: [v => !!v || 'Role is required'],
    valid: true,
    editvalid: true
  }),
  computed: mapState({
    roles: (state) => state.role.roles,
    users: (state) => state.user.users
  }),
  methods: {
    ...mapActions({ createUser: 'user/createUser', updateUser: 'user/updateUser' }),
    addItem () {
      this.newDialog = true
    },
    editItem (item) {
      this.user = item
      this.editDialog = true
    },
    triggerCreateUser () {
      if (this.$refs.form.validate()) {
        this.createUser(this.user).then(response => {
          this.newDialog = false
          this.message = ''
          this.user = {}
          this.$store.dispatch('user/getUsers')
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
        }).catch(error => {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    },
    triggerUpdateUser () {
      if (this.$refs.editform.validate()) {
        this.updateUser(this.user).then(response => {
          this.editDialog = false
          this.user = {}
          this.message = ''
          this.$store.dispatch('user/getUsers')
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
        }).catch(error => {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    }
  },
  created () {
    this.$store.dispatch('user/getUsers')
    this.$store.dispatch('role/getRoles')
  }
}
</script>
