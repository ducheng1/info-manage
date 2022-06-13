<template>
    <div id="container">
        <bread-crumb page-index="role" page-name="角色管理"></bread-crumb>
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
            <el-input v-model="searchText" placeholder="请输入要搜索的内容" suffix-icon="el-icon-search"
                      style="width: 20rem;margin: 0 10px"></el-input>
            <el-button type="primary" @click="search">
                搜索
                <i class="el-icon-search"></i>
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
            <el-table-column prop="id" label="角色编号" width="200">
            </el-table-column>
            <el-table-column prop="roleName" label="角色名称" width="200">
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
        <!-- 分页器 -->
        <el-pagination
            background
            layout="total, prev, pager, next"
            @current-change="changePageNum"
            :current-page="pageNum"
            :page-size="pageSize"
            :total="total"
            style="margin-top: 1rem;text-align: center"
        >
        </el-pagination>
        <!-- 新增抽屉 -->
        <el-drawer title="新增角色" :visible.sync="drawer" :with-header="true" :close-on-click-modal="false"
                   :wrapper-closable="false">
            <el-form ref="form" :model="form" label-width="80px" class="addUserForm" v-loading="drawerLoading">
                <el-form-item label="ID">
                    <el-input disabled v-model="form.id"></el-input>
                </el-form-item>
                <el-form-item label="角色名称">
                    <el-input v-model="form.roleName"></el-input>
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
    name: "SysRole",
    components: {
        BreadCrumb
    },
    data() {
        return {
            tableData: [],
            tableLoading: false,
            form: {
                id: null,
                roleName: ""
            },
            drawer: false,
            drawerLoading: false,
            pageNum: 1, // 当前页
            pageSize: 10, // 每页数据个数
            total: 0, // 数据总数
            searchText: "", // 搜索内容
        }
    },
    methods: {
        // 分页器改变页码
        changePageNum: function (val) {
            // console.log(val);
            this.pageNum = val;
            this.refresh();
        },
        // 刷新
        async refresh() {
            this.tableLoading = true;
            this.searchText = "";
            // 调用后端接口获取数据
            await this.$http.get("/role").then((res) => {
                // console.log(res);
                this.total = res.data.length;
                // 数据绑定到表格
                this.tableData = res.data.slice(10 * (this.pageNum - 1), 10 * this.pageNum);
            })
            this.tableLoading = false;
        },
        // 添加记录
        async add() {
            this.form.id = null;
            this.form.roleName = "";
            this.drawer = true;
            this.drawerLoading = true;
            this.drawerLoading = false;
        },
        // 表单取消提交
        cancel() {
            this.drawer = false;
            this.roleName = "";
        },
        // 表单提交
        async submit() {
            let res = await this.$http.post("/role", this.form);
            // console.log(res);
            await this.refresh();
            this.drawer = false;
        },
        // 删除
        async deleteOne(row) {
            await this.$http.delete("/role/" + row.id);
            await this.refresh();
        },
        // 编辑
        async edit(row) {
            this.drawer = true;
            this.drawerLoading = true;
            let res = await this.$http.get("/role/" + row.id);
            this.form = res.data;
            this.drawerLoading = false;
            await this.refresh();
        },
        // 搜索
        async search() {
            this.tableLoading = true;
            let res = await this.$http.get("/role/search/" + this.searchText);
            console.log(res);
            this.tableData = res.data;
            this.total = res.data.length;
            this.tableLoading = false;
        },
    },
    mounted() {
        this.refresh();
    }
}
</script>

<style scoped>
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