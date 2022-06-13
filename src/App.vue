<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<style lang="less">
#app {
    min-height: 100vh;
}

body {
    margin: 0;
}
</style>

<script>
import UserLogin from "@/views/UserLogin";
import Home from "@/views/Home";

export default {
    name: "HomeView",
    components: {
        Home,
        UserLogin
    },
};
</script>
