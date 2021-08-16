<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title>
            <v-btn @click="addItem"> New Role </v-btn>
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
          <v-data-table :headers="headers" :items="roles" :search="search">
            <template v-slot:[`item.actions`]="{ item }">
              <v-icon small class="mr-2" @click="editItem(item)">
                mdi-pencil
              </v-icon>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
    <v-dialog v-model="newDialog" max-width="800px">
      <v-card>
        <v-card-title>
          <span class="headline">Create Role</span>
        </v-card-title>
        <v-card-text>
         <v-form v-model="valid" ref="form" lazy-validation>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="role.name"
                  label="Name"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
                <v-col cols="12"> Permissions </v-col>
            </v-row>
            <v-row>
               <v-col cols="12" sm="12" md="12">
                   <v-data-table
                         v-model="rolepermissions"
                        :headers="permissionheaders"
                        :items="permissions"
                        class="elevation-1"
                        show-select
                    >
                    </v-data-table>
               </v-col>
            </v-row>
          </v-container>
         </v-form>
        </v-card-text>

        <v-card-actions>
          <v-btn color="blue darken-1" text @click="newDialog=false"> Cancel </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="triggerCreateRole"> Create </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editDialog" max-width="800px">
      <v-card>
        <v-card-title>
          <span class="headline">Edit Role</span>
        </v-card-title>
        <v-card-text>
         <v-form v-model="editvalid" ref="editform" lazy-validation>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="role.name"
                  label="Name"
                  :rules="nameRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
                <v-col cols="12"> Permissions </v-col>
            </v-row>
            <v-row>
               <v-col cols="12" sm="12" md="12">
                   <v-data-table
                         v-model="rolepermissions"
                        :headers="permissionheaders"
                        :items="permissions"
                        class="elevation-1"
                        show-select
                    >
                    </v-data-table>
               </v-col>
            </v-row>
          </v-container>
         </v-form>
        </v-card-text>

        <v-card-actions>
          <v-btn color="blue darken-1" text @click="editDialog = false"> Cancel </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="triggerUpdateRole"> Update </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'Role',
  data: () => ({
    search: '',
    headers: [
      { text: 'Id', value: 'id' },
      { text: 'Name', value: 'name' },
      { text: 'Actions', value: 'actions', sortable: false }
    ],
    newDialog: false,
    editDialog: false,
    role: {},
    nameRules: [v => !!v || 'Name is required'],
    valid: true,
    editvalid: true,
    rolepermissions: [],
    permissionheaders: [
      { text: 'Id', value: 'id' },
      { text: 'Action', value: 'action' }
    ]
  }),
  computed: mapState({
    roles: (state) => state.role.roles,
    permissions: (state) => state.permission.permissions
  }),
  methods: {
    ...mapActions({ createRole: 'role/createRole', updateRole: 'role/updateRole' }),
    addItem () {
      this.newDialog = true
    },
    editItem (item) {
      this.role = item
      this.editDialog = true
    },
    triggerCreateRole () {
      if (this.$refs.form.validate()) {
        this.createRole(this.role).then(response => {
          this.newDialog = false
          this.message = ''
          this.role = {}
          this.$store.dispatch('role/getRoles')
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
        }).catch(error => {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    },
    triggerUpdateRole () {
      if (this.$refs.editform.validate()) {
        this.updateRole(this.role).then(response => {
          this.editDialog = false
          this.role = {}
          this.message = ''
          this.$store.dispatch('role/getRoles')
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
        }).catch(error => {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    }
  },
  created () {
    this.$store.dispatch('role/getRoles')
    this.$store.dispatch('permission/getPermissions')
  }
}
</script>
