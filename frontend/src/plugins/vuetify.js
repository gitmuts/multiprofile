import Vue from 'vue'
import Vuetify from 'vuetify/lib'

Vue.use(Vuetify)

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: '#0f3057',
        secondary: '#00587a',
        accent: '#008891'
      }
    }
  }
})
