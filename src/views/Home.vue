<template>
    <div class="home">
        <el-container style="min-height: 100vh">
            <!-- 左侧菜单 -->
            <el-aside
                :width="sideWidth + 'px'"
                style="
          background-color: rgb(238, 241, 246);
          box-shadow: 2px 0 6px rgb(0, 21, 41, 35%);
        "
            >
                <el-menu
                    :default-openeds="['1', '3']"
                    style="min-height: 100%; overflow-x: hidden"
                    :collapse="isCollapse"
                    background-color="rgb(48,65,86)"
                    text-color="#fff"
                    active-text-color="#ffd04b"
                    :collapse-transition="false"
                    class="el-menu-vertical-demo"
                    router
                >
                    <h3
                        style="
              text-align: center;
              color: white;
              height: 60px;
              line-height: 60px;
            "
                    >
                        <img
                            :src="logoSrc"
                            style="
                width: 30px;
                position: relative;
                top: 10px;
                margin-right: 5px;
              "
                            id="logoCont"
                            :class="collapseLogo"
                        />
                        <span v-show="!isCollapse">后台管理系统</span>
                    </h3>
                    <el-submenu index="1">
                        <template slot="title">
                            <i class="el-icon-message"></i>
                            <span slot="title">系统管理</span>
                        </template>
                        <el-menu-item-group>
                            <template slot="title">管理项</template>
                            <el-menu-item index="/user">用户管理</el-menu-item>
                            <el-menu-item index="/role">角色管理</el-menu-item>
                            <el-menu-item index="/dept">组织管理</el-menu-item>
                            <el-menu-item index="/menu">菜单管理</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </el-aside>

            <!-- 右侧内容 -->
            <el-container>
                <el-header
                    style="
            font-size: 12px;
            border-bottom: 1px solid #ccc;
            line-height: 60px;
            display: flex;
          "
                >
                    <div style="flex: 1; font-size: 18px">
            <span
                :class="collapseBtnClass"
                style="cursor: pointer"
                @click="collapse"
            ></span>
                    </div>
                    <el-dropdown style="width: 70px; cursor: pointer">
                        <span>管理员</span>
                        <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>个人信息</el-dropdown-item>
                            <el-dropdown-item divided @click="userLogOut">注销</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-header>

                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import SysUser from "@/views/SysUser.vue";

export default {
    name: "Home",
    components: {
        SysUser
    },
    data() {
        return {
            isCollapse: false,
            sideWidth: 250,
            collapseBtnClass: "el-icon-s-fold",
            logoSrc: require("/public/logo.png"),
            collapseLogo: "",
            pageName: "用户管理",
            pageIndex: "user"
        };
    },
    methods: {
        // 点击收缩
        collapse: function () {
            this.isCollapse = !this.isCollapse;
            if (this.isCollapse) {
                this.sideWidth = 64;
                this.collapseLogo = "collapseLogo";
                this.collapseBtnClass = "el-icon-s-unfold";
            } else {
                this.sideWidth = 250;
                this.collapseLogo = "";
                this.collapseBtnClass = "el-icon-s-fold";
            }
        },
        userLogOut: function () {

        }
    },
}
</script>

<style lang="less" scoped>
.home {
    height: 100%;
}

.el-header {
    background-color: #b3c0d1;
    color: #333;
    line-height: 60px;
}

.el-aside {
    color: #333 !important;
}

.collapseLogo {
    margin-left: 5px;
}
</style>