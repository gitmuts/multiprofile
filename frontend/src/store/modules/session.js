import sessionService from '../../api/session'
import router from '../../router'

const state = {
  user: {},
  token: {},
  loggedIn: false,
  loginError: {},
  message: ''
}
const mutations = {
  SET_USER (state, user) {
    state.user = user
  },
  SET_TOKEN (state, token) {
    state.token = token
  },
  SET_LOGIN_ERROR (state, loginError) {
    state.loginError = loginError
  },
  SET_LOGGED_IN (state, loggedIn) {
    state.loggedIn = loggedIn
  },
  SET_MESSAGE (state, message) {
    state.message = message
  }
}

const actions = {
  getToken ({ commit }, userLogin) {
    commit('SET_USER', {})
    return new Promise((resolve, reject) => {
      sessionService.getToken(userLogin).then(response => {
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  setUser ({ commit }, user) {
    commit('SET_USER', user)
  },
  setToken ({ commit }, token) {
    commit('SET_TOKEN', token)
  },
  setLoggedIn ({ commit }, loggedIn) {
    commit('SET_LOGGED_IN', loggedIn)
  },
  logout ({ commit, dispatch }) {
    const token = localStorage.getItem('token')
    if (token === null || token === 'null') {
      router.push('/login')
    }
    sessionService.logout(token).then(response => {
      commit('SET_USER', null)
      commit('SET_TOKEN', null)
      if (window.localStorage) {
        window.localStorage.setItem('user', null)
        window.localStorage.setItem('token', null)
      }
      state.loggedIn = false
      router.push(response.data.redirect ? response.data.redirect : '/login')
      dispatch('notification/resetNotifications', {}, { root: true })
      dispatch('alert/displayAlert', { snackbar: true, color: 'success', message: response.data.message ? response.data.message : 'Success' }, { root: true })
    }).catch(error => {
      dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: error.response.data ? error.response.data.message : 'Error ocurred' }, { root: true })
    })
  },
  resetLoginError ({ commit }) {
    commit('SET_LOGIN_ERROR', {})
  },
  hasPermission ({ state }, permission) {
    return state.user.permissions.includes(permission)
  },
  setMessage ({ commit }, message) {
    commit('SET_MESSAGE', message)
  },
  switchAccount ({ commit }, org) {
    commit('SET_USER', {})
    return new Promise((resolve, reject) => {
      sessionService.switchAccount(org).then(response => {
        resolve(response)
      }).catch(error => {
        reject(error)
      })
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
