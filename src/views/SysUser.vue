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
              <el-menu-item index="/dept">组织管理</el-menu-item>
              <el-menu-item index="/role">角色管理</el-menu-item>
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
              <el-dropdown-item>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
          <el-breadcrumb separator="/" style="padding-bottom: 10px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">用户管理</a></el-breadcrumb-item>
          </el-breadcrumb>
          <!-- 按钮组 -->
          <div style="margin: 10px 0" id="homeBtnAdd">
            <el-button type="primary" @click="drawer = true">
              新增
              <i class="el-icon-circle-plus-outline"></i>
            </el-button>
            <el-button type="success" @click="refresh">
              刷新
              <!-- <i class="el-icon-circle-plus-outline"></i> -->
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
            border
            stripe
            style="border-radius: 10px"
            id="homeContent"
          >
            <el-table-column prop="id" label="编号" width="140" fixed>
            </el-table-column>
            <el-table-column prop="username" label="用户名称" width="150">
            </el-table-column>
            <el-table-column prop="password" label="口令" width="150">
            </el-table-column>
            <el-table-column prop="roleId" label="角色编号" width="150">
            </el-table-column>
            <el-table-column prop="deptId" label="部门编号" width="150">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  type="success"
                  @click="edit(scope.row)"
                  style="margin-right: 10px"
                  >编辑 <i class="el-icon-edit-outline"></i
                ></el-button>
                <template>
                  <el-popconfirm
                    title="确认删除吗？"
                    @confirm="deleteOne(scope.row)"
                    @cancel="cancel"
                  >
                    <el-button type="danger" slot="reference"
                      >删除 <i class="el-icon-remove-outline"></i
                    ></el-button>
                  </el-popconfirm>
                </template>
              </template>
            </el-table-column>
          </el-table>
          <!-- 新增抽屉 -->
          <el-drawer
            title="新增用户"
            :visible.sync="drawer"
            :with-header="true"
          >
            <el-form
              ref="form"
              :model="form"
              label-width="80px"
              class="addUserForm"
            >
              <el-form-item label="用户名">
                <el-input v-model="form.username"></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="form.password"></el-input>
              </el-form-item>
              <el-form-item label="角色编号">
                <el-input v-model="form.roleId"></el-input>
              </el-form-item>
              <el-form-item label="部门编号">
                <el-input v-model="form.deptId"></el-input>
              </el-form-item>
              <div class="buttonGroup">
                <el-button type="primary" @click="submit">确认</el-button>
                <el-button @click="cancel">取消</el-button>
              </div>
            </el-form>
          </el-drawer>
          <!-- 分页栏 -->
          <div style="padding: 10px 0" id="homePage">
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
    return {
      tableData: [],
      isCollapse: false,
      sideWidth: 250,
      collapseBtnClass: "el-icon-s-fold",
      logoSrc: require("/public/logo.png"),
      collapseLogo: "",
      currentPage: 1,
      drawer: false,
      form: {
        username: "",
        password: "",
        roleId: "",
        deptId: "",
      },
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
    // 刷新
    async refresh() {
      try {
        let res = await this.$http.get("/user");
        console.log(res);
        this.tableData = res.data.data;
      } catch (err) {
        console.log(err);
        this.$message.error(
          "错误码：" +
            err.response.status +
            ", 错误信息：" +
            err.response.data.msg
        );
      }
    },
    // 表单取消提交
    cancel() {
      this.drawer = false;
      this.form = { username: "", password: "", roleId: "", deptId: "" };
    },
    // 表单提交
    async submit() {
      try {
        let res = await this.$http.post("/user", this.form);
        console.log(res);
        this.refresh();
        this.drawer = false;
      } catch (err) {
        console.log(err);
        this.$message.error(
          "错误码：" +
            err.response.status +
            ", 错误信息：" +
            err.response.data.msg
        );
      }
    },
    // 删除
    async deleteOne(row) {
      try {
        await this.$http.delete("/user/" + row.id);
        this.refresh();
      } catch (err) {
        console.log(err);
      }
    },
    // 编辑
    async edit(row) {
      try {
        let res = await this.$http.get("/user/" + row.id);
        this.form = res.data.data;
        this.drawer = true;
      } catch (err) {
        console.log(err);
        this.$message.error(
          "错误码：" +
            err.response.status +
            ", 错误信息：" +
            err.response.data.msg
        );
      }
    },
  },
  mounted() {
    this.refresh();
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

.addUserForm {
  padding: 2rem;
}

.buttonGroup {
  width: 21rem;
}

.buttonGroup > .el-button {
  width: 10rem;
}
</style>