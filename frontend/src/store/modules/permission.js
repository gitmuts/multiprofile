import permissionService from '../../api/permission'
const state = {
  permissions: []
}

const mutations = {
  setPermissions (state, permissions) {
    state.permissions = permissions
  }
}

const actions = {
  getPermissions ({ commit }) {
    permissionService.getPermissions().then(response => {
      commit('setPermissions', response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  createPermission ({ commit }, permission) {
    return new Promise((resolve, reject) => {
      permissionService.createPermission(permission).then((res) => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  updatePermission ({ commit }, permission) {
    return new Promise((resolve, reject) => {
      permissionService.updatePermission(permission).then((res) => {
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
