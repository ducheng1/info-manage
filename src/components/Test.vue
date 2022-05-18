<template>
  <div id="container">
    <button @click="onClick">测试后端接口</button>
  </div>
</template>

<script>
export default {
  name: "test",
  data() {
    return {};
  },
  methods: {
    onClick: function () {
      console.log("clicked");
      let formData = new FormData();
      formData.append("username", "admin");
      formData.append("password", "admin");
      let {data: result} = this.$http.post("/user/login", formData);
      console.log(result);
    },
  },
};
</script>

<style lang="less" scoped>
</style>