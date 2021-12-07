<template>
    <nav class="navbar navbar-expand-lg navbar-transparent navbar-dark navbar-theme-primary" style = "padding: 0px 0px 0px 0px;">
      <div class="container-fluid">
        <img src="../assets/img/013_jukbangTitle.png" @click="main" width="112" height="58" style=" cursor:pointer" />
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" @click="building" id="menuname">
                아파트 매매 정보
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" @click="notice" id="menuname" >공지사항</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" @click="board" id="menuname" >게시판</a>
            </li>
          </ul>
        </div>

        <div
          class="collapse navbar-collapse justify-content-end"
          id="navbarNav"
        >
          <ul class="navbar-nav">

                

            <!-- 프로필이미지  http://localhost:8080/upload/    -->
            <li class="nav-item"> 
              <a class="nav-link" id="myimg" >
                <img :src="imgsrc" class="rounded-circle" width="58" height="58"  @click="profile" >
              </a>
            </li>

            <li class="nav-item">
              <a class="nav-link" id="logout" @click="asklogout" >
                <img src="../assets/img/002_logout.png" width="58" height="58" >
              </a>
            </li>


          </ul>
        </div>

        
      </div>



    </nav>
  
</template>

<script>
// 알람
import Swal from 'sweetalert2';

export default {
  name: "NavBar",
  data(){
    return {
      imgsrc : "http://localhost:8080/upload/" + this.$store.state.login.userProfileImageUrl,
      myimg : false,
    }
  }, 
  computed: {

  },
  methods : {
    profile(){
        this.$router.push("/Mypage")
    },
    checkLogin() {
      var link = document.location.href;
      console.log(link);
      if(link != 'http://localhost:5500/' && ( sessionStorage.length == 0 || sessionStorage == null ) ) {
         this.myimg = false;
         Swal.fire('로그인 먼저 하세요');
        this.$router.push("/");
       } else {
         if (link == 'http://localhost:5500/' ) {
            this.myimg = false;
         } else {
          this.myimg = true;
          this.$store.commit("SET_LOGIN", {
              isLogin: true,
              userName: sessionStorage.getItem("userName"),
              userRegisterDate : sessionStorage.getItem("userRegisterDate"),
              userProfileImageUrl : sessionStorage.getItem("userProfileImageUrl"),
            });
            this.imgsrc = "http://localhost:8080/upload/" + this.$store.state.login.userProfileImageUrl;
         }

       }
    },
    asklogout(){
      Swal.fire({
          title: '정말 로그아웃 하시겠어요?',
          text: "가지마세요😥",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '로그아웃',
          cancelButtonText: '취소'
      }).then((result) => {
          if (result.isConfirmed) {
              this.logout();
          }
      })
    }, 
    logout(){
      var link = document.location.href;
      if(link == 'http://localhost:5500/' || ( sessionStorage.length == 0 || sessionStorage == null ) ) {
        this.$router.push("/");
        Swal.fire('로그인 먼저 하세요!');
        //  console.log("로그인먼저 하세요! ");
        
       } else {
          Swal.fire('안전하게 로그아웃 됐습니다!');

          this.$store.commit("SET_LOGIN", {
                isLogin: false,
                userName: "",
                userRegisterDate : "",
              });
          sessionStorage.clear();
          this.myimg = false;
          this.imgsrc = "http://localhost:8080/upload/profile/005_noprofile.PNG";
          this.$router.push("/");
       }
    },
    building(){
      this.checkLogin();
      this.$router.push("/building")
    },
    main(){
        this.$router.push("/main")
    },
    notice(){
      this.checkLogin();
        this.$router.push("/notice");
    },
    board(){
      this.checkLogin();
      this.$router.push("/board");
    }
  },
  created(){
    this.checkLogin();
    console.log(this.myimg);
    if ( this.$store.state.login.userProfileImageUrl != null ){
        this.imgsrc = "http://localhost:8080/upload/" + this.$store.state.login.userProfileImageUrl;
      } else {
        this.imgsrc = "http://localhost:8080/upload/profile/005_noprofile.PNG";
      }

    console.log(sessionStorage);
  }, 
  watch : {
    myimg() {
      if ( this.$store.state.login.userProfileImageUrl != null ){
        this.imgsrc = "http://localhost:8080/upload/" + this.$store.state.login.userProfileImageUrl;
      } else {
        this.imgsrc = "http://localhost:8080/upload/profile/005_noprofile.PNG";
      }
    },
    
  }, 
  mounted() {
  }
};
</script>

<style scoped src="@/css/volt.css">

</style>

<style>
@font-face {
  font-family: 'KOTRA';
  src: url(../assets/fonts/font_KOTRALEAP.ttf) format('woff');
  font-weight: normal;
  font-style: normal;
}

#menuname {
  font-family: KOTRA;
  font-size: 25px;
}

</style>