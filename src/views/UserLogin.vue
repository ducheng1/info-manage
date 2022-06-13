<template>
    <div id="login-div">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <!-- 增加:rules -->
            <el-form-item label="用户名" prop="username">
                <!-- 增加:prop -->
                <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="form.password"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="onSubmit">登录</el-button>
                <el-button @click="onReset">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            form: {
                username: "",
                password: "",
            },
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
        }
    },
    methods: {
        onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    let params = new FormData()
                    params.append("username", this.form.username)
                    params.append("password", this.form.password)
                    const result = await this.$http.post("/user/login", params)
                    localStorage.setItem("token", result.data)
                    await this.$router.push({path: '/home'})
                } else {
                    this.$message.error("用户名或密码错误");
                    console.log('数据录入有误');
                    return false;
                }
            });
        },
        onReset() {
            this.$refs.form.resetFields();
        },
    },
};
</script>

<style lang="less" scoped>
#login-div {
    position: absolute;
    top: 30%;
    left: 40%;
    padding: 5rem 5rem;
    border-radius: 15px;
    width: 20rem;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}
</style>