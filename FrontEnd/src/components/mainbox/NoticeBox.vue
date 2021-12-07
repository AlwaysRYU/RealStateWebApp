<template>
  <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="height: 100% !important">
 
  
  <div class="carousel-inner" >
    <div class="carousel-item active">
      
      <div class="temp" >
        <div id="now"> 공지사항   </div>
        <hr id="narrow">
        <!-- <div  > 제목 : {{ notice.title }}  </div>
        <div> 작성자 : {{ notice.userName}} </div>
        <div> 내용 : {{ notice.content }}  </div>
        <div> 작성일 : {{ notice.regDt.date.year }}-{{ notice.regDt.date.month }}-{{notice.regDt.date.day }} </div> -->

        <div v-html="listnotice[0].title" id="ntitle"></div>
        <div v-html="listnotice[1].title" id="ntitle"></div>
        <div v-html="listnotice[2].title" id="ntitle"></div>
        <div v-html="listnotice[3].title" id="ntitle"></div>
    
      </div>

    </div>

    <div class="carousel-item">
 
        <div class="temp" >
    <div id="now">인기글</div>
    <hr id="narrow">
    <div id="ntitle-note">
      <div> <h5 style="color:white"># {{ board.title }} </h5> </div>
      <div> 작성자 : {{ board.userName}} </div>
      <div id="solid-main"><div v-html="board.content"></div></div>
      <!-- <div> 작성일 : {{ board.regDt.date.year }}-{{ board.regDt.date.month }}-{{board.regDt.date.day }} </div> -->
  </div>


    </div>

  </div>
  </div>
 
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
  </div>

  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>

</div>
</template>

<script>
import http from "@/common/axios.js";

export default {
  name: "RandomBox",
  data: function () {
    return {
      board : {},
      notice : {},
      listnotice : {},

    };
  },
  methods : {
    popularBoards(){
      http
        .get("/popularboards")
        .then( ({data}) => {
            console.log("메인박스1번");
            console.log(data);
            console.log(data.list[0]);
            this.board = data.list[0];
            
            console.log(data.list[1]);
            this.notice = data.list[1];
        });
    },noticeList(){
      http
      .get("/noticeboards")
        .then( ({data}) => {
            console.log("공지 4개");
            console.log(data.list[0]);
            this.listnotice = data.list;
            console.log(this.listnotice)
        });
    }

  },
  created() {
    this.popularBoards();
    this.noticeList();
  }
}
</script>

<style src="../../css/main.css">

</style>