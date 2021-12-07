<template>
  <nav aria-label="Page navigation" id="pagebutton">
    <!-- 매매 리스트 페이지 -->
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a class="page-link" href="#" aria-label="Previous" @click="paginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li v-for="index in ( endPageIndex-startPageIndex + 1 )" :key="index"
          v-bind:class="{active: (startPageIndex + index - 1 == $store.state.building.currentPageIndex)}" class="page-item">
        <a @click="paginationChanged(startPageIndex + index - 1)" 
           class="page-link" href="#">{{ startPageIndex + index - 1 }}</a> <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <a class="page-link" href="#" aria-label="Next" @click="paginationChanged(endPageIndex + 1)">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'Pagination',
  // props 사용 X
  // props: ['listRowCount', 'pageLinkCount', 'currentPageIndex', 'totalListItemCount'],
  data(){
    return {
    }
  },
  computed: {
    pageCount: function(){
      return this.$store.getters.getPageCountB;
    },
    startPageIndex: function(){
      return this.$store.getters.getStartPageIndexB;
    },
    endPageIndex: function(){
      return this.$store.getters.getEndPageIndexB;
    },
    prev: function(){
      return this.$store.getters.getPrevB;
    },
    next: function(){
      return this.$store.getters.getNextB;
    }
  },
  methods:{
    // 부모에게 event 전달    
    paginationChanged(pageIndex){
      console.log("paginationVue : paginationChanged : pageIndex : " + pageIndex );
      this.$emit('call-parent', pageIndex);
    }
  },

}
</script>
<style scoped src="@/css/volt.css">
</style>
<style>
#pagebutton{
  width : 50%;
  padding: 10px 0px 0px 270px;

}
</style>