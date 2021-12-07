<template>
  <div class="container">
    <div
      class="row justify-content-center form-bg-image"
      data-background-lg="../assets/img/illustrations/signin.svg"
    >
      <div class="col-12 d-flex align-items-center justify-content-center">
        <div
          class="
            signin-inner
            my-3 my-lg-0
            bg-white
            shadow-soft
            border
            rounded
            border-light
            p-4 p-lg-5
            w-100
            fmxw-500
          "
        >
          <div class="text-center text-md-center mb-4 mt-md-0">
            <img src="../assets/img/014_title.png"/>
          </div>
          <form action="#" class="mt-4">
            <!-- Form -->
            <div class="form-group mb-4">
              <label for="email" id="inputt" >이메일 </label>
              <div class="input-group">
                <span class="input-group-text" id="basic-addon1" style="background-color:black"
                  ><span class="fas fa-envelope"></span
                ></span>
                <input
                  type="email"
                  class="form-control"
                  v-model="$store.state.login.userEmail"
                  placeholder="사용자 이메일 "
                />
              </div>
            </div>
            <!-- End of Form -->
            <div class="form-group">
              <!-- Form -->
              <div class="form-group mb-4">
                <label for="password" id="inputt" > 비밀번호 </label>
                <div class="input-group">
                  <span class="input-group-text" id="basic-addon2" style="background-color:black">
                  <span class="fas fa-unlock-alt"></span
                  ></span>
                  <input
                    type="password"
                    v-model="$store.state.login.userPassword"
                    placeholder="사용자 비밀번호"
                    class="form-control"
                  />
                </div>
              </div>
              <!-- End of Form -->
              
            </div>
            <div style="text-align:center" >
            <button @click="login" class="btn btn-block btn-primary">로그인</button> | 
            <button @click="register" class="btn btn-block btn-primary">회원가입</button> 
            </div>
          </form>
          <div class="mt-3 mb-4 text-center">
            <span class="font-weight-normal"></span>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";


export default {
  name: "Login",
  methods: {
    login() {
      console.log("이메일 " + this.$store.state.login.userEmail);
      console.log("비밀번호 " + this.$store.state.login.userPassword);
      http
        .post("/login", {
          userEmail: this.$store.state.login.userEmail,
          userPassword: this.$store.state.login.userPassword,
        })
        .then(({ data }) => {
          console.log("LoginVue: data : ");
          console.log(data);
          // isLogin 포함 mutator 호출
          this.$store.commit("SET_LOGIN", {
            isLogin: true,
            userName: data.userName,
            userEmail : data.userEmail,
            userRegisterDate : data.userRegisterDate,
            userProfileImageUrl : data.userProfileImageUrl,
            userClsf: data.userClsf
          });
          console.log("유저이메일임!!");
          // 여기서 세션 스토리지에 적용하자.
          // sessionStorage.setItem("USER", this.$store.state.login);
          sessionStorage.setItem("userSeq", data.userSeq);
          sessionStorage.setItem("userName", this.$store.state.login.userName);
          sessionStorage.setItem("userEmail", this.$store.state.login.userEmail);
          sessionStorage.setItem("userRegisterDate", this.$store.state.login.userRegisterDate );
          sessionStorage.setItem("userProfileImageUrl", this.$store.state.login.userProfileImageUrl);
          sessionStorage.setItem("userClsf", this.$store.state.login.userClsf);
          // main 으로 이동
          window.open("http://localhost:5500/main", "_self");
          // this.$router.push("/main");
        })
        .catch((error) => {
          console.log("LoginVue: error : ");
          console.log(error);
          if (error.response.status == "404") {
            this.$alertify.error("이메일 또는 비밀번호를 확인하세요.");
          } else {
            this.$alertify.error("Opps!! 서버에 문제가 발생했습니다.");
          }
        });
    },
    register(){
        this.$router.push("/register");
    }
  },
  mounted() {

  }
};
</script>
<style scoped src="@/css/volt.css">
</style>

<style scoped>
@font-face {
  font-family: 'Elice';
  src: url(../assets/fonts/font_Elice.ttf) format('woff');
  font-weight: normal;
  font-style: normal;
}


#inputt{
  font-family: Elice;
  font-size: 20px;
}
</style>