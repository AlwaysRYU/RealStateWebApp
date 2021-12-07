<template>
    <div class="mt-4">
        <!-- Section -->
        <section class="mt-5 mt-lg-0 bg-soft d-flex align-items-center">
            <div class="container">
                <p class="text-center">
                    <router-link to="/" class="d-flex align-items-center justify-content-center">
                        <svg class="icon icon-xs me-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l2.293 2.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg>
                        Back to loginpage
                    </router-link>
                </p>
                <div class="row justify-content-center form-bg-image" data-background-lg="../assets/img/illustrations/signin.svg">
                    <div class="col-12 d-flex align-items-center justify-content-center">
                        <div class="bg-white shadow border-0 rounded border-light p-4 p-lg-5 w-100 fmxw-500">
                            <div class="text-center text-md-center mb-4 mt-md-0">
                                <h1 class="mb-0 h3" id="name" style="font-size:45px">회원 가입 </h1>
                            </div>
                            <form action="#" class="mt-4">
                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <label for="name" id="inputprofile">이름</label>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon1">
                                            <svg class="icon icon-xs text-gray-600" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path><path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path></svg>
                                        </span>
                                        <input type="text" class="form-control" placeholder="이름" v-model="userName"
                                        :class="{ 'is-valid': isUserNameFocusAndValid , 'is-invalid': isUserNameFocusAndInvalid  }"  
                                        @input="validateUserName"
                                        @focus="isUserNameFocus = true"/>
                                        <div class="valid-feedback">Valid.</div>
                                        <div class="invalid-feedback">올바른 이름을 입력해 주세요.</div>
                                    </div>  
                                </div>
                                <!-- End of Form -->
                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <label for="email" id="inputprofile">이메일</label>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon1">
                                            <svg class="icon icon-xs text-gray-600" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path><path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path></svg>
                                        </span>
                                        <input type="email" class="form-control" placeholder="example@company.com" v-model="userEmail"
                                        :class="{ 'is-valid': isUserEmailFocusAndValid , 'is-invalid': isUserEmailFocusAndInValid  }"
                                        @input="validateEmail" 
                                        @focus="isUserEmailFocus = true"/>
                                        <div class="valid-feedback">Valid.</div>
                                        <div class="invalid-feedback">올바른 Email 을 입력해 주세요.</div>
                                    </div>  
                                </div>
                                <!-- End of Form -->
                                <div class="form-group">
                                    <!-- Form -->
                                    <div class="form-group mb-4">
                                        <label for="password" id="inputprofile">비밀번호</label>
                                        <div class="input-group">
                                            <span class="input-group-text" id="basic-addon2">
                                                <svg class="icon icon-xs text-gray-600" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd"></path></svg>
                                            </span>
                                            <input type="password" placeholder="비밀번호" class="form-control" v-model="userPassword"
                                            :class="{ 'is-valid': isUserPasswordFocusAndValid , 'is-invalid': isUserPasswordFocusAndInvalid  }" 
                                            @input="validatePassword"
                                            @focus="isUserPasswordFocus = true"/>
                                            <div class="valid-feedback">Valid.</div>
                                            <div class="invalid-feedback">1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.</div>
                                        </div>  
                                    </div>
                                    <!-- End of Form -->
                                    <!-- Form -->
                                    <div class="form-group mb-4">
                                        <label for="confirm_password" id="inputprofile">비밀번호 확인</label>
                                        <div class="input-group">
                                            <span class="input-group-text" id="basic-addon2">
                                                <svg class="icon icon-xs text-gray-600" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd"></path></svg>
                                            </span>
                                            <input type="password" placeholder="비밀번호 확인" class="form-control" v-model="userPassword2"
                                            :class="{ 'is-valid': isUserPassword2FocusAndValid , 'is-invalid': isUserPassword2FocusAndInvalid  }" 
                                            @input="validatePassword2"
                                            @focus="isUserPassword2Focus = true"/>
                                            <div class="valid-feedback">Valid.</div>
                                            <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
                                        </div>  
                                    </div >

                                        <div class="mb-4">
                                            <div>
                                                <div class="form-check form-check-inline" v-for="(code, index) in codeList" :key="index">
                                                    <input class="form-check-input" :value="code.code" type="radio" v-model="userClsf">
                                                    <label class="form-check-label" id="inputprofile">{{ code.codeName}}</label>
                                                </div>
                                            </div>
                                        </div>

                                        

                                    <!-- End of Form -->
                                </div>

                                <div class="d-grid">
                                    <button @click="register" class="btn btn-gray-800" id="inputprofile">회원 가입</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div> 
</template>

<script>

import Vue from 'vue';
import VueAlertify from 'vue-alertify'; 
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
    // data 사용 X
    data(){
        return {
        // v-model
        userName: '',
        userEmail: '',
        userPassword: '',
        userPassword2: '',

         // focus
        isUserNameFocus: false,
        isUserEmailFocus: false,
        isUserPasswordFocus: false,
        isUserPassword2Focus: false,

        // validation
        isUserNameValid: false,
        isUserEmailValid: false,
        isUserPasswordValid: false,
        isUserPassword2Valid: false,

        // 회원 구분
        groupCode: '001',
        codeList: [],
        userClsf: '001' // 일반회원 default

        }
    },
    computed: {
        isUserNameFocusAndValid(){
            return this.isUserNameFocus && this.isUserNameValid;
        },
        isUserNameFocusAndInvalid(){
            return this.isUserNameFocus && ! this.isUserNameValid;
        },
        isUserEmailFocusAndValid(){
            return this.isUserEmailFocus && this.isUserEmailValid;
        },
        isUserEmailFocusAndInValid(){
            return this.isUserEmailFocus && ! this.isUserEmailValid;
        },
        isUserPasswordFocusAndValid(){
        return this.isUserPasswordFocus && this.isUserPasswordValid;
        },
        isUserPasswordFocusAndInvalid(){
        return this.isUserPasswordFocus && ! this.isUserPasswordValid;
        },
        isUserPassword2FocusAndValid(){
            return this.isUserPassword2Focus && this.isUserPassword2Valid;
        },
        isUserPassword2FocusAndInvalid(){
            return this.isUserPassword2Focus && ! this.isUserPassword2Valid;
        }
    },
    methods: {
        validateUserName() {
            this.isUserNameValid = this.userName.length > 0 ? true : false;
            console.log(this.isUserNameValid)
        },
        validateEmail() {
            // ^ 시작일치, $ 끝 일치
            // {2, 3} 2개 ~ 3개
            // * 0회 이상, + 1회 이상
            // [-_.] - 또는 _ 또는 .
            // ? 없거나 1회
            let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            this.isUserEmailValid = (regexp.test(this.userEmail)) ? true : false;
            console.log(this.isUserEmailValid)
        },
        validatePassword() {

            let patternEngAtListOne = new RegExp(/[a-zA-Z]+/);// + for at least one
            let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/);// + for at least one
            let patternNumAtListOne = new RegExp(/[0-9]+/);// + for at least one

            this.isUserPasswordValid = 
                ( patternEngAtListOne.test( this.userPassword ) 
                    && patternSpeAtListOne.test( this.userPassword )  
                    && patternNumAtListOne.test( this.userPassword )
                    && this.userPassword.length >= 8
                ) ? true : false;
        },
        validatePassword2() {
            this.isUserPassword2Valid = ( this.userPassword == this.userPassword2 ) ? true : false;
        },
        register(){
            if( ! this.isUserEmailValid || ! this.isUserPasswordValid || ! this.isUserPassword2Valid ) {
                console.log(this.isUserEmailValid);
                console.log(this.isUserPasswordValid);
                console.log(this.isUserPassword2Valid);
                alert("올바르게 입력해주세요");
            
            }else{
            http.post(
                "/register",
                {
                    userName: this.userName,
                    userEmail: this.userEmail,
                    userPassword: this.userPassword,
                    userClsf: this.userClsf
                }
            )
            .then(({ data }) => {
                console.log("RegisterVue: data : ");
                console.log(data);

            let $this = this;
            this.$alertify.alert(
                '회원가입을 축하합니다. 로그인 페이지로 이동합니다',
                function() {
                    $this.$router.push("/login");
                }
            );        
            })
            .catch( error => {
                console.log("RegisterVue: error : ");
                console.log(error);
                if( error.response.status == '404'){
                    this.$alertify.error('Opps!! 서버에 문제가 발생했습니다.');          
                }
            });
        }
        }
    },
    created(){
        http.get(
        "/codes",
        {
            // get query string
            params: {
            groupCode: this.groupCode
            }
        })
        .then(({ data }) => {
            console.log("RegisterVue: data : ");
            console.log(data);
            if( data.result == 'login' ){
            this.$router.push("/login")
            }else{
            this.codeList = data;
            }
        });
    }
}
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
  font-family: Elice;
  font-size: 20px;
}

#inputprofile {
  font-family: ONER;
  font-size: 20px;
}
</style>

<style scoped src="@/css/volt.css">

</style>