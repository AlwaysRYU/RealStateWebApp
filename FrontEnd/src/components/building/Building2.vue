<template>
  <div>
    <!-- #2 컨테이너 플루이드 -->
  <div class="mapPosition map_wrap">
    <div id="map" style="width: 100%; height: 650px" />
    <ul id="category">
            <li id="BK9" data-order="0" @click="onClickCategory('BK9')"> 
                <span class="category_bg bank"></span>
                은행
            </li>       
            <li id="MT1" data-order="1" @click="onClickCategory('MT1')"> 
                <span class="category_bg mart"></span>
                마트
            </li>  
            <li id="PM9" data-order="2" @click="onClickCategory('PM9')"> 
                <span class="category_bg pharmacy"></span>
                약국
            </li>  
            <li id="OL7" data-order="3" @click="onClickCategory('OL7')"> 
                <span class="category_bg oil"></span>
                주유소
            </li>  
            <li id="CE7" data-order="4" @click="onClickCategory('CE7')"> 
                <span class="category_bg cafe"></span>
                카페
            </li>  
            <li id="CS2" data-order="5" @click="onClickCategory('CS2')"> 
                <span class="category_bg store"></span>
                편의점
            </li>      
        </ul>
  </div> 

  <div class="container">
    <div>
        <!-- 일단여기안에다가 다넣자 -->
        <div class="input-group mb-3" id="searchbar">
        <input
          id="inputSearchWord"
          type="text"
          class="form-control"
          placeholder="동이름"
          aria-label="Recipient's username"
          aria-describedby="button-addon2"
          v-model="$store.state.building.searchWord"
          @keydown.enter="buildingList"
        />
        <input
          id="inputSearchWord"
          type="text"
          style="width: 100px;"
          class="form-control"
          placeholder="건물이름"
          aria-label="Recipient's username"
          aria-describedby="button-addon2"
          v-model="$store.state.building.searchWordName"  
        />
        <button 
          @click="buildingList" 
          class="btn btn-outline-success"
          type="button"
          id="btnSearchWord"
        >
          찾기
        </button>
        </div>

    <!-- 테이블 -->
    <table class="table table-hover rounded-end" id="Blist" >
        <thead>
          <tr>
            <th id="dong" >동</th>
            <th>이름</th>
          </tr>
        </thead>
        <!--  헤드  -->
        <tbody>
          <tr style="cursor:pointer"
              v-for="(board, index) in listGetters"
              @click="buildingDetailclick(index)"
              v-bind:key="index"
              id="aptD"
            >
            <td id="dong" >{{ board.dong }}</td>
            <td>{{ board.AptName }}</td>
          </tr>
        </tbody>

        <div style="text-align:center">
          <div class="justify-content-center" style="text-align:center">
          <pagination v-show="showlikedetail==false"  v-on:call-parent="movePage" style="display:inline-block; text-slign:center; "></pagination>
          </div>
        </div>

      </table>

    <!-- 나의 좋아요 리스트 -->
     <img id="heart_icon" src="../../assets/img/006_heart.png" @click="likelist"> 
     <img id="house_icon" src="../../assets/img/012_house.png" @click="firstlist" > 
    
    </div>
   
  <!--  -->
  </div>
  
  <detail-modal></detail-modal>
  </div>
</template>

<script>
// /*global kakao*/ 
import http from "@/common/axios.js";
import util from "@/common/util.js";

import { Modal } from 'bootstrap';

import Pagination from "@/components/building/Pagination.vue";

import DetailModal from '@/components/building/BuildingDetail.vue';
// 알람
import Swal from 'sweetalert2';

