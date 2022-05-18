<template>
  <div class="home">
    <!-- <user-login></user-login> -->
    <test></test>
    <manage></manage>
  </div>
</template>

<script>
// @ is an alias to /src
import UserLogin from "@/components/UserLogin.vue";
import Test from "@/components/Test.vue";
import Manage from "@/components/Manage.vue";

export default {
  name: 'HomeView',
  components: {
    UserLogin,
    Test,
    Manage
  }
}
</script>
