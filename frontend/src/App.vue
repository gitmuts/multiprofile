<template>
  <v-app>
    <v-snackbar
      v-model="snackbar"
      :color="color"
      :top="true"
      :right="true"
      :timeout="timeout"
      >
      {{ message }}
      <v-btn
        dark
        text
        @click="snackbar = false"
      >
        CLOSE
      </v-btn>
    </v-snackbar>
    <router-view/>
  </v-app>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'App',
  data: () => ({
    //
  }),
  methods: {
    ...mapActions({
      closeSuccessDialog: 'alert/closeSuccessDialog',
      closeFailedDialog: 'alert/closeFailedDialog'
    })
  },
  computed: {
    ...mapState({
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
    }
  }
}
</script>
