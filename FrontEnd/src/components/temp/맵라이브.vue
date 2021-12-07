<template>
  <div class="container">
    <!-- #2 컨테이너 플루이드 -->
    <!-- 남아있는건 컨테이너 / 컨테이너 플루이드는 100프로로 양옆에 채워줌 -->
    <div class="my-3 d-flex justify-content-center">
      <h4>부동산 거래 목록 조회</h4>
    </div>

    <div class="left">
      <div class="input-group mb-3">
        <input
          id="inputSearchWord"
          type="text"
          class="form-control"
          placeholder="동이름으로 검색"
          aria-label="Recipient's username"
          aria-describedby="button-addon2"
          v-model="$store.state.building.searchWord"
          @keydown.enter="buildingList"
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
      <table class="table table-hover">
        <thead>
          <tr>
            <th>#</th>
            <th>동</th>
            <th>아파트 이름</th>
            <th>아파트 거래가</th>
            <th>크기</th>
          </tr>
        </thead>
        <!--  헤드  -->
        <tbody>
          <!-- store 사용 -->

          <!-- 직접 store 에 접근해도 된다. -->
          <!-- <tr v-for="(board, index) in $store.state.board.list" @click="boardDetail(board.boardId)" v-bind:key="index"> -->

          <!-- getters 를 이용하는 코드 -->
          <!-- computed - listGetters - getBoardList  
          v-for로 반복적으로 호출 . listGetters를 반복적으로. -->
          <tr
            style="cursor: pointer"
            v-for="(board, index) in listGetters"
            @click="buildingDetail(board.no)"
            v-bind:key="index"
          >
            <td>{{ board.no }}</td>
            <td>{{ board.dong }}</td>
            <td>{{ board.AptName }}</td>
            <td>{{ board.dealAmount }}</td>
            <td>{{ board.area }}</td>
          </tr>
        </tbody>
      </table>

      <pagination v-on:call-parent="movePage"></pagination>
    </div>
    <!-- 왼쪽 게시판  -->

    <!--           맵              -->
    <div class="right" id="wrapper" >
      <div id="map" style="width: 100%; height: 500px"/>
      <div id="list">
      <table class="table">
        <thead>
            <tr>
            <th scope="col">#</th>
            <th scope="col">First</th>
            <th scope="col">Last</th>
            <th scope="col">Handle</th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
            </tr>
            <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
            </tr>
            <tr>
            <th scope="row">3</th>
            <td colspan="2">Larry the Bird</td>
            <td>@twitter</td>
            </tr>
        </tbody>
        </table>
      </div>
    </div>
    <!-- 버튼 추가
	<button class="btn btn-primary" type="button" id="btnInsert"> 글쓰기 </button>
	  -->
    <detail-modal></detail-modal>
  </div>
  <!-- #2 컨테이너 -->
</template>

<script>
import http from "@/common/axios.js";
import util from "@/common/util.js";
import Pagination from "@/components/building/Pagination.vue";

import { Modal } from 'bootstrap';

import DetailModal from '@/components/building/BuildingDetail.vue';

export default {
  name: "Building",
  //컴포넌트 사용
  components : { Pagination, DetailModal},
  data() {
    return {
      map : null,
      detailModal : null,
      buildinglist : [],
      positions : [],
      markers : [],
    };
  },
  computed: {
    listGetters() {
      // 게시판 불러오기
      return this.$store.getters.getBuildingList;
    },
  },
  methods: {
    buildingList() {
      this.$store.dispatch("buildingList");

      this.markers.forEach(element => {
        element.setMap(null);
      })
      
      this.addmarkers();
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
    buildingDetail(no) {
      http
        .get("/building/" + no)
        .then(({ data }) => {
          console.log("빌딩자세히보기: data : ");
          console.log(data);

          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.$store.commit("SET_BUILDING_DETAIL", {
              // 갔다와서 상세안에 다 집어넣기
            no : data.dto.no,
            dong : data.dto.dong,
            AptName : data.dto.AptName,
            dealAmount : data.dto.dealAmount,
            buildYear : data.dto.buildYear,
            dealYear : data.dto.dealYear,
            floor : data.dto.floor,
            area : data.dto.area,
            gugunName : data.dto.gugunName,
            //  : , I.lat, I.lng

            });

            this.detailModal.show();
          }
        })
        .catch((error) => {
          console.log("DetailModalVue: error ");
          console.log(error);
        });
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
    },
    addmarkers() {
      // 여기서부터 다시
      this.markers = [];
      
      this.$store.state.building.markers.forEach(element => {
        console.log(element);
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: new kakao.maps.LatLng(element[0],element[1]),
        });
        this.markers.push(marker);
      });

      this.markers.forEach(element => {
          element.setMap(this.map);
      });

      var moveLatLon = new kakao.maps.LatLng(this.$store.state.building.markers[0][0], this.$store.state.building.markers[0][1]);
      this.map.panTo(moveLatLon);

    }

  },
  created() {
    this.buildingList();
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src ="//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=3c4678e9fc747f81c35acab80fbff1c0";
        // "//dapi.kakao.com/v2/maps/sdk.       js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      document.head.appendChild(script);
    }


    this.detailModal = new Modal(document.getElementById('boardDetailModal'));
  }
};


const obj = {
    num : 5,
    // print : function() {
    //     // console.log(this.num); // 이건 요기 위에 num
    // }
    print : () => {
        console.log(this.num);
        // 이때 
    }
}
</script>

<style>
div.left {
  width: 45%;
  float: left;
  box-sizing: border-box;
}
div.right {
  width: 45%;
  float: right;
  box-sizing: border-box;
  margin-left: 1px;
}

#wrapper {
    position: relative;
    
}
#list {
    position: absolute;
    top : 10px;
    bottom: 10px;
    left : 10px;
    z-index : 100;
    
    width : 400px;
    padding : 10px;

    background-color: rgb(73, 71, 68);
    opacity: 0.9;

    overflow: scroll;

    overflow-y: scroll;
}
</style>
