import dashService from '../../api/dash'

const state = {
  airtimeCount: 0,
  reviewCount: 0,
  airtimeRequests: [],
  reviews: [],
  kplcTokenCount: 0,
  kplcTokenRequests: [],
  kplcPostPaidCount: 0,
  kplcPostPaidRequests: [],
  bongaPointsCount: 0,
  bongaPointsRequests: [],
  waterCount: 0,
  waterRequests: [],
  payTVRequests: [],
  payTVCount: 0
}

const mutations = {
  setAirtimeCount (state, count) {
    state.airtimeCount = count
  },
  setReviewCount (state, count) {
    state.reviewCount = count
  },
  setAirtimeRequests (state, items) {
    state.airtimeRequests = items
  },
  setReviews (state, items) {
    state.reviews = items
  },
  setKPLCTokenCount (state, count) {
    state.kplcTokenCount = count
  },
  setKPLCTokenRequests (state, items) {
    state.kplcTokenRequests = items
  },
  setKPLCPostPaidCount (state, count) {
    state.kplcPostPaidCount = count
  },
  setKPLCPostPaidRequests (state, items) {
    state.kplcPostPaidRequests = items
  },
  setBongaPointsCount (state, count) {
    state.bongaPointsCount = count
  },
  setBongaPointsRequests (state, items) {
    state.bongaPointsRequests = items
  },
  setWaterRequests (state, items) {
    state.waterRequests = items
  },
  setWaterCount (state, count) {
    state.waterCount = count
  },
  setPaytvRequests (state, items) {
    state.payTVRequests = items
  },
  setPaytvCount (state, count) {
    state.payTVCount = count
  }
}

const actions = {
  getAirtimeCount ({ commit }) {
    dashService.getAirtimeCount().then(response => {
      commit('setAirtimeCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  },
  getAirtimeRequests ({ commit }) {
    dashService.getAirtimeRequests().then(response => {
      commit('setAirtimeRequests', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getReviewCount ({ commit }) {
    dashService.getReviewCount().then(response => {
      commit('setReviewCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  },
  getReviewRequests ({ commit }) {
    dashService.getReviewRequests().then(response => {
      commit('setReviews', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getKPLCTokenCount ({ commit }) {
    dashService.getKPLCTokenCount().then(response => {
      commit('setKPLCTokenCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  },
  getKPLCTokenRequests ({ commit }) {
    dashService.getKPLCTokenRequests().then(response => {
      commit('setKPLCTokenRequests', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getKPLCPostPaidCount ({ commit }) {
    dashService.getKPLCPostPaidCount().then(response => {
      commit('setKPLCPostPaidCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  },
  getKPLCPostPaidRequests ({ commit }) {
    dashService.getKPLCPostPaidRequests().then(response => {
      commit('setKPLCPostPaidRequests', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getBongaPointsCount ({ commit }) {
    dashService.getBongaPointsCount().then(response => {
      commit('setBongaPointsCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  },
  getBongaPointsRequests ({ commit }) {
    dashService.getBongaPointsRequests().then(response => {
      commit('setBongaPointsRequests', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getWaterRequests ({ commit }) {
    dashService.getWaterRequests().then(response => {
      commit('setWaterRequests', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getWaterCount ({ commit }) {
    dashService.getWaterCount().then(response => {
      commit('setWaterCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  },
  getPayTVRequests ({ commit }) {
    dashService.getPayTVRequests().then(response => {
      commit('setPaytvRequests', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getPayTVCount ({ commit }) {
    dashService.getPayTVCount().then(response => {
      commit('setPaytvCount', response.data.message)
    }).catch(error => {
      console.log(error)
    })
  }
}
const getters = {}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
