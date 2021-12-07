<template>
  <main>
    <!-- Section -->
    <section class="vh-lg-100 mt-5 mt-lg-0 bg-soft d-flex align-items-center">
      <div class="container">
        <div
          class="row justify-content-center form-bg-image"
          data-background-lg="../../assets/img/illustrations/signin.svg"
        >

        <p class="text-center">
                <router-link to="/main" class="d-flex align-items-center justify-content-center">
                    <svg class="icon icon-xs me-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l2.293 2.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg>
                    Back to Main
                </router-link>
            </p>
            

          <div class="col-12 d-flex align-items-center justify-content-center">
            <div
              class="bg-white shadow border-0 rounded p-4 p-lg-5 w-100 fmxw-500"
            >
              <div class="text-center text-md-center mb-4 mt-md-0">
                <div>
                  <!-- 이미지 넣기 <div class="avatar avatar-lg mx-auto mb-3" > src="../assets/img/003_mypage_temp.png" -->
                  <!-- <img
                    v-bind:src="requireImg" 
                    class="rounded-circle"
                    alt="Image placeholder"
                    
                    width="300px"
                    height="300px"
                  /> -->

                  <img v-bind:src="imgsrc" 
                    class="rounded-circle"
                    alt="Image placeholder"
                    width="300px"
                    height="300px"
                    style="margin-bottom:30px"/>

                </div>
                <div id="hello" >
                  {{ Name }} 님, 반갑습니다!
                </div>
                <p id="name" >죽이는 방은 찾으셨나요?</p>

                <div class="table-responsive">
                  <table class="table table-centered table-nowrap mb-0 rounded">
                    <thead class="thead-light">
                      <tr>
                        <th class="border-0">회원 이메일</th>
                        <th class="border-0">회원 가입날짜</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- Item -->
                      <tr>
                        <td>{{ Email }}</td>
                        <td>{{ RegisterDate }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <form class="mt-5">
                <!-- Form -->
                
                


                <!-- End of Form -->
                <div class="d-grid mt-3">
                  <div class="card-body">
                    <button
                      class="btn btn-lg btn-dark"
                      type="button"
                      @click="Profile"
                    >
                      회원 수정
                    </button>

                    <button
                      class="btn btn-lg btn-dark"
                      style="margin: 0px 0px 0px 120px"
                      type="button"
                      @click="askSignout"
                    >
                      회원 탈퇴
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>

  </main>
</template>

<script>
import http from "@/common/axios.js";
// 알람
import Swal from 'sweetalert2';

export default {
  name: "Mypage",
  data() {
    return{
      imgsrc : '',
      RegisterDate : sessionStorage.getItem("userRegisterDate"),
      Name : sessionStorage.getItem("userName"),
      Email : sessionStorage.getItem("userEmail"),
    }
  },  
  methods: {
    Profile() {
      this.$router.push("/Profile");
    },
    // 로그인 했는지 확인하기
    checkLogin() {
      var link = document.location.href;
      if(link != 'http://localhost:5500/' && ( sessionStorage.length == 0 || sessionStorage == null ) ) {
        Swal.fire('로그인이 필요한 서비스 입니다.');
        this.$router.push("/");
       } else {
         this.$store.commit("SET_LOGIN", {
            isLogin: true,
            userName: sessionStorage.getItem("userName"),
            userRegisterDate : sessionStorage.getItem("userRegisterDate"),
            userProfileImageUrl : sessionStorage.getItem("userProfileImageUrl"),
          });
       }
    },
    askSignout(){
      Swal.fire({
          title: '정말로 탈퇴 하시는 건가요?',
          text: "회원 정보가 모두 삭제됩니다.",
          icon: 'error',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '회원탈퇴',
          cancelButtonText: '취소'
      }).then((result) => {
          if (result.isConfirmed) {
              this.Signout();
          }
      })

    }, 
    Signout() {
      
      http
        .delete(
            "/deleteuser/" + sessionStorage.getItem("userEmail")
        )
        .then(({ data }) => {
          alert("회원탈퇴가 완료되었습니다.");
          console.error("회원탈퇴! : ");
          console.log(data);

          // isLogin 포함 mutator 호출
         

          // main 으로 이동
          this.$router.push("/");
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
    }
  },
  //https://scontent-gmp1-1.xx.fbcdn.net/v/t1.6435-9/146920428_262114008606476_1455088809082829943_n.jpg?_nc_cat=1&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=rK6HZ9bztMkAX_U7NEa&_nc_ht=scontent-gmp1-1.xx&oh=35b8349116cb90ab7ab61aed01b16343&oe=61BB1F04
  computed : {
    requireImg(){
      if( this.$store.state.login.userProfileImageUrl == '' ) {
        // src="../assets/img/003_mypage_temp.png"
        return require('../assets/img/005_noprofile.jpg')
      }else{        
        
        return require(this.imgsrc)
        // return require('../assets/' + this.$store.state.login.userProfileImageUrl);
      }
    }
  },
  created() {
    this.checkLogin();
    this.imgsrc = "http://localhost:8080/upload/" + this.$store.state.login.userProfileImageUrl;
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

#hello{
  text-align: center;
  font-family: Elice;
  font-size: 40px;
}

.btn-primary {
  color: #ffffff;
  background-color: #1f2937;
  border-color: #1f2937;
  box-shadow: inset 0 1px 0 rgb(255 255 255 / 15%), 0 1px 1px rgb(17 24 39 / 8%);
}

.btn {
  transition: none;
}
</style>
