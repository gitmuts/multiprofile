<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title>
            <v-spacer></v-spacer>
            <v-col cols="12" sm="3">
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            </v-col>
          </v-card-title>
          <v-data-table :headers="headers" :items="sessions" :search="search">
              <template v-slot:[`item.createdAt`]="{ item }">
                {{ item.createdAt | formatdate }}
              </template>
              <template v-slot:[`item.chat.phoneNumber`]="{ item }">
                <router-link :to="`/session/${item.id}/messages`">{{item.chat.phoneNumber}}</router-link>
             </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Session',
  data: () => ({
    search: '',
    headers: [
      { text: 'Id', value: 'id' },
      { text: 'Phone Number', value: 'chat.phoneNumber' },
      { text: 'Created At', value: 'createdAt' },
      { text: 'Actions', value: 'actions', sortable: false }
    ]
  }),
  computed: mapState({
    sessions: state => state.botsession.sessions
  }),
  created () {
    this.$store.dispatch('botsession/getSessions')
  },
  methods: {
  }
}
</script>
