import axios from 'axios'
import store from './store'
import config from './config'
// create a new axios instance
const instance = axios.create({
  baseURL: config.baseURI
})

instance.interceptors.request.use(function (config) {
  const token = window.localStorage.getItem('token')
  const tokenUrls = ['/token', 'logout', 'register', 'contactus']
  var tokenUrl = 0
  for (var i = 0; i < tokenUrls.length; i++) {
    tokenUrl = config.url.indexOf(tokenUrls[i])
    if (tokenUrl !== -1) {
      break
    }
  }
  // if token exists and token url not the current request url used to get the token
  if (token && token && tokenUrl === -1) {
    console.log(token)
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, function (err) {
  return Promise.reject(err)
})

// before a response is returned stop nprogress
instance.interceptors.response.use(response => {
  console.log(response)
  return response
}, function (error) {
  // Do something with response error
  if (error.message.includes('Network Error')) {
    store.dispatch('alert/displayAlert', { snackbar: true, color: 'error', message: 'Failed to connect to server, check network connectivity' }, { root: true })
  }
  if (error.response && error.response.status === 401) {
    if (error.response.config.url.indexOf('token') === -1 && error.response.config.url.indexOf('refresh-token') === -1) {
      store.dispatch('session/logout', { root: true })
    }
  }
  return Promise.reject(error)
})

export default instance