export default {
  name: "Building",
  components : {DetailModal, Pagination},
  //컴포넌트 사용
  data() {
    return {
      markers : [],
      detailModal : null,
      bList : [],
      select : document.querySelector("#aptD"),
      index : 0,
      
      // true면 좋아요 디테일 보는거임
      showlikedetail : false,

      // 주변 편의시설
      Imarkers : [],
      currCategory : '',
      level: 5,
      markersInfo : [],
      placeOverlay : new kakao.maps.CustomOverlay({zIndex:1}),
      contentNode : document.createElement('div'),

      

        
    };
  },

  computed: {
    listGetters() {
      // 게시판 불러오기
      console.log("listGetters() 함수 : 게시판 불러오기 ");
      // console.log(this.$store.getters.getBuildingList);
      return this.$store.getters.getBuildingList;
    },
    
  },
  methods: {
    // 빌딩불러오기
    buildingList() {
      
        this.$store.state.building.limit = 10;
      if( this.showlikedetail == true ){
        this.showlikedetail = false;
        this.$store.state.building.beforeSW = '뷁';
      }
      this.$store.dispatch("buildingList");
      // console.log("빌딩리스트일때 ");
      // console.log(this.$store.state.building.list);
    },// 로그인 했는지 확인하기
    checkLogin() {
      var link = document.location.href;
      console.log(link);
      if(link != 'http://localhost:5500/' && ( sessionStorage.length == 0 || sessionStorage == null ) ) {
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
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(35.15654485418116, 128.9953139918553),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
      // 20211123 추가 
      this.ps = new kakao.maps.services.Places(this.map);
      kakao.maps.event.addListener(this.map, 'zoom_changed', this.searchPlaces);

    },
    // 빌딩 디테일 
    buildingDetail(X) {
      // 리스트 클릭시 호풀되는 함수
      console.log("디테일");
      console.log(this.$store.getters.getBuildingList[X]);
      // 맵 이동 
      var golat = this.$store.getters.getBuildingList[X].lat;
      var golng = this.$store.getters.getBuildingList[X].lng;
      var moveLatLon = new kakao.maps.LatLng(golat, golng );
      this.map.panTo(moveLatLon)
      this.map.setLevel(2);

      // 이미지 불러오기
      let fetchUrl = "https://www.googleapis.com/customsearch/v1?key=AIzaSyAuwaj-XuIV5W0KREKpp3rqx4FBhCGIToc&cx=110fb73c4bc171e93&q=site:https://hogangnono.com " + this.$store.getters.getBuildingList[X].dong + " " + this.$store.getters.getBuildingList[X].AptName + "&searchType=image";
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

      http.get( 
        "/buildingdetail",
        {
          params: {
            dong: this.$store.getters.getBuildingList[X].dong,
            AptName: this.$store.getters.getBuildingList[X].AptName,
          }
        })
        .then(({ data }) => {
          // console.log(data);
          console.log("빌딩자세히보기 : ");
          // console.log(data);
          if( data.result == 'login' ){
            this.$router.push("/")
          }else{
            // 몇 개 알림있는지 알려주기
            Swal.fire({
                icon: 'success',
                title : this.$store.getters.getBuildingList[X].AptName,
                text: '총 ' + data.list.length +'건의 매물이 있습니다.'
            });

            // 평균 값 구하기
            var avg = 0;
            var sum = 0;
            data.list.forEach(element => {
              // var temp = element.dealAmount * 1;
              sum += parseInt(element.dealAmount);
            });
            avg = Math.round(( sum / data.list.length ) * 1000) ;
            this.$store.state.building.detailAvg = avg;
            console.log("평균가 : " + sum);

            this.$store.commit( 'SET_BUILDINGDETAIL_LIST', data.list );
            
            var detail = data.list[0];

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
            
            

            

            //내가 좋아요 했는지 안했는지 알기
             var no = this.$store.state.building.no;
             http
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
                    this.$store.state.building.likeimage = require('../../assets/img/007_like.png');
                  } else {
                    // 아니면 좋아목록에 있다.
                    console.log("좋아요 목록에 있다!!");
                    this.$store.state.building.likeimage = require('../../assets/img/008_dislike.png');
                  } 
              });


            this.$store.state.building.totaldetailcount = data.list.length;
            console.log("디테일길이");
            this.$store.state.building.detailIndex = 0;
            console.log(this.$store.state.building.totaldetailcount);

            console.log(this.$store.state.building.detaillist);
            this.detailModal.show();
          }

          });


    },
    likebuildingDetail(X) {
      // 클릭시 호풀되는 함수
      
      console.log("좋아요 눌렀을 때 나오는 디테일");
      console.log(this.$store.getters.getBuildingList[X]);
      let fetchUrl = "https://www.googleapis.com/customsearch/v1?key=AIzaSyAuwaj-XuIV5W0KREKpp3rqx4FBhCGIToc&cx=110fb73c4bc171e93&q=site:https://hogangnono.com " + this.$store.getters.getBuildingList[X].dong + " " + this.$store.getters.getBuildingList[X].AptName + "&searchType=image";
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

      // 이동 
      var golat = this.$store.getters.getBuildingList[X].lat;
      var golng = this.$store.getters.getBuildingList[X].lng;
      var moveLatLon = new kakao.maps.LatLng(golat, golng );
      this.map.panTo(moveLatLon)
      this.map.setLevel(2);

      //디테일띠우기 // 좋아요 눌렀을 때는 그냥 no로 받아오면됨 
      http.get( 
        '/likebuildingdetail/' + this.$store.getters.getBuildingList[X].no // props variable
        )
        .then(({ data }) => {
          // console.log(data);
          console.log("좋아요 빌딩자세히보기 : ");
          console.log(data);
          // console.log(data);

          if( data.result == 'login' ){
            this.$router.push("/")
          }else{
            
            var list = [];
            list.push(data);
            console.log(list);
            this.$store.commit( 'SET_BUILDINGDETAIL_LIST', list );
            
            var detail = list[0];
            console.log(detail);
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

            
            this.$store.state.building.detailAvg = detail.dealAmount;
            this.$store.state.building.totaldetailcount = list.length;
            console.log("디테일길이");
            this.$store.state.building.detailIndex = 0;
            console.log(this.$store.state.building.totaldetailcount);
            // 이미 좋아요 한 목록이라서 
            this.$store.state.building.likeimage = require('../../assets/img/008_dislike.png'); 
            console.log(this.$store.state.building.detaillist);
            this.detailModal.show();
          }

          });


    },
    movePage(pageIndex) {
      console.log("BoardMainVue : movePage : pageIndex : " + pageIndex);

      // store commit 으로 변경
      // this.offset = (pageIndex - 1) * this.listRowCount;
      // this.currentPageIndex = pageIndex;
      this.$store.commit("SET_BUILDING_MOVE_PAGE", pageIndex);
      this.buildingList();
    },
    makeDateStr: util.makeDateStr,
    addmarkers() {
      //console.log("마커추가");
      // map에다가 마커 넣기

      // 마커 초기화
      this.markers.forEach(element => {
        element.setMap(null);
      });

      this.markers = [];
      this.$store.getters.getBuildingList.forEach(element => {
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: new kakao.maps.LatLng(element.lat, element.lng),
        });
        this.markers.push(marker);
      });

      this.markers.forEach(element => {
          element.setMap(this.map);
      });
      
      //console.log("지도 이동용");
      if (this.$store.getters.getBuildingList[0] != null ){            //console.error(this.$store.getters.getBuildingList[0].lat);
        var startlat = this.$store.getters.getBuildingList[0].lat;
        var startlng = this.$store.getters.getBuildingList[0].lng;
         var moveLatLon = new kakao.maps.LatLng(startlat, startlng );
      this.map.panTo(moveLatLon)
      this.map.setLevel(3);
      }
      // 지도 이동
      // var moveLatLon = new kakao.maps.LatLng(this.$store.state.building.buildingList[0].lat, this.$store.state.building.buildingList[0].lng);
      // this.map.panTo(moveLatLon)

    },
    // 내가 좋아요 누른 리스트 확인
    likelist(){
      // alert("좋아요");
      // console.warn(sessionStorage);
      // alert("내가 좋아요 누른 매매 목록  보기!");
      if ( this.showlikedetail == false ){
        this.$store.state.building.limit = 100;
        this.$store.state.building.offset = 0;
      }

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
            // console.warn(data.count);
            Swal.fire({
                icon: 'success',
                title : sessionStorage.getItem("userName") + "님의 좋아요 리스트를 불러왔습니다.",
                text: '총 ' + data.list.length +'건의 매물이 있습니다.'
            });
            }

            if ( this.showlikedetail == false ){
                this.showlikedetail = true;
                this.state.building.currentPageIndex = 1;
            }

          });

    },
    buildingDetailclick( index ) {
      if ( this.showlikedetail == true ) {
          // 이때는 좋아요를 누른 상태
          // alert("좋아ㅛㅇ좋요ㅏ");
          this.likebuildingDetail(index);
      } else {
        this.buildingDetail(index);
      }
    }, 
    // 주변편의시설보기
    searchPlaces(){
      if(!this.currCategory){
        return;
      }
      this.Imarkers.forEach(element => {
          element.setMap(null);
        });
      this.Imarkers = [];
      this.ps.categorySearch(this.currCategory, this.placeSearchCB, {useMapBounds:true});
    },
    placeSearchCB(data, status, pagination){
      console.log('placeSearch')
      if (status === kakao.maps.services.Status.OK) {
          // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
          if( pagination.hasNextPage){
            this.displayPlaces(data);
          }
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
          // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },
    displayPlaces(places) {
      console.log('displayPlaces')
        // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
        // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
        var order = document.getElementById(this.currCategory).getAttribute('data-order');


        for ( var i=0; i<places.length; i++ ) {

            // 마커를 생성하고 지도에 표시합니다
            var marker = this.addIMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

                            // 마커와 검색결과 항목을 클릭 했을 때
            // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
            //this.markersInfo.push();
            //console.log(marker)
            var $this = this;
            (function(marker, place) {
                kakao.maps.event.addListener(marker, 'click', function() {
                    $this.displayPlaceInfo(place);
                });
            })(marker, places[i]);
        this.Imarkers.forEach(element => {
          element.setMap(this.map);
        })
        }

    },
    displayPlaceInfo(place){
        var content = `<div class="placeinfo">` +
                    `   <a class="title" href="` + place.place_url + `" target="_blank" title="` + place.place_name + `">` + place.place_name + `</a>`;   

        if (place.road_address_name) {
            content += `    <span title="` + place.road_address_name + `">` + place.road_address_name + `</span>` +
                        `  <span class="jibun" title="` + place.address_name + `">(지번 : ` + place.address_name + `)</span>`;
        }  else {
            content += `    <span title="` + place.address_name + `">` + place.address_name + `</span>`;
        }                
      
        content += `    <span class="tel">` + place.phone + `</span>` + 
                    `</div>` + 
                    `<div class="after"></div>`;

        this.contentNode.innerHTML = content;
        this.contentNode.className = 'placeinfo_wrap';
        this.placeOverlay.setContent(this.contentNode);
        this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
        this.placeOverlay.setMap(this.map); 


    },
    addIMarker(position, order){
      console.log('addIMarker')
      var imageSrc = 	'https://ifh.cc/g/rjzFzm.png';
      var imageSize = new kakao.maps.Size(27, 28);
      var imgOptions =  {
            spriteSize : new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(46, (order*36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        };
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
      var marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage 
      });
      this.Imarkers.push(marker);
      return marker;
      //kakao.maps.event.addListener(marker, 'click', this.dispalyPlaceInfo(this.Imarkers));
    },
    onClickCategory(id){
      // 현재 카테고리가 적용되어 있고 값이 다르다면 ->  삭제후 새로운 id적용
      if( this.currCategory != '' && this.currCategory != id){
          this.currCategory = '';
          this.Imarkers.forEach(element => {
            element.setMap(null);
          });
          this.placeOverlay.setMap(null);

          this.Imarkers = [];
          this.searchPlaces();

          this.currCategory = id;
          this.searchPlaces();

      }else if( this.currCategory != '' ){
          this.currCategory = '';
          this.Imarkers.forEach(element => {
            element.setMap(null);
          });
          this.placeOverlay.setMap(null);
          this.Imarkers = [];
          this.searchPlaces();
          
      }else{
          this.placeOverlay.setMap(null);
          this.currCategory = id;
          this.searchPlaces();
      }
      
    },
    // 리스트 초기화
    firstlist(){
      this.$store.state.building.limit = 10;
      this.$store.state.building.searchWord = '',
      this.$store.state.building.searchWordName = '',
      this.showlikedetail = false;
      this.$store.dispatch("buildingList");
    }

  },
  created() {
    this.checkLogin();
    this.buildingList();
    
  },
  mounted() {
    
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.type="text/javascript";
      // script.onload = () => kakao.maps.load(this.initMap);
      script.src ="//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=3c4678e9fc747f81c35acab80fbff1c0";
        // "//dapi.kakao.com/v2/maps/sdk.       js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      document.head.appendChild(script);
    }

    this.$store.state.building.searchWord = '';
    this.$store.state.building.searchWordName = '';
    this.$store.state.building.list = [];
    
    
    

    // const script = document.createElement("script");
    // script.type="text/javascript"
    // script.src ="//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=3c4678e9fc747f81c35acab80fbff1c0";
    // document.head.appendChild(script);
    // this.initMap();

    this.detailModal = new Modal(document.getElementById('boardDetailModal'));

  },
  watch : {
    listGetters : function(){
      this.addmarkers();
    },

  }
};
</script>

<style>
    #searchbar{
        position: absolute;
        top : 120px;
        left : 450px;

        width: 500px;

        z-index : 51;

        background-color: rgb(73, 71, 68);
        opacity: 0.9;
    }

    #dong {
      width: 100px;
    }

    #Blist{
        table-layout: fixed;
        color : white;

        position: absolute;
        left : 0px;
        top : 106px;

        width : 550px;

        background-color: rgb(72, 87, 107);
        opacity: 1;
        z-index : 50;

        overflow-y : auto;
    }

    #heart_icon{
       position: absolute;
       right : 1%;
       top : 200px;
       z-index : 51;
       width : 55px;
       height : 55px;
       cursor:pointer;

    }

    #house_icon{

      position: absolute;
       right : 1%;
       top : 250px;
       z-index : 51;
       width : 55px;
       height : 55px;
       cursor:pointer;
    }

.map_wrap, .map_wrap * {margin:0; padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap {position:relative;width:100%;height:0px;}
#category {position:absolute; top:10px ;right : 1%;border-radius: 5px; border:1px solid #909090;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);padding-left: 0rem !important;background: #fff;overflow: hidden;z-index: 2;}
#category li {float:left;list-style: none;width:50px;border-right:1px solid #acacac;padding:6px 0;text-align: center; cursor: pointer;}
#category li.on {background: #eee;}
#category li:hover {background: #E3E5E6;border-left:1px solid #acacac;margin-left: -1px;}
#category li:last-child{margin-right:0;border-right:0;}
#category li span {display: block;margin:0 auto 3px;width:27px;height: 28px;}
#category li .category_bg {background:url(https://ifh.cc/g/5ypW0K.png) no-repeat;}
#category li .bank {background-position: -10px 0;}
#category li .mart {background-position: -10px -36px;}
#category li .pharmacy {background-position: -10px -72px;}
#category li .oil {background-position: -10px -108px;}
#category li .cafe {background-position: -10px -144px;}
#category li .store {background-position: -10px -180px;}
#category li.on .category_bg {background-position-x:-46px;}
.placeinfo_wrap {position:absolute;bottom:28px;left:-150px;width:300px;}
.placeinfo {position:relative;width:100%;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;padding-bottom: 10px;background: #fff;}
.placeinfo:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
.placeinfo_wrap .after {content:'';position:relative;margin-left:-12px;left:50%;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
.placeinfo a, .placeinfo a:hover, .placeinfo a:active{color:#fff;text-decoration: none;}
.placeinfo a, .placeinfo span {display: block;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
.placeinfo span {margin:5px 5px 0 5px;cursor: default;font-size:13px;}
.placeinfo .title {font-weight: bold; font-size:14px;border-radius: 6px 6px 0 0;margin: -1px -1px 0 -1px;padding:10px; color: #fff;background: #1f2937;background: #1f2937 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
.placeinfo .tel {color:#0f7833;}
.placeinfo .jibun {color:#999;font-size:11px;margin-top:0;}

</style>

<style scoped src="@/css/volt.css">

</style>
<style scoped src="@/css/main.css">

</style>
