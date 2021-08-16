import Vue from 'vue'
import Vuex from 'vuex'
import session from './modules/session'
import alert from './modules/alert'
import user from './modules/user'
import role from './modules/role'
import permission from './modules/permission'
import dash from './modules/dash'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    alert,
    session,
    user,
    role,
    permission,
    dash
  }
})
