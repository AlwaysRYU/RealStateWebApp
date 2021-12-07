<template>
  <div>
    <main>
      <!-- Section -->
      <section class="vh-lg-100 mt-5 mt-lg-0 bg-soft d-flex align-items-center">
        <div class="container">
          <div class="row justify-content-center form-bg-image">
            
            <p class="text-center">
                <router-link to="/main" class="d-flex align-items-center justify-content-center">
                    <svg class="icon icon-xs me-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l2.293 2.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg>
                    Back to Main
                </router-link>
            </p>

            <div
              class="col-12 d-flex align-items-center justify-content-center"
            >
              <div
                class="
                  bg-white
                  shadow
                  border-0
                  rounded
                  p-4 p-lg-5
                  w-100
                  fmxw-500
                "
              >
                <h1 id="name" style="font-size:50px">회원 정보 수정</h1>
                <form action="#">
                  <!-- Form -->
                  <div class="mb-4">
                    <label for="email" id="inputprofile" >이름 변경</label>
                    <div class="input-group">
                      <input
                        type="email"
                        class="form-control"
                        placeholder="새 이름"
                        id="email"
                        v-model="userName"
                      />
                    </div>
                  </div>
                  <!-- End of Form -->
                  <!-- Form -->
                  <div class="form-group mb-4">
                    <label for="password" id="inputprofile" >새로운 비밀번호</label>
                    <div class="input-group">
                      <span class="input-group-text" id="basic-addon2">
                        <svg
                          class="icon icon-xs text-gray-600"
                          fill="currentColor"
                          viewBox="0 0 20 20"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            fill-rule="evenodd"
                            d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z"
                            clip-rule="evenodd"
                          ></path>
                        </svg>
                      </span>
                      <input
                        type="password"
                        placeholder="새 비밀번호"
                        class="form-control"
                        id="password"
                        v-model="userPassword"
                        required
                      />
                    </div>
                  </div>
                  <!-- End of Form -->
                  <!-- Form -->
                  <div class="form-group mb-4">
                    <label for="confirm_password" id="inputprofile" >비밀번호 확인</label>
                    <div class="input-group">
                      <span class="input-group-text" id="basic-addon2">
                        <svg
                          class="icon icon-xs text-gray-600"
                          fill="currentColor"
                          viewBox="0 0 20 20"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            fill-rule="evenodd"
                            d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z"
                            clip-rule="evenodd"
                          ></path>
                        </svg>
                      </span>
                      <input
                        type="password"
                        placeholder="비밀번호 확인"
                        class="form-control"
                        id="confirm_password"
                        required
                      />
                    </div>
                  </div>

                    <!-- 유저잉미지  -->
                  <div class="form-group mb-4">
                    <label for="confirm_password" id="inputprofile"> 프로필이미지 변경 </label>
                    <div class="input-group">
                      <label class="form-check-label" for="chkFileUploadInsert"></label>
                      <!-- 멀티플넣으면 파일여러개임 -->
                      <input @change="changeFile" type="file" id="inputFileUploadInsert">
                    </div>
                  </div>

                

                  <!-- End of Form -->
                  <div class="d-grid">
                    <button
                      type="submit"
                      class="btn btn-gray-800"
                      @click="UpdateProfile"
                      id="inputprofile"
                    >
                      수정완료
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";

Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
  name: "Profile",
  data() {
    return {
      image : '',
      userName: "",
      userPassword: "",
      fileList : []
    };
  },
  methods: {
    UpdateProfile() {
      

      var formData = new FormData();
      // console.log(this.$store.state.login.userEmail);
      formData.append("userEmail", sessionStorage.getItem("userEmail"));
      formData.append("userName", this.userName);
      formData.append("userPassword", this.userPassword);
      formData.append("userProfileImageUrl", sessionStorage.getItem("userProfileImageUrl"));

      // file upload
      var attachFiles = document.querySelector("#inputFileUploadInsert");
      console.log(this.userName + " / " + this.userPassword + " 파일업로드 : " + attachFiles );

      var cnt = attachFiles.files.length;
      for (var i = 0; i < cnt; i++) {
        formData.append("file", attachFiles.files[i]);
      }
      console.error(formData);
      http
        .post( 
          "/updateprofile",
          formData,
          { headers: { 'Content-Type': 'multipart/form-data' } })
        .then(({ data }) => {
          console.error("회원정보 수정 완료!");
          console.log(data);
          console.log(data.userName);
          
          alert("회원 정보를 수정 완료했습니다. 로그인 화면으로 되돌아 갑니다.")

          // isLogin 포함 mutator 호출
            this.$store.commit("SET_LOGIN", {
              isLogin: false,
              userName: "",
            });
          sessionStorage.clear();
          // main 으로 이동
          this.$router.push("/");
        })
        .catch((error) => {
          console.log("LoginVue: error : ");
          console.log(error);
          if (error.response.status == "404") {
            this.$alertify.error(
              "에러가 발생했습니다." + error.response.status
            );
          } else {
            this.$alertify.error(
              "에러가 발생했습니다." + error.response.status
            );
          }
        });
    },
    checkLogin() {  
        if ( this.$store.state.login.isLogin == false ){
            alert("로그인 부터 먼저 하세요 ! ");
          this.$router.push("/");
        }

    },
    changeFile(fileEvent) {
        if( fileEvent.target.files && fileEvent.target.files.length > 0 ){

          for( var i=0; i<fileEvent.target.files.length; i++ ){
            const file = fileEvent.target.files[i];
            this.fileList.push(URL.createObjectURL(file));
          }
        }
      },

  },
  created() {
      console.log(this.$store.state.login.userName + "님이 접속 중 ");
      this.checkLogin();
  }
};
</script>

<style>
@font-face {
  font-family: 'Elice';
  src: url(../assets/fonts/font_Elice.ttf) format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'ONER';
  src: url(../assets/fonts/ONETitle.ttf) format('woff');
  font-weight: normal;
  font-style: normal;
}

#name{
  text-align: center;
  font-family: Elice;
  margin-bottom: 30px;
}

#inputprofile {
  font-family: ONER;
  font-size: 20px;
}
</style>
