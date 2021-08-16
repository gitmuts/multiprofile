import http from '../http'

export default {
  getRoles () {
    return http.get('/role')
  },
  createRole (role) {
    return http.post('/role', role)
  },
  updateRole (user) {
    return http.put('/role', user)
  },
  deleteRole (user) {
    return http.delete('/role', user)
  }
}
