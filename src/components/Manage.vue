<template>
  <div class="home" style="min-height: 100%">
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
              <el-menu-item index="1-1">用户管理</el-menu-item>
              <el-menu-item index="1-2">组织管理</el-menu-item>
              <el-menu-item index="1-3">角色管理</el-menu-item>
              <el-menu-item index="1-4">菜单管理</el-menu-item>
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
              <el-dropdown-item>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
          <el-breadcrumb separator="/" style="padding-bottom: 10px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">用户管理</a></el-breadcrumb-item>
          </el-breadcrumb>
          <!-- 搜索框 -->
          <div style="margin: 10px 0" id="homeSearch">
            <el-input
              suffix-icon="el-icon-search"
              style="width: 200px"
              placeholder="请输入名称"
            ></el-input>
            <el-input
              suffix-icon="el-icon-message"
              style="width: 200px; margin-left: 5px"
              placeholder="请输入邮箱"
            ></el-input>
            <el-input
              suffix-icon="el-icon-place"
              style="width: 200px; margin-left: 5px"
              placeholder="请输入地址"
            ></el-input>
            <el-button style="margin-left: 5px" type="primary">搜索</el-button>
          </div>
          <!-- 按钮组 -->
          <div style="margin: 10px 0" id="homeBtnAdd">
            <el-button type="primary">
              新增
              <i class="el-icon-circle-plus-outline"></i>
            </el-button>
            <el-button type="danger">
              批量删除
              <i class="el-icon-remove-outline"></i>
            </el-button>
            <el-button type="primary">
              导入
              <i class="el-icon-upload2"></i>
            </el-button>
            <el-button type="primary">
              导出
              <i class="el-icon-download"></i>
            </el-button>
          </div>
          <!-- 内容 -->
          <el-table
            :data="tableData"
            border="true"
            stripe="true"
            style="border-radius: 10px"
            id="homeContent"
          >
            <el-table-column prop="date" label="日期" width="140">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址"> </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="success"
                  >编辑 <i class="el-icon-edit-outline"></i
                ></el-button>
                <el-button type="danger"
                  >删除 <i class="el-icon-remove-outline"></i
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页栏 -->
          <div style="padding: 10px 0" id="homePagi">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 15, 20]"
              :page-size="10"
              layout="total, sizes, prev, pager, next, jumper"
              :total="200"
            >
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Manage",
  data() {
    const item = {
      date: "2016-05-02",
      name: "王小虎",
      address: "上海市普陀区金沙江路 1518 弄",
    };
    return {
      tableData: Array(10).fill(item),
      isCollapse: false,
      sideWidth: 250,
      collapseBtnClass: "el-icon-s-fold",
      logoSrc: require("/public/logo.png"),
      collapseLogo: "",
      currentPage: 1,
    };
  },
  methods: {
    // 点击收缩
    collapse: function () {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64;
        this.collapseLogo = "collapseLogo";
      } else {
        this.sideWidth = 250;
        this.collapseLogo = "";
      }
    },
    handleSizeChange: function () {
      console.log(this.currentPage);
    },
    handleCurrentChange: function () {
      console.log(this.currentPage);
    },
  },
};
</script>

<style scoped>
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