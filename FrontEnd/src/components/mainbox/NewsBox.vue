<template>
<div>
    <div class="container">
      <div class="row">
        <div class="col-8" id="newsbox" >
          <div id="newstitle">📢오늘의 부동산 소식</div>
            <div style="margin-left: 30px">
            <div>📄 <a id="newscontent" v-bind:href="news1.link" target="_blank" >{{news1.title}}</a></div>
            <div>📄 <a id="newscontent" v-bind:href="news2.link" target="_blank" >{{news2.title}}</a></div>
            <div>📄 <a id="newscontent" v-bind:href="news3.link" target="_blank" >{{news3.title}}</a></div>
            </div>
        </div>
        <div class="col-4">
          <button class="btn" id="gologin" @click="goLoginpage">{{LoginBTstr}}</button>
        </div>
      </div>
    </div>
    

</div>
  
</template>

<script>
export default {
    name: "NewsBox",
    data : function() {
        return {
            news : {},
            news1 : {},
            news2 : {},
            news3 : {},
            LoginBTstr : '',
        }
    }, 
    methods : {
        goLoginpage(){

              if ( sessionStorage == null || sessionStorage.length == 0){
                this.$router.push("/");
            } else {
                this.$router.push("/Mypage");
            }
        }
    }, 
    created(){
        // 뉴스 가져오기
        let fetchUrl = "https://www.googleapis.com/customsearch/v1?key=AIzaSyAuwaj-XuIV5W0KREKpp3rqx4FBhCGIToc&cx=110fb73c4bc171e93&q=site:https://news.google.com 부동산";
        fetch(fetchUrl)
        .then((res) => {
          console.log(res);
          return res.json();
        })
        .then((results) => {
          console.log("뉴스 결과");
          console.log(results);
          this.news = results.items;
          console.log(this.news);
          
          this.news1 = this.news[0];
          this.news2 = this.news[1];
          this.news3 = this.news[2];
        });

        if ( sessionStorage == null || sessionStorage.length == 0){
          this.LoginBTstr = "로그인하러가기";
        } else {
          this.LoginBTstr = sessionStorage.getItem("userName") + "님, 안녕?";
        }
        
    }

}
</script>

<style>
@font-face {
  font-family: 'ONER';
  src: url(../../assets/fonts/ONETitle.ttf) format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'AG';
  src: url(../../assets/fonts/font_AG.ttf) format('woff');
  font-weight: normal;
  font-style: normal;
}

#gologin{
    font-family: AG;
    text-align: center;
    font-size: 50px;
    cursor: pointer;
    width: 400px;
    height: 150px;
    color:white;
    background-color: rgb(131, 90, 90);
}

#newsbox{
    color : white;
    background-color: rgb(49, 48, 48);
}
#newstitle{
  margin-top: 5px;
  margin-left: 15px;
  font-family: Elice;
  font-size: 35px;
}
a { text-decoration:none !important } 
#newscontent{
    color:white;
}

</style>