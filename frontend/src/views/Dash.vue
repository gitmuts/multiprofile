<template>
<div>
    <nav-menu/>
    <v-main>
      <router-view></router-view>
    </v-main>
</div>
</template>

<script>
import NavMenu from '../components/NavMenu'
export default {
  name: 'Dash',
  components: {
    NavMenu
  },
  data: () => ({
  })
}
</script>
