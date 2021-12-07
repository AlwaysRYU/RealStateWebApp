<template>
  <div class="modal" tabindex="-1" id="boardDetailModal" style="z-index: 1051" >
     <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content">
        <div class="modal-header bg-primary">
          <h4 style="color: white">{{ $store.state.building.AptName }}</h4>          
          <button
            style="color: white"
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        
        <div class="modal-body">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-8">
                <div class="row">
                  <div class="col-md-4"><h5>no.{{ $store.state.building.no }}</h5></div>
                </div>
                <div class="row">
                  <div class="col-md-8">
                    <div><h5 style="color: black !important; ">매매가</h5></div>
                    <div><h2 style="display:inline !important">{{ $store.state.building.dealAmount }}</h2><h6 style="display:inline !important">만원</h6></div>
                  </div>
                  <div class="col-md-4">
                      <div><h5 style="color: black !important; ">평균가</h5></div>
                       <div><h2 style="display:inline !important">{{ $store.state.building.detailAvg }}</h2><h6 style="display:inline !important">만원</h6></div>
                  </div>
                </div>
                <hr>
                <div class="row">
                  <div class="col-md-6">
                    <div><h5>층</h5></div>
                    <div><h5>{{ $store.state.building.floor }}</h5></div>
                  </div>
                  <div class="col-md-6">
                    <div><h5>평 수</h5></div>
                    <div><h5>{{ $store.state.building.area }}</h5></div>
                  </div>
                </div>
                <hr>
                <div class="row">
                  <div class="col-md-10">
                    <div><h5>위치</h5></div>
                    <div><h6>{{ $store.state.building.gugunName }} / {{ $store.state.building.dong }}</h6></div>
                  </div>
                </div>
                <hr>
                <div class="row">
                  <div class="col-md-6">
                    <div><h5>매매 년도</h5></div>
                    <div><h5>{{ $store.state.building.dealYear }}</h5></div>
                  </div>
                  <div class="col-md-6">
                    <div><h5>완공 년도</h5></div>
                    <div><h5>{{ $store.state.building.buildYear }}</h5></div>
                  </div>
                </div>
              </div>
              <div class="col-md-4">
                <!-- <img src="http://placeimg.com/250/390/arch" width="250" height="390"> -->
                <img :src=$store.state.building.detailImgsrc width="300" height="390" />
              </div>
            </div>
          </div>
        </div>

        <div class="modal-footer" style="text-align:center">
      <div class="container" id="console">
        <div class="row" id="console1">
          <div class="col-4">
            <img @click="before" src="../../assets/img/009_arrow-left.png" width="100" height="100" style="cursor:pointer;">
          </div>
          <div class="col-4">
            <img @click="likeit" :src="$store.state.building.likeimage" width="100" height="100" style="cursor:pointer;">
          </div>
          <div class="col-4">
            <img @click="next" src="../../assets/img/010_arrow-right.png" width="100" height="100" style="cursor:pointer;" >
          </div>
        </div>
        <h5 class="modal-title">  {{ $store.state.building.detailIndex + 1 }} / {{ $store.state.building.detaillist.length }}  </h5>
      </div>
    </div>

      </div>
    </div>
  </div>
</template>

<script>

import http from "@/common/axios.js";
// 알람
import Swal from 'sweetalert2';

