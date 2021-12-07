<template>
  <div>
    <div>
      <input v-model="text" type="text" />
      <button @click="setData">SetDataToVuex</button>
    </div>
    <div>
      <button @click="getData">GetDataFromVuex</button>
      <div>fromVuex : {{ sessionData }}</div>
    </div>
    <div>
      <button @click="getSessionData">getDataFromSessionStorage</button>
      <div>fromSessionStorage : {{ sessionData2 }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      text: "",
      sessionData: "",
      sessionData2: "",
    };
  },
  computed: {
    data() {
      return this.$store.getters.getData;
    },
  },
  methods: {
    setData() {
      console.log("comp setData");
      this.$store.dispatch("SET_DATA", this.text);
    },
    getData() {
      console.log("comp getData", this.data);
      this.sessionData = this.data;
    },
    getSessionData() {
      console.log(sessionStorage.getItem("data"));
      this.sessionData2 = sessionStorage.getItem("data");
    },
  },
};
</script>
<style scoped>
div {
  margin: 10px;
  text-align: left;
}
</style>
