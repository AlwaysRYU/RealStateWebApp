<template>
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a class="page-link" href="#" aria-label="Previous" @click="noticePaginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li v-for="index in ( endPageIndex-startPageIndex + 1 )" :key="index"
          v-bind:class="{active: (startPageIndex + index - 1 == $store.state.notice.currentPageIndex)}" class="page-item">
        <a @click="noticePaginationChanged(startPageIndex + index - 1)" 
          class="page-link" href="#">{{ startPageIndex + index - 1 }}</a> <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <a class="page-link" href="#" aria-label="Next" @click="noticePaginationChanged(endPageIndex + 1)">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'NoticePagination',
  // props 사용 X
  // props: ['listRowCount', 'pageLinkCount', 'currentPageIndex', 'totalListItemCount'],
  data(){
    return {
    }
  },
  computed: {
    pageCount: function(){
      return this.$store.getters.getNoticePageCount;
    },
    startPageIndex: function(){
      return this.$store.getters.getNoticeStartPageIndex;
    },
    endPageIndex: function(){
      return this.$store.getters.getNoticeEndPageIndex;
    },
    prev: function(){
      return this.$store.getters.getNoticePrev;
    },
    next: function(){
      return this.$store.getters.getNoticeNext;
    }
  },
  methods:{
    // 부모에게 event 전달    
    noticePaginationChanged(pageIndex){
      console.log("paginationVue : noticePaginationChanged : pageIndex : " + pageIndex );
      this.$emit('call-parent', pageIndex);
    }
  },

}
</script>

<style scoped src="@/css/volt.css">

</style>
