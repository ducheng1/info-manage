<template>
    <div class="home">
        <bread-crumb page-index="user" page-name="用户管理"></bread-crumb>
        <!-- 按钮组 -->
        <div style="margin: 10px 0" id="homeBtnAdd">
            <el-button type="primary" @click="add">
                新增
                <i class="el-icon-circle-plus-outline"></i>
            </el-button>
            <el-button type="success" @click="refresh">
                刷新
                <i class="el-icon-refresh-right"></i>
            </el-button>
        </div>
        <!-- 内容 -->
        <el-table
            :data="tableData"
            border
            stripe
            style="border-radius: 10px"
            id="homeContent"
            v-loading="tableLoading"
        >
            <el-table-column prop="id" label="编号" width="140" fixed>
            </el-table-column>
            <el-table-column prop="username" label="用户名称" width="200">
            </el-table-column>
            <el-table-column prop="password" label="密码" width="200">
            </el-table-column>
            <el-table-column prop="roleName" label="角色" width="200">
            </el-table-column>
            <el-table-column prop="deptName" label="组织" width="200">
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
        <el-drawer title="新增用户" :visible.sync="drawer" :with-header="true" :close-on-click-modal="false"
                   :wrapper-closable="false">
            <el-form ref="form" :model="form" label-width="80px" class="addUserForm" v-loading="drawerLoading"
                     :rules="rules">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select v-model="form.roleId" placeholder="请选择">
                        <el-option v-for="item in roleOptions" :key="item.id" :label="item.roleName"
                                   :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="部门">
                    <el-cascader :options="deptOptions" v-model="form.deptId"
                                 :props="{ checkStrictly: true, emitPath:false, label: 'deptName', value: 'id' }"
                                 clearable></el-cascader>
                </el-form-item>
                <div class="buttonGroup">
                    <el-button type="primary" @click="submit">确认</el-button>
                    <el-button @click="cancel">取消</el-button>
                </div>
            </el-form>
        </el-drawer>
    </div>
</template>

<script>
import BreadCrumb from "@/components/BreadCrumb";

export default {
    name: "Manage",
    components: {
        BreadCrumb
    },
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
            currentPage: 1,
            deptOptions: [],
            roleOptions: [],
            rules: {
                username: [
                    {required: true, message: "请输入用户名", trigger: "blur"},
                    {min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur"},
                ],
                password: [
                    {required: true, message: "请输入密码", trigger: "blur"},
                    {min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur"},
                ],
            },
            tableLoading: false,
            drawerLoading: false
        };
    },
    methods: {
        handleSizeChange: function () {
            console.log(this.currentPage);
        },
        handleCurrentChange: function () {
            console.log(this.currentPage);
        },
        // 添加记录
        async add() {
            this.form.id = null;
            this.form.username = "";
            this.form.password = "";
            this.form.deptId = 0;
            this.form.roleId = 0;
            this.drawer = true;
            this.drawerLoading = true;
            await this.initDrawer();
            this.drawerLoading = false;
        },
        // 抽屉初始化
        async initDrawer() {
            let roles = this.$http.get('/role').then(result => {
                this.roleOptions = result.data
                this.form.roleId = result.data.length > 0 ? result.data[0].id : 0
            })
            let depts = this.$http.get('/dept/tree').then(result => {
                this.deptOptions = result.data
            })
            await Promise.all([roles, depts])
        },
        // 刷新
        async refresh() {
            this.tableLoading = true;
            let res = await this.$http.get("/user");
            // console.log(res);
            this.tableData = res.data;
            this.tableLoading = false;
        },
        // 表单取消提交
        cancel() {
            this.drawer = false;
            this.form = {username: "", password: "", roleId: "", deptId: ""};
        },
        // 表单提交
        async submit() {
            await this.$refs.form.validate(async (valid) => {
                if (valid) {
                    await this.$http.post("/user", this.form);
                    // console.log(res);
                    await this.refresh();
                    this.drawer = false;
                } else {
                    return;
                }
            });
        },
        // 删除
        async deleteOne(row) {
            await this.$http.delete("/user/" + row.id);
            await this.refresh();
        },
        // 编辑
        async edit(row) {
            this.drawer = true;
            this.drawerLoading = true;
            let res = await this.$http.get("/user/" + row.id);
            this.form = res.data;
            await this.initDrawer();
            this.drawerLoading = false;
            await this.refresh();
        },
    },
    mounted() {
        this.refresh();
    },
};
</script>

<style scoped>
.home {
    max-height: 90%;
}

.addUserForm {
    padding: 2rem;
}

.buttonGroup {
    width: 100%;
    margin-left: 25%;
}

.buttonGroup > .el-button {
    width: 30%;
    height: 2.5rem;
    font-size: 1rem;
}
</style>