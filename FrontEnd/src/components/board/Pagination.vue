<template>
  <nav aria-label="Page navigation expample">
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a class="page-link" href="#" aria-label="Previous" @click="boardPaginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li v-for="index in ( endPageIndex-startPageIndex + 1 )" :key="index"
          v-bind:class="{active: (startPageIndex + index - 1 == $store.state.board.CurrentPageIndex)}" class="page-item">
        <a @click="boardPaginationChanged(startPageIndex + index - 1)" 
          class="page-link" href="#">{{ startPageIndex + index - 1 }}</a> <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <a class="page-link" href="#" aria-label="Next" @click="boardPaginationChanged(endPageIndex + 1)">
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
      return this.$store.getters.getBoardPageCount;
    },
    startPageIndex: function(){
      return this.$store.getters.getBoardStartPageIndex;
    },
    endPageIndex: function(){
      return this.$store.getters.getBoardEndPageIndex;
    },
    prev: function(){
      return this.$store.getters.getBoardPrev;
    },
    next: function(){
      return this.$store.getters.getBoardNext;
    }
  },
  methods:{
    // 부모에게 event 전달    
    boardPaginationChanged(pageIndex){
      console.log("paginationVue : boardPaginationChanged : pageIndex : " + pageIndex );
      this.$emit('call-parent', pageIndex);
    }
  },

}
</script>

<style scoped src="@/css/volt.css">

</style>