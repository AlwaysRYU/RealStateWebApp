<template>
  <div class="container">

    <h4 class="text-center">게시판</h4>       

  <div class="input-group mb-3">
    <input v-model="$store.state.board.SearchWord" @keydown.enter="boardList" type="text" class="form-control">
    <button @click="boardList" class="btn btn-primary" type="button" >Search</button>
  </div>

    <table class="table table-hover">
        <thead class="thead-light">
            <tr>
                <!-- <th class="border-0 rounded-start">#</th> -->
                <th class="border-0 rounded-start" style="text-align: center">제목</th>
                <th class="border-0" style="text-align: center">작성자</th>
                <th class="border-0" style="text-align: center">작성일시</th>
                <th class="border-0 rounded-end" style="text-align: center">조회수</th>
            </tr>
        </thead>
        <tbody>
            <tr style="cursor:pointer" v-for="(board, index) in listGetters" @click="boardDetail(board.boardId)"  v-bind:key="index">
                <!-- <td class="border-0"><div><span class="h6">{{ board.boardId }}</span></div></td> -->
                <td class="border-0">{{ board.title }}</td>
                <td class="border-0" style="text-align: center">
                    
                    <img v-bind:src="'http://localhost:8080/upload/' + board.userProfileImageUrl" width="30" height="30" class="rounded-circle"  />
                    {{ board.userName }}</td>
                <td class="border-0" style="text-align: center">{{ makeDateStr(board.regDt.date.year, board.regDt.date.month, board.regDt.date.day, '.') }}</td>            
                <td class="border-0" style="text-align: center">{{ board.readCount }}</td>
            </tr>
        </tbody>
    </table>

    <pagination v-on:call-parent="movePage"></pagination>

    <button class="btn btn-sm btn-primary" @click="showInsertModal">글쓰기</button>

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

// 삭제
import Vue from 'vue';
import VueAlertify from 'vue-alertify'; 
Vue.use(VueAlertify);
// 알람
import Swal from 'sweetalert2';

export default {
  
    name: 'Notice',
    components: { InsertModal, DetailModal, UpdateModal, Pagination },
    data(){
        return {
        // modal
        insertModal : null,
        detailModal : null,
        updateModal : null,

        }
    },
    computed :{
        // gttters 이용
        listGetters(){
        return this.$store.getters.getBoardList; // no getBoardList()
        },
    },
    methods : {

        // list
        // store actions 에 구현
        // 가능한 한 가지 방법
        boardList(){
        this.$store.dispatch('boardList');      
        },

        // pagination
        movePage(pageIndex){
        console.log("BoardVue : movePage : pageIndex : " + pageIndex );

        // store commit 으로 변경
        // this.offset = (pageIndex - 1) * this.listRowCount;
        // this.currentPageIndex = pageIndex;
        this.$store.commit( 'SET_BOARD_MOVE_PAGE', pageIndex );


        this.boardList();
        },

        // util
        makeDateStr : util.makeDateStr,

        // insert
        showInsertModal(){
        this.insertModal.show();
        },

        closeAfterInsert(){
        this.insertModal.hide();
        this.boardList();
        },

        boardDetail(boardId){
        http.get(
        '/boards/' + boardId, // props variable
        )
        .then(({ data }) => {
            console.log("DetailModalVue: data : ");
            console.log(data);
            
            if( data.result == 'login' ){
            this.$router.push("/login")
            }else{
            this.$store.commit(
                'SET_BOARD_DETAIL',
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
        this.boardList();
        },

        changeToDelete(){
        this.detailModal.hide();

        var $this = this; // alertify.confirm-function()에서 this 는 alertify 객체
        this.$alertify.confirm(
            //'삭제 확인', '이 글을 삭제하시겠습니까?', <- ???? title 추가하면 cancel이 ok 처럼 동작
            '이 글을 삭제하시겠습니까?',
            function(){
            $this.boardDelete(); // $this 사용
            },
            function(){
            console.log('cancel');
            }
        );
        },
        boardDelete(){ // parameter 사용 X
        console.log(">>>>>>>>>>>>>>>>>>>>> " + this.$store.state.board.boardId )
        http.delete(
            "/boards/" + this.$store.state.board.boardId
            )
            .then(({ data }) => {
            console.log("BoardVue: data : ");
            console.log(data);
            if( data.result == 'login' ){
                this.$router.push("/login")
            }else{
                this.boardList();
            }
            })
            .catch( error => {
                console.log(error)
            });
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
        this.boardList();
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


