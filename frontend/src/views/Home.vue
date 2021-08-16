<template>
  <v-container fluid>
    <v-row align="center">
      <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ airtimeCount }}</h3>
          <p class="pa-3 primary--text"> Buy Airtime </p>
          <v-btn text class="pb-4" to="/airtime"> View More </v-btn>
        </v-card>
      </v-col>
       <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ kplcTokenCount }}</h3>
          <p class="pa-3 primary--text"> KPLC Tokens </p>
          <v-btn text class="pb-4" to="/kplc_token"> View More </v-btn>
        </v-card>
      </v-col>
       <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ kplcPostPaidCount }} </h3>
          <p class="pa-3 primary--text"> KPLC PostPaid </p>
          <v-btn text class="pb-4" to="/kplc_postpaid"> View More </v-btn>
        </v-card>
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ reviewCount }} </h3>
          <p class="pa-3 primary--text"> Reviews </p>
          <v-btn text class="pb-4" to="/reviews"> View More </v-btn>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ bongaPointsCount }} </h3>
          <p class="pa-3 primary--text"> Bonga Points </p>
          <v-btn text class="pb-4" to="/bongapoints"> View More </v-btn>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ waterCount }} </h3>
          <p class="pa-3 primary--text"> Water </p>
          <v-btn text class="pb-4" to="/water"> View More </v-btn>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ payTVCount }} </h3>
          <p class="pa-3 primary--text"> Pay TV </p>
          <v-btn text class="pb-4" to="/paytv"> View More </v-btn>
        </v-card>
      </v-col>
       <v-col cols="12" sm="4">
        <v-card class="text-center ma-2">
          <h3 class="pb-3 pt-3"> {{ sessionCount }}  </h3>
          <p class="pa-3 primary--text"> Total Sessions </p>
          <v-btn text class="pb-4" to="/session"> View More </v-btn>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Home',
  data: () => ({
  }),
  computed: mapState({
    sessionCount: state => state.botsession.sessionCount,
    airtimeCount: state => state.dash.airtimeCount,
    reviewCount: state => state.dash.reviewCount,
    kplcTokenCount: state => state.dash.kplcTokenCount,
    kplcPostPaidCount: state => state.dash.kplcPostPaidCount,
    bongaPointsCount: state => state.dash.bongaPointsCount,
    waterCount: state => state.dash.waterCount,
    payTVCount: state => state.dash.payTVCount
  }),
  created () {
    this.$store.dispatch('botsession/getSessionCount')
    this.$store.dispatch('dash/getAirtimeCount')
    this.$store.dispatch('dash/getReviewCount')
    this.$store.dispatch('dash/getKPLCTokenCount')
    this.$store.dispatch('dash/getKPLCPostPaidCount')
    this.$store.dispatch('dash/getBongaPointsCount')
    this.$store.dispatch('dash/getWaterCount')
    this.$store.dispatch('dash/getPayTVCount')
  }
}
</script>