export default {
    name : 'boardDetailModal',
    data(){
      return {
        // this.$store.state.building.detailIndex
        index : 0,
        imgsrc : '',
      }
    }, 
    methods : {

      async before() {
        if( this.$store.state.building.detailIndex > 0 ){
          this.$store.state.building.detailIndex -= 1;
          // 이게 인덱스임
          console.log(this.$store.state.building.detailIndex);

          var detail = this.$store.state.building.detaillist[this.$store.state.building.detailIndex];
          this.$store.commit("SET_BUILDING_DETAIL", {
                // 갔다와서 상세안에 다 집어넣기
              no : detail.no,
              dong : detail.dong,
              AptName : detail.AptName,
              dealAmount : detail.dealAmount,
              buildYear : detail.buildYear,
              dealYear : detail.dealYear,
              floor : detail.floor,
              area : detail.area,
              gugunName : detail.gugunName,
              //  : , I.lat, I.lng
              });

              var result = await this.alreadylikeit();
              if ( result ){
                this.$store.state.building.likeimage = require('../../assets/img/008_dislike.png');
            } else {
                this.$store.state.building.likeimage = require('../../assets/img/007_like.png');
            }

        } else {
          Swal.fire('첫번째 매물입니다.');
        }
      },
      async next(){
        if ( this.$store.state.building.detailIndex < this.$store.state.building.totaldetailcount -1 ){
          this.$store.state.building.detailIndex += 1;
          console.log(this.$store.state.building.detailIndex);
        var detail = this.$store.state.building.detaillist[this.$store.state.building.detailIndex];
        this.$store.commit("SET_BUILDING_DETAIL", {
              // 갔다와서 상세안에 다 집어넣기
            no : detail.no,
            dong : detail.dong,
            AptName : detail.AptName,
            dealAmount : detail.dealAmount,
            buildYear : detail.buildYear,
            dealYear : detail.dealYear,
            floor : detail.floor,
            area : detail.area,
            gugunName : detail.gugunName,
            //  : , I.lat, I.lng
            });

            var result = await this.alreadylikeit();
            if ( result ){
              this.$store.state.building.likeimage = require('../../assets/img/008_dislike.png');
            } else {
              this.$store.state.building.likeimage = require('../../assets/img/007_like.png');
            }

        } else {
          Swal.fire('마지막 매물입니다.');
        }
        
      },
      async alreadylikeit() {
        // 좋아요 중복체크 함수
        // true면 좋아요 목록에 있음
        // falase면 좋아요 목록에 없다. 
        var no = this.$store.state.building.no;
        const result = await http
        .get( 
            "/likey", {
              params: {
                 userSeq : sessionStorage.getItem("userSeq"),
                 likeName : no
              }
        })
        .then( ({data}) => {
          // console.log(data);
            if (data == -1 ){
              // -1 이면 좋아요 목록에 없다.
              console.log("좋아요 목록에 없다!!");
              this.$store.state.building.likecheck = false;
              return false;
            } else {
              // 아니면 좋아목록에 있다.
              console.log("좋아요 목록에 있다!!");
              this.$store.state.building.likecheck = true;
              return true;
            } 
          });
          
          // console.error(result);
          return result;

          
      },
      async likeit(){
        // alert("좋아요!");
        // 1.이미 목록에 있는지 체크하기
        // console.log(result);
        // console.log(this.$store.state.building.likecheck);
        var result = await this.alreadylikeit();
         if ( result ){
          // 2. 있다면 제거
          // Swal.fire("이미 좋아요 목록에 있습니다!", "좋아요 목록에서 제거합니다.");
          // 좋아요 제거 
          http
          .delete(
              "/likey",
              {
                params: {
                  userSeq : sessionStorage.getItem("userSeq"),
                  likeName : this.$store.state.building.no
                }
              }
          )
          .then(({ data }) => {
            this.$store.state.building.likeimage = require('../../assets/img/007_like.png');
            Swal.fire("좋아요가 해제 되었습니다.");
            console.error("좋아요 목록에서 제거! : ");
            console.log(data);
          })

          //리스트 다시불러오기
          var userSeq_now = sessionStorage.getItem("userSeq");
          http.get( 
            "/likelists",
            {
              params: {
                limit: this.$store.state.building.limit,
                offset: this.$store.state.building.offset,
                userSeq: userSeq_now,
              }
            })
            .then(({ data }) => {
              // console.log("짱나네");
              console.error(data);
              if( data.result == 'login' ){
                this.$router.push("/")
              }else{
                this.$store.commit( 'SET_BUILDING_LIST', data.list );
                this.$store.commit( 'SET_BUILDING_TOTAL_LIST_ITEM_COUNT', data.count );
                console.warn(data.list);
              }
          });


        } else {
          // 3. 목록에 없다면 추가 
          var formData = new FormData();
          formData.append("userSeq", sessionStorage.getItem("userSeq"));
          formData.append("likeName", this.$store.state.building.no);
          http
            .post(
              '/likey',
               formData,
              { headers: { 'Content-Type': 'multipart/form-data' } }

            )
            .then(({ data }) => {
            Swal.fire("좋아요 목록에 추가했습니다!");
            this.$store.state.building.likeimage = require('../../assets/img/008_dislike.png');
            console.log("좋아요 추가완료!  ");
            console.log(data);

          })
        }
      },

    },
    watch : {
      
    },
    mounted() {
      this.imgsrc = this.$store.state.building.detailImgsrc;
    }
};
</script>
<style scoped src="@/css/volt.css">
  #console{
    width: 100%;
    text-align: center;
  }
</style>
