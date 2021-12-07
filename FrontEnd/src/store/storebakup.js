import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);


import http from "@/common/axios.js";
import router from '@/router/index.js'


// import http from "@/common/axios.js";
// import router from '@/routers/routers.js'

export default new Vuex.Store({
  state: {
    login: { // 뷰어플리케이션에서 
      isLogin: false, // 로그인 되어있는 상태 // false면 로그인이 안되어있는 상태
      userEmail: '디폴트',// 로그인이 되어있으면,  밑 4개의 값이 설정이 됨.
      userPassword: '디폴트',
      userName: '',
      userRegisterDate: '',
      userProfileImageUrl : "profile/005_noprofile.PNG",
    },
    // 아파트 매물 관련
    building: {
      // 이거는 나중에 크기가 커지면 또 부분으로 처리할 수 있을 것이다.
      // list // 게시글 목록
      list: [],
      limit: 10,
      offset: 0,
      searchWord: '',
      searchWordName: '',

      // pagination //페이지 네이션
      listRowCount: 10,
      pageLinkCount: 3,
      currentPageIndex: 1,
      totalListItemCount: 0,
      
      //아파트 데이터 // 상세 
      no : 0,
      dong : '',
      AptName : '',
      dealAmount : '',
      buildYear : '',
      dealYear : '',
      floor : '',
      area : '',
      gugunName : '',
      // 마커스 리스트
      markers : [],

      // 디테일관련
      detaillist : [],
      totaldetailcount : 0,
      detailIndex : 0,

      // 좋아요 관련
      likecheck : false, // 중복체크
      likeimage : '', // 사용이미지

    },
    notice:{
        // list
        list: [],
        limit: 10,
        offset: 0,
        SearchWord: '',

        // pagination
        listRowCount: 10,
        pageLinkCount: 10,
        currentPageIndex: 1,
        totalListItemCount: 0,

        // detail, update, delete
        boardId: 0,
        title: '',
        content: '',
        userName: '',
        regDt: {},
        readCount: 0,
        fileList: [],
        sameUser: false
    },
    board:{
        // list
        list: [],
        limit: 10,
        offset: 0,
        SearchWord: '',

        // pagination
        listRowCount: 10,
        pageLinkCount: 10,
        currentPageIndex: 1,
        totalListItemCount: 0,

        // detail, update, delete
        boardId: 0,
        title: '',
        content: '',
        userName: '',
        regDt: {},
        readCount: 0,
        fileList: [],
        sameUser: false
    },

  },
  mutations: {
    SET_LOGIN(state, payload){
      state.login.isLogin = payload.isLogin;
      state.login.userName = payload.userName;
      state.login.userRegisterDate = payload.userRegisterDate;
      state.login.userProfileImageUrl = payload.userProfileImageUrl;

    },
    // 빌딩목록 관련
    SET_BUILDING_LIST(state, list){
      state.building.list = list
    },
    SET_BUILDINGDETAIL_LIST(state, list){
      state.building.detaillist = list
    },
    SET_BUILDING_TOTAL_LIST_ITEM_COUNT(state, count){
      state.building.totalListItemCount = count
    },
    // 페이지 이동 
    SET_BUILDING_MOVE_PAGE(state, pageIndex){
      state.building.offset = (pageIndex - 1) * state.building.listRowCount;
      state.building.currentPageIndex = pageIndex;
    },
    SET_BUILDING_DETAIL(state, payload){
      state.building.no = payload.no;
      state.building.dong = payload.dong;
      state.building.AptName = payload.AptName;
      state.building.dealAmount = payload.dealAmount;
      state.building.buildYear = payload.buildYear;
      state.building.dealYear = payload.dealYear;
      state.building.floor = payload.floor;
      state.building.area = payload.area;
      state.building.gugunName = payload.gugunName;
      
    },
    // 게시판 목록 관련
    SET_BOARD_LIST(state, list){
        state.board.list = list
    },

    SET_BOARD_TOTAL_LIST_ITEM_COUNT(state, count){
        state.board.totalListItemCount = count
    },

    SET_BOARD_MOVE_PAGE(state, pageIndex){
        state.board.offset = (pageIndex - 1) * state.board.listRowCount;
        state.board.currentPageIndex = pageIndex;
    },

    SET_BOARD_DETAIL(state, payload){
        state.board.boardId = payload.boardId
        state.board.title = payload.title;
        state.board.content = payload.content;
        state.board.userName = payload.userName;
        state.board.regDt = payload.regDt;
        state.board.fileList = payload.fileList;
        state.board.sameUser = payload.sameUser;
    },
    // 공지목록 관련
    SET_NOTICE_LIST(state, list){
        state.notice.list = list
    },

    SET_NOTICE_TOTAL_LIST_ITEM_COUNT(state, count){
        state.notice.totalListItemCount = count
    },

    SET_NOTICE_MOVE_PAGE(state, pageIndex){
        state.notice.offset = (pageIndex - 1) * state.notice.listRowCount;
        state.notice.currentPageIndex = pageIndex;
    },

    SET_NOTICE_DETAIL(state, payload){
        state.notice.boardId = payload.boardId
        state.notice.title = payload.title;
        state.notice.content = payload.content;
        state.notice.userName = payload.userName;
        state.notice.regDt = payload.regDt;
        state.notice.fileList = payload.fileList;
        state.notice.sameUser = payload.sameUser;
    },
  },
  actions: {
    // 빌딩 목록 불러오기
    buildingList(context){
      console.log("빌딩 목록 불러오기 버전2");
      http.get( 
        "/building",
        {
          params: {
            limit: this.state.building.limit,
            offset: this.state.building.offset,
            searchWord: this.state.building.searchWord,
            searchWordName: this.state.building.searchWordName
          }
        })
        .then(({ data }) => {
          // console.log(data);
          if( data.result == 'login' ){
            router.push("/")
          }else{
            context.commit( 'SET_BUILDING_LIST', data.list );
            context.commit( 'SET_BUILDING_TOTAL_LIST_ITEM_COUNT', data.count );
            }

          });
    },
    // buildingList(context){

    //   console.log("빌딩 목록 불러오기 ");
    //   http
    //   .get(
    //     "/building",
    //     {
    //       // get query string
    //       params: {
    //         limit: this.state.building.limit,
    //         offset: this.state.building.offset,
    //         searchWord: this.state.building.searchWord
    //       }
    //     })
    //     .then(({ data }) => {
    //       console.log("BUILDINGVue: data : ");
    //       console.log(data);
    //       if( data.result == 'login' ){
    //         router.push("/")
    //       }else{
    //         context.commit( 'SET_BUILDING_LIST', data.list );
    //         context.commit( 'SET_BUILDING_TOTAL_LIST_ITEM_COUNT', data.count );

    //       }

    //       this.state.building.markers = [];
    //       this.state.building.list.forEach(element => {
    //           this.state.building.markers.push([ element.lat, element.lng ] );
    //           // console.log(element);
    //       })
    //       console.error(this.state.building.markers);
    //   });
    // },
    boardList(context){
      console.log("게시판 목록 불러오기 ");
      http.get(
          "/boards",
          {
          // get query string

              params: {
                  limit: this.state.board.limit,
                  offset: this.state.board.offset,
                  SearchWord: this.state.board.SearchWord
              }
          })
          .then(({ data }) => {
              console.log("BoardVue: data : ");
              console.log(data);
              if( data.result == 'login' ){
                  router.push("/")
              }else{
              context.commit( 'SET_BOARD_LIST', data.list );
              context.commit( 'SET_BOARD_TOTAL_LIST_ITEM_COUNT', data.count );
              }
          });
  },
  // 공지 목록 불러오기
  noticeList(context){
      console.log("공지 목록 불러오기 ");
      http.get(
          "/notice",
          {
          // get query string

              params: {
                  limit: this.state.notice.limit,
                  offset: this.state.notice.offset,
                  SearchWord: this.state.notice.SearchWord
              }
          })
          .then(({ data }) => {
              console.log("NoticeVue: data : ");
              console.log(data);
              if( data.result == 'login' ){
                  router.push("/")
              }else{
              context.commit( 'SET_NOTICE_LIST', data.list );
              context.commit( 'SET_NOTICE_TOTAL_LIST_ITEM_COUNT', data.count );
              }
          });
  },

  },
  // 직접 stae에서 사용하는거
  getters : {
    isLogin : function(state){
      return state.login.isLogin;
    },
    // 빌딩 관련-----------------------------------------------
    // 목록을 보여주는거 state의 목록을 리턴함
    getBuildingList : function(state){
      return state.building.list;
    },
    // 여기서부턴 페이지 
    // pagination
    getPageCountB: function(state){
      return Math.ceil(state.building.totalListItemCount/state.building.listRowCount);
    },
    getStartPageIndexB: function(state){
      if( (state.building.currentPageIndex % state.building.pageLinkCount) == 0 ){ //10, 20...맨마지막
        return ((state.building.currentPageIndex / state.building.pageLinkCount)-1)*state.building.pageLinkCount + 1
      }else{
        return  Math.floor(state.building.currentPageIndex / state.building.pageLinkCount)*state.building.pageLinkCount + 1
      }
    },
    getEndPageIndexB: function(state, getters){
      let ret = 0;
      if( (state.building.currentPageIndex % state.building.pageLinkCount) == 0 ){ //10, 20...맨마지막
        ret = ((state.building.currentPageIndex / state.building.pageLinkCount)-1)*state.board.pageLinkCount + state.building.pageLinkCount;
      }else{
        ret = Math.floor(state.building.currentPageIndex / state.building.pageLinkCount)*state.building.pageLinkCount + state.building.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ( ret > getters.getPageCount ) ? getters.getPageCount : ret;
    },
    getPrevB: function(state){
      if( state.building.currentPageIndex <= state.building.pageLinkCount ){
        return false;
      }else{
        return true;
      }
    },
    getNextB: function(getters){
      if( getters.getEndPageIndex >= getters.getPageCount){
        return false;
      }else{
        return true;
      }
    },
    // 게시판 관련 -----------------------------------------------
    // 게시판을 보여주는거 state의 목록을 리턴함
    getBoardList : function(state){
        return state.board.list;
    },
    // 여기서부턴 페이지
    // pagination
    getBoardPageCount: function(state){
    return Math.ceil(state.board.totalListItemCount/state.board.listRowCount);
    },
    getBoardStartPageIndex: function(state){
        if( (state.board.currentPageIndex % state.board.pageLinkCount) == 0 ){ //10, 20...맨마지막
            return ((state.board.currentPageIndex / state.board.pageLinkCount)-1)*state.board.pageLinkCount + 1
        }else{
        return  Math.floor(state.board.currentPageIndex / state.board.pageLinkCount)*state.board.pageLinkCount + 1
        }
    },
    getBoardEndPageIndex: function(state, getters){
        let ret = 0;
        if( (state.board.currentPageIndex % state.board.pageLinkCount) == 0 ){ //10, 20...맨마지막
            ret = ((state.board.currentPageIndex / state.board.pageLinkCount)-1)*state.board.pageLinkCount + state.board.pageLinkCount;
        }else{
            ret = Math.floor(state.board.currentPageIndex / state.board.pageLinkCount)*state.board.pageLinkCount + state.board.pageLinkCount;
        }
    // 위 오류나는 코드를 아래와 같이 비교해서 처리
        return ( ret > getters.getBoardPageCount ) ? getters.getBoardPageCount : ret;
    },
    getBoardPrev: function(state){
        if( state.board.currentPageIndex <= state.board.pageLinkCount ){
            return false;
        }else{
        return true;
        }
    },
    getBoardeNext: function(getters){
        if( getters.getBoardEndPageIndex >= getters.getBoardPageCount){
            return false;
        }else{
            return true;
        }
    },
    // 공지 관련 --------------------------------------------
    // 목록을 보여주는거 state의 목록을 리턴함
    getNoticeList : function(state){
        return state.notice.list;
    },
    // 여기서부턴 페이지
    // pagination
    getNoticePageCount: function(state){
    return Math.ceil(state.notice.totalListItemCount/state.notice.listRowCount);
    },
    getNoticeStartPageIndex: function(state){
        if( (state.notice.currentPageIndex % state.notice.pageLinkCount) == 0 ){ //10, 20...맨마지막
            return ((state.notice.currentPageIndex / state.notice.pageLinkCount)-1)*state.notice.pageLinkCount + 1
        }else{
        return  Math.floor(state.notice.currentPageIndex / state.notice.pageLinkCount)*state.notice.pageLinkCount + 1
        }
    },
    getNoticeEndPageIndex: function(state, getters){
        let ret = 0;
        if( (state.notice.currentPageIndex % state.notice.pageLinkCount) == 0 ){ //10, 20...맨마지막
            ret = ((state.notice.currentPageIndex / state.notice.pageLinkCount)-1)*state.notice.pageLinkCount + state.notice.pageLinkCount;
        }else{
            ret = Math.floor(state.notice.currentPageIndex / state.notice.pageLinkCount)*state.notice.pageLinkCount + state.notice.pageLinkCount;
        }
    // 위 오류나는 코드를 아래와 같이 비교해서 처리
        return ( ret > getters.getNoticePageCount ) ? getters.getNoticePageCount : ret;
    },
    getNoticePrev: function(state){
        if( state.notice.currentPageIndex <= state.notice.pageLinkCount ){
            return false;
        }else{
        return true;
        }
    },
    getNoticeNext: function(getters){
        if( getters.getNoticeEndPageIndex >= getters.getNoticePageCount){
            return false;
        }else{
            return true;
        }
    },

  } ,
  modules: {},
});
