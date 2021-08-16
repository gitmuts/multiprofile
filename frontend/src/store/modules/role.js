import roleService from '../../api/role'
const state = {
  roles: [],
  role: {}
}

const mutations = {
  setRoles (state, roles) {
    state.roles = roles
  },
  setRole (state, role) {
    state.role = role
  }
}

const actions = {
  getRoles ({ commit }) {
    roleService.getRoles().then(response => {
      commit('setRoles', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  createRole ({ commit }, role) {
    commit('')
    return new Promise((resolve, reject) => {
      roleService.createRole(role).then((res) => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  updateRole ({ commit }, role) {
    commit('')
    return new Promise((resolve, reject) => {
      roleService.updateRole(role).then((res) => {
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
