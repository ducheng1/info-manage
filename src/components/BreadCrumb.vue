<template>
    <div>
        <el-breadcrumb separator="/" style="padding-bottom: 10px">
            <el-breadcrumb-item :to="{ path: '/user' }"
            >首页
            </el-breadcrumb-item
            >
            <el-breadcrumb-item><a :href="'/'+pageIndex">{{ pageName }}</a></el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
export default {
    name: "BreadCrumb",
    props: {
        pageIndex: String,
        pageName: String
    }
}
</script>

<style scoped>

</style>