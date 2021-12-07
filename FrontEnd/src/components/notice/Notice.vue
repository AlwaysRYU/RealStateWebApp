<template>
  <div class="container">

    <h4 class="text-center">공지사항</h4>       

  <div class="input-group mb-3">
    <input v-model="$store.state.notice.SearchWord" @keydown.enter="noticeList" type="text" class="form-control">
    <button @click="noticeList" class="btn btn-primary" type="button" >Search</button>
  </div>

    <table class="table table-hover">
        <thead class="thead-light">
            <tr>
                <th class="border-0 rounded-start" style="text-align: center">제목</th>
                <th class="border-0" style="text-align: center">작성자</th>
                <th class="border-0" style="text-align: center">작성일시</th>
                <th class="border-0 rounded-end" style="text-align: center">조회수</th>
            </tr>
        </thead>
        <tbody>
            <tr style="cursor:pointer" v-for="(notice, index) in listGetters" @click="noticeDetail(notice.boardId)" v-bind:key="index">
                <td class="border-0">{{ notice.title }}</td>
                <td class="border-0" style="text-align: center">
                  <img v-bind:src="'http://localhost:8080/upload/' + notice.userProfileImageUrl" width="30" height="30" class="rounded-circle"  />
                  {{ notice.userName }}</td>
                <td class="border-0" style="text-align: center">{{ makeDateStr(notice.regDt.date.year, notice.regDt.date.month, notice.regDt.date.day, '.') }}</td>            
                <td class="border-0" style="text-align: center">{{ notice.readCount }}</td>
            </tr>
        </tbody>
      </table>

    <pagination v-on:call-parent="movePage"></pagination>

    <button class="btn btn-sm btn-primary" v-show="visible" @click="showInsertModal">글쓰기</button>

    <insert-modal v-on:call-parent-insert="closeAfterInsert"></insert-modal>
    <!-- props 제거 -->
    <detail-modal v-on:call-parent-change-to-update="changeToUpdate" v-on:call-parent-change-to-delete="changeToDelete"></detail-modal>
    <update-modal v-on:call-parent-update="closeAfterUpdate"></update-modal>
  </div>
</template>

<script>
import InsertModal from './modals/InsertModal.vue';
import DetailModal from './modals/DetailModal.vue';
import UpdateModal from './modals/UpdateModal.vue';

import { Modal } from 'bootstrap';

import http from "@/common/axios.js";
import util from "@/common/util.js";

import Pagination from './Pagination.vue';
// 알람
import Swal from 'sweetalert2';
// 삭제
import Vue from 'vue';
import VueAlertify from 'vue-alertify'; 
Vue.use(VueAlertify);

export default {
  
  name: 'Notice',
  components: { InsertModal, DetailModal, UpdateModal, Pagination },
  data(){
    return {
      // modal
      insertModal : null,
      detailModal : null,
      updateModal : null,
      
      // 글쓰기 button
      visible : ''

    }
  },
  computed :{
    // gttters 이용
    listGetters(){
      return this.$store.getters.getNoticeList; // no getBoardList()
    },
  },
  methods : {

    // list
    // store actions 에 구현
    // 가능한 한 가지 방법
    noticeList(){
      this.$store.dispatch('noticeList');      
    },

    // pagination
    movePage(pageIndex){
      console.log("BoardMainVue : movePage : pageIndex : " + pageIndex );

      // store commit 으로 변경
      // this.offset = (pageIndex - 1) * this.listRowCount;
      // this.currentPageIndex = pageIndex;
      this.$store.commit( 'SET_NOTICE_MOVE_PAGE', pageIndex );


      this.noticeList();
    },

    // util
    makeDateStr : util.makeDateStr,

    // insert
    showInsertModal(){
      this.insertModal.show();
    },

    closeAfterInsert(){
      this.insertModal.hide();
      this.noticeList();
    },

    noticeDetail(boardId){
      http.get(
      '/notice/' + boardId, // props variable
      )
      .then(({ data }) => {
        console.log("DetailModalVue: data : ");
        console.log(data);
        
        if( data.result == 'login' ){
          this.$router.push("/login")
        }else{
          this.$store.commit(
            'SET_NOTICE_DETAIL',
            { 
              boardId: data.dto.boardId,
              title: data.dto.title,
              content: data.dto.content,
              userName: data.dto.userName,
              regDt: this.makeDateStr(data.dto.regDt.date.year, data.dto.regDt.date.month, data.dto.regDt.date.day, '.'),
              fileList: data.dto.fileList,
              sameUser: data.dto.sameUser, // not data.dto.sameUser

            }
          );

          this.detailModal.show();
        }
      })
      .catch((error) => {
        console.log("DetailModalVue: error ");
        console.log(error);
      });

      
    },


    changeToUpdate(){ 

      this.detailModal.hide();
      this.updateModal.show();
    },

    closeAfterUpdate(){      


      this.updateModal.hide();
      this.noticeList();
    },

    changeToDelete(){
      this.detailModal.hide();

      var $this = this; // alertify.confirm-function()에서 this 는 alertify 객체
      this.$alertify.confirm(
        //'삭제 확인', '이 글을 삭제하시겠습니까?', <- ???? title 추가하면 cancel이 ok 처럼 동작
        '이 글을 삭제하시겠습니까?',
        function(){
          $this.noticeDelete(); // $this 사용
        },
        function(){
          console.log('cancel');
        }
      );
    },
    noticeDelete(){ // parameter 사용 X
    console.log(">>>>>>>>>>>>>>>>>>>>> " + this.$store.state.notice.boardId )
      http.delete(
        "/notice/" + this.$store.state.notice.boardId
        )
        .then(({ data }) => {
          console.log("NoticeVue: data : ");
          console.log(data);
          if( data.result == 'login' ){
            this.$router.push("/login")
          }else{
            this.noticeList();
          }
        })
        .catch( error => {
            console.log(error)
        });
      },
      showButton(){
        console.log(sessionStorage.getItem("userEmail"))
        console.log(sessionStorage.getItem("userClsf"))
        if( sessionStorage.getItem("userClsf") == '001'){
          return this.visible = true;
        }else{
          return this.visible = false;
        }
      },
      checkLogin() {
      if( sessionStorage.length == 0 || sessionStorage == null  ) {
        Swal.fire('로그인이 필요한 서비스 입니다.');
        //  console.log("로그인먼저 하세요! ");
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
  },
  created() {
    this.checkLogin();
    this.noticeList();
    this.showButton();
  },
  mounted() {
    this.insertModal = new Modal(document.getElementById('insertModal'));
    this.detailModal = new Modal(document.getElementById('detailModal'));
    this.updateModal = new Modal(document.getElementById('updateModal'));
  },

}
</script>

<style scoped src="@/css/volt.css">

</style>
