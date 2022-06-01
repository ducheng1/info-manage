<template>
  <div class="home">
    <!-- 按钮组 -->
    <div style="margin: 10px 0" id="homeBtnAdd">
      <el-button type="primary" @click="drawer = true">
        新增
        <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-button type="success" @click="refresh">
        刷新
        <i class="el-icon-refresh-right"></i>
      </el-button>
      <el-button type="danger">
        批量删除
        <i class="el-icon-remove-outline"></i>
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
    <el-drawer title="新增用户" :visible.sync="drawer" :with-header="true">
      <el-form ref="form" :model="form" label-width="80px" class="addUserForm">
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
  </div>
</template>

<script>
export default {
  name: "Manage",
  data() {
    return {
      tableData: [],
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