<template>
  <div>
    <div class="container">
      <div class="row">
        <div class="col">
          <div class="image">
            <video class="image-thumnail" style="opacity: 0.9;" autoplay muted loop src="../assets/img/mainbuilding.mp4"></video>
            <div class="text">
              <img src="../assets/img/logo.png" height="160" width="400">
            </div>
          </div>
        </div>
      </div>
      
      <!-- <div class="row">
        
        <div class="col-md-8 col-sm-8 col-xs-8"> 
           <div id="news" > 
             알라라라라
           </div>
        </div>
        <div class="ccol-md-4 col-sm-4 col-xs-4"> 
          <a id="loginbutton" class="btn btn-sm animated-button victoria-four">로그인</a> 
        </div>
      </div> -->
    <div>
      <news/>
    </div>
      <div class="row" id="BoxRow">
        <div class="col" id="B">
          <notice />
        </div>
        <div class="col" id="B">
          <random />
        </div>
        <div class="col" id="B">
          <weather />
        </div>
      </div>
    </div>

    <div class="container" id="Box">
      <footers />
    </div>
  </div>
</template>

<script>
import Weather from "@/components/mainbox/WeatherBox.vue";
import Notice from "@/components/mainbox/NoticeBox.vue";
import Random from "@/components/mainbox/RandomBox.vue";
import News from "@/components/mainbox/NewsBox.vue";
import Footers from "@/components/Footer.vue";


export default {
  name: "Main",
  components: {
    Weather,
    Notice,
    Random,
    Footers,
    News
  },
};
</script>

<style src="@/css/main.css"></style>
