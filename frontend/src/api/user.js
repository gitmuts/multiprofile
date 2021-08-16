import http from '../http'

export default {
  getUsers () {
    return http.get('/user')
  },
  createUser (user) {
    return http.post('/user', user)
  },
  updateUser (user) {
    return http.put('/user', user)
  },
  deleteUser (user) {
    return http.delete('/user', user)
  },
  getUserInfo (token) {
    return http.get(`/registration/${token}`)
  },
  completeRegistration (user) {
    return http.post('/registration/complete', user)
  }
}
