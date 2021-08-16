import http from '../http'

export default {
  getPermissions () {
    return http.get('/permission')
  },
  createPermission (permission) {
    return http.post('/permission', permission)
  },
  updatePermission (permission) {
    return http.put('/permission', permission)
  },
  deletePermission (permission) {
    return http.delete('/permission', permission)
  }
}
