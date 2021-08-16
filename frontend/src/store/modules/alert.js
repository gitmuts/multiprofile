const state = {
  snackbar: false,
  color: '',
  timeout: -1,
  message: '',
  successdialog: false,
  faileddialog: false,
  header: '',
  overlay: false
}

const mutations = {
  setAlert (state, payload) {
    state.snackbar = true
    state.color = payload.color
    state.timeout = typeof payload.timeout === 'undefined' ? 6000 : payload.timeout
    state.message = payload.message
  },
  showSuccessDialog (state, payload) {
    state.successdialog = true
    state.message = payload.message
    state.header = payload.header ? payload.header : 'Success'
  },
  hideSuccessDialog (state) {
    state.successdialog = false
    state.message = ''
  },
  showFailedDialog (state, payload) {
    state.faileddialog = true
    state.message = payload.message
  },
  hideFailedDialog (state) {
    state.faileddialog = false
    state.message = ''
  },
  showOverlay (state) {
    state.overlay = true
  },
  hideOverlay (state) {
    state.overlay = false
  }
}

const actions = {
  displayAlert ({ commit }, payload) {
    commit('setAlert', payload)
  },
  displaySuccessDialog ({ commit }, payload) {
    commit('showSuccessDialog', payload)
  },
  displayFailedDialog ({ commit }, payload) {
    commit('showFailedDialog', payload)
  },
  closeSuccessDialog ({ commit }) {
    commit('hideSuccessDialog')
  },
  closeFailedDialog ({ commit }) {
    commit('hideFailedDialog')
  },
  displayOverlay ({ commit }) {
    commit('showOverlay')
  },
  closeOverlay ({ commit }) {
    commit('hideOverlay')
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
