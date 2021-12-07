<template>
  <div>
    <body style="background-color: #f2f4f6" >
    <nav-bar/>
    <router-view/>
    </body>
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'

export default {
  name: 'App',
  // data 사용 X
  // data(){
  //   return {
  //     isLogin: false,
  //     userInfo: {}
  //   }
  // },
  components: {
    NavBar
  },
  // methods 사용 X
  // methods:{
  //   loginSuccess( userInfo ){
  //     this.isLogin = true;
  //     this.userInfo = userInfo;
  //   }
  // }
}
</script>

<style>

</style>
