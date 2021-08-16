import http from '../http'

export default {
  getToken (userLogin) {
    return http.post('/token', userLogin)
  },
  logout (token) {
    if (token === null) {
      return 'Token supllied is null'
    }
    const bearerIndex = token.indexOf('Bearer')
    if (bearerIndex !== -1) {
      token = token.substring(6, token.length)
    }
    return http.get(`/token/logout/${token}`)
  },
  switchAccount (org) {
    return http.post('/switch_account', org)
  }
}
