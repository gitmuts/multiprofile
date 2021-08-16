import userService from '../../api/user'
const state = {
  users: [],
  user: {}
}

const mutations = {
  setUsers (state, users) {
    state.users = users
  },
  setUser (state, user) {
    state.user = user
  }
}

const actions = {
  getUsers ({ commit }) {
    userService.getUsers().then(response => {
      commit('setUsers', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  getUserInfo ({ commit }, token) {
    userService.getUserInfo(token).then(response => {
      commit('setUser', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  createUser ({ commit }, user) {
    return new Promise((resolve, reject) => {
      userService.createUser(user).then((res) => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  updateUser ({ commit }, user) {
    return new Promise((resolve, reject) => {
      userService.updateUser(user).then((res) => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  completeRegistration ({ commit }, user) {
    return new Promise((resolve, reject) => {
      userService.completeRegistration(user).then((res) => {
        resolve(res)
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
