<template>
    <div id="container">
        <bread-crumb page-index="dept" page-name="部门管理"></bread-crumb>
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
        <!-- 树形控件 -->
        <el-tree :data="treeData" :props="props" show-checkbox node-key="id" default-expand-all
                 :expand-on-click-node="false"
                 @check-change="handleCheckChange" v-loading="treeLoading" style="width: 20rem">
            <span slot-scope="{node,data}">
                <span>{{ node.label }}</span>
                <span style="margin-left: 20px">
                    <el-button
                        type="text"
                        @click="edit(data)"
                        style="margin-right: 10px"
                    >编辑 <i class="el-icon-edit-outline"></i
                    ></el-button>
                    <template>
                        <el-popconfirm
                            title="确认删除吗？"
                            @confirm="deleteOne(data)"
                            @cancel="cancel"
                        >
                            <el-button type="text" slot="reference"
                            >删除 <i class="el-icon-remove-outline"></i
                            ></el-button>
                        </el-popconfirm>
                    </template>
                </span>
            </span>
        </el-tree>
        <!-- 编辑抽屉 -->
        <el-drawer title="新增部门" :visible.sync="drawer" :wrapperClosable="false">
            <el-form
                v-loading="drawerLoading"
                ref="form"
                :model="deptData"
                label-width="80px"
            >
                <!-- 隐藏域，用于保存id-->
                <el-input type="hidden" v-model="deptData.id"></el-input>

                <el-form-item label="组织名称">
                    <el-input type="text" v-model="deptData.deptName"></el-input>
                </el-form-item>

                <el-form-item label="parent_id">
                    <el-input type="text" v-model="deptData.parentId"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submit">提交</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </div>
</template>

<script>
import BreadCrumb from "@/components/BreadCrumb";

export default {
    components: {
        BreadCrumb
    },
    data() {
        return {
            treeData: [],
            props: {
                id: Number,
                label: "deptName",
                children: "children"
            },
            treeLoading: false,
            drawer: false,
            deptData: {
                id: null,
                deptName: "",
                parentId: 1
            },
            drawerLoading: false,
        }
    },
    methods: {
        // 刷新
        async refresh() {
            this.treeLoading = true;
            // 调用后端接口获取数据
            let res = await this.$http.get("/dept/tree");
            // console.log(res.data);
            this.treeData = res.data;
            this.treeLoading = false;
        },
        // 选中操作
        handleCheckChange: function (val) {
            console.log(val);
        },
        // 添加
        add() {
            this.drawer = true;
            this.drawerLoading = true;
            this.deptData = {id: null, deptName: "", parentId: 1};
            this.drawerLoading = false;
        },
        // 删除
        async deleteOne(data) {
            // console.log(data);
            await this.$http.delete("/dept/" + data.id);
            await this.refresh();
        },
        // 表单取消提交
        cancel() {
            this.drawer = false;
        },
        // 编辑
        async edit(data) {
            this.drawer = true;
            console.log(data);
            this.deptData = data;
        },
        // 提交
        async submit() {
            let res = await this.$http.post("/dept", this.deptData);
            // console.log(res);
            await this.refresh();
            this.drawer = false;
        },
    },
    mounted() {
        this.refresh();
    }
}
</script>

<style>

</style>