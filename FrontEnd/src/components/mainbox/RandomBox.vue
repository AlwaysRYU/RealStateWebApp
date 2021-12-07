<template>
  <div>
      <div id="now">관심 매물<hr id="narrow"></div>
      <div class="temp">
        <div class="row">
          <div class="col" style="margin-left : 10px">
            <div class="row">
              <div class="col"><h5 style="color: white !important">{{ building.AptName }}</h5></div>
            </div>
            <div class="row">
              <div class="col"><h2 style="display:inline !important; color: white !important">{{ building.dealAmount }}</h2><h6 style="display:inline !important; color: white !important">만원</h6></div>
            </div>
            <div class="row" style="margin-top : 10px">
              <div class="col"><h5 style="color: white !important">{{ building.dong }}</h5></div>
            </div>
            <div class="row">
              <div class="col">{{ building.buildYear }}년 완공 </div>
            </div>
          </div>
          <div class="col">
            <img :src=$store.state.building.detailImgsrc width="150" height="170" />
          </div>
        </div>

      </div>
  </div>
</template>

<script>
import http from "@/common/axios.js";

export default {
  name: "RandomBox",
  data: function () {
    return {
      building : {},

    };
  },
  methods : {

  },
  created() {
    //이미지 먼저 불러세팅하기 
  http
    .get("/randomapts")
    .then( ({data}) => {
      // console.log("랜덤 뽑기");
      // console.log(data);
      this.building = data;
      let fetchUrl = "https://www.googleapis.com/customsearch/v1?key=AIzaSyAuwaj-XuIV5W0KREKpp3rqx4FBhCGIToc&cx=110fb73c4bc171e93&q=site:https://hogangnono.com " + this.building.dong + " " + this.building.AptName + "&searchType=image";
      fetch(fetchUrl)
        .then((res) => {
          console.log(res);
          return res.json();
        })
        .then((results) => {
          console.log(results);
          this.$store.state.building.detailImgsrc = results.items[0].link;
          // console.error(results.items[0].link);
          // console.error(this.$store.state.building.detailImgsrc);
        });


    });

    

      
  }
}
</script>

<style src="../../css/main.css">

</style>

<style scoped src="@/css/volt.css">

</style>