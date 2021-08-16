<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title>
            <v-btn @click="addItem"> New Permission </v-btn>
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
          <v-data-table :headers="headers" :items="permissions" :search="search">
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
          <span class="headline">Create Permission</span>
        </v-card-title>
        <v-card-text>
         <v-form v-model="valid" ref="form" lazy-validation>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="permission.action"
                  label="Action"
                  :rules="actionRules"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
         </v-form>
        </v-card-text>

        <v-card-actions>
          <v-btn color="blue darken-1" text @click="newDialog=false"> Cancel </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="triggerCreatePermission"> Create </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editDialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Edit Permission</span>
        </v-card-title>
        <v-card-text>
         <v-form v-model="editvalid" ref="editform" lazy-validation>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8" md="8">
                <v-text-field
                  v-model="permission.action"
                  label="Action"
                  :rules="actionRules"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
         </v-form>
        </v-card-text>

        <v-card-actions>
          <v-btn color="blue darken-1" text @click="editDialog = false"> Cancel </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="triggerUpdatePermission"> Update </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'Permission',
  data: () => ({
    search: '',
    headers: [
      { text: 'Id', value: 'id' },
      { text: 'Action', value: 'action' },
      { text: 'Actions', value: 'actions', sortable: false }
    ],
    newDialog: false,
    editDialog: false,
    permission: {},
    actionRules: [v => !!v || 'Action is required'],
    valid: true,
    editvalid: true
  }),
  computed: mapState({
    permissions: (state) => state.permission.permissions
  }),
  methods: {
    ...mapActions({ createPermission: 'permission/createPermission', updatePermission: 'permission/updatePermission' }),
    addItem () {
      this.newDialog = true
    },
    editItem (item) {
      this.permission = item
      this.editDialog = true
    },
    triggerCreatePermission () {
      if (this.$refs.form.validate()) {
        this.createPermission(this.permission).then(response => {
          this.newDialog = false
          this.message = ''
          this.permission = {}
          this.$store.dispatch('permission/getPermissions')
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
        }).catch(error => {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    },
    triggerUpdatePermission () {
      if (this.$refs.editform.validate()) {
        this.updatePermission(this.permission).then(response => {
          this.editDialog = false
          this.permission = {}
          this.message = ''
          this.$store.dispatch('permission/getPermissions')
          this.$store.dispatch('alert/displayAlert', { snackbar: false, color: 'success', message: response.data.message }, { root: true })
        }).catch(error => {
          this.$store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data.message }, { root: true })
        })
      }
    }
  },
  created () {
    this.$store.dispatch('permission/getPermissions')
  }
}
</script>
