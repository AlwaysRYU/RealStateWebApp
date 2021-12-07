<template>
  <div class="weatherbox">
    <main>
      <div class="weather-wrap" v-if="typeof weather.main != 'undefined'">
        <div class="location-box">
          <div class="now" id="now">
            오늘의 날씨
            <hr id="narrow">
          </div>
          <!-- 날짜 -->
          <div class="date" id="content-center">{{ dateBuilder() }}</div>
        </div>

        <div class="temp" id="content-center" >
          <img :src="weatherimgsrc" width="100" height="100"  >
         
          <div class="weather">{{ weatherBuilder() }}</div>
           <div>
          <span class="temp">현재기온 : {{ weather.main.temp }} ℃   </span>
          
          <span class="temp">습도 : {{ weather.main.humidity }} %</span>

          </div>
          <div>
            <span class="temp">최저기온 : {{ weather.main.temp_min }}℃  </span>
          <span class="temp">최고기온 : {{ weather.main.temp_max }}℃  </span>
          
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
//http://api.openweathermap.org/data/2.5/weather?q=seoul&units=metric&APPID=03d1adfce60c32b8c281a13bf8ad4f64

  

export default {
  name: "Weather",
  data: function () {
    return {
      api_key: "03d1adfce60c32b8c281a13bf8ad4f64",
      url_base: "http://api.openweathermap.org/data/2.5/",
      weatherimgsrc  : '',
      query: "",
      weather: {},
    };
  },
  methods: {
    setResult: function (results) {
      this.weather = results;
      console.log(this.weather);
    },
    dateBuilder: function () {
      let d = new Date();
      let months = [
        "1월",
        "2월",
        "3월",
        "4월",
        "5월",
        "6월",
        "7월",
        "8월",
        "9월",
        "10월",
        "11월",
        "12월",
      ];
      let days = [
        "일요일",
        "월요일",
        "화요일",
        "수요일",
        "목요일",
        "금요일",
        "토요일",
      ];
      let day = days[d.getDay()];
      let date = d.getDate() + "일";
      let month = months[d.getMonth()];
      let year = d.getFullYear() + "년";
      return `${year} ${month} ${date} ${day} `;
    },
    weatherBuilder(){
        var weath = this.weather.weather[0].description;
        let descript = '';
        if (weath === 'sky is clear') {
            descript = "맑음";
            this.weatherimgsrc = require("../../assets/weatherimg/01SUN.png");
        } else if(weath === 'few clouds'){
            descript = "구름조금";
            this.weatherimgsrc = require("../../assets/weatherimg/02FC.png");
        } else if(weath === 'scattered clouds'){
            descript = "구름보통";
            this.weatherimgsrc = require("../../assets/weatherimg/03MC.png");
        } else if(weath === 'broken clouds'){
            descript = "구름많음";
            this.weatherimgsrc = require("../../assets/weatherimg/03MC.png");
        } else if(weath === 'overcast clouds'){
            descript = "흐림";
            this.weatherimgsrc = require("../../assets/weatherimg/04HC.png");
        } else if(weath === 'shower rain'){
            descript = "소나기";
            this.weatherimgsrc = require("../../assets/weatherimg/05SRain.png");
        } else if(weath === 'light rain'){
            descript = "여우비";
            this.weatherimgsrc = require("../../assets/weatherimg/05SRain.png");
        } else if(weath === 'moderate rain'){
            descript = "비";
            this.weatherimgsrc = require("../../assets/weatherimg/06Rain.png");
        } else if(weath === 'Rain'){
            descript = "비 많이옴!!";
            this.weatherimgsrc = require("../../assets/weatherimg/07HR.png");
        } else if(weath === 'Thunderstorm'){
            descript = "천둥번개";
            this.weatherimgsrc = require("../../assets/weatherimg/08Strom.png");
        } else if(weath === 'snow'){
            descript = "눈";
            this.weatherimgsrc = require("../../assets/weatherimg/09S.png");
        } else if(weath === 'mist'){
            descript = "안개";
            this.weatherimgsrc = require("../../assets/weatherimg/10Mist.png");
        } else if(weath ==='clear sky'){
            descript = "맑음";
            this.weatherimgsrc = require("../../assets/weatherimg/01SUN.png");
        }
        return `${descript}`;
    }
  },
    created() {
      let fetchUrl = "http://api.openweathermap.org/data/2.5/weather?q=seoul&units=metric&APPID=03d1adfce60c32b8c281a13bf8ad4f64";
        fetch(fetchUrl)
          .then((res) => {
            console.log(res);
            return res.json();
          })
          .then((results) => {
            return this.setResult(results);
          });

      console.error(this.weather);
    }
};
</script>

<style scoped src="../../css/main.css">

</style>
