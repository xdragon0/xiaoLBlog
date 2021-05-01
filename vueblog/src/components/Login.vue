<template>
    <div>
        <!--element ui的表单验证规则配置 用 :rules="rules"这是简写的，本来是v-model:rules="rules"-->
        <!--:model 表单里面的数据对象-->
        <!-- ref="loginForm" 绑定校验表单 -->
        <el-form 
            v-loading="loading"
            element-loading-text="正在登录..."
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :rules="rules" :model="loginForm" class="loginContainer" ref="loginForm">
            <!-- <h3 class="loginTitle">小L星光站后台登录</h3> -->
            <h1 class="loginTitle">小L星光站后台登录</h1>
            <!--表单里面的每一项叫做<el-form-item></el-form-item,要加个prop属性，本来是不用加的，但是我们这个使用了字段校验，这种情况家就一定要加prop属性-->
            <el-form-item prop="username">
                <!--auto-complete:是否自动补全-->
                <el-input type="text" v-model="loginForm.username" auto-complete="off" 
                placeholder="请输入用户名" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginForm.password" auto-complete="off" 
                placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-checkbox class="loginRember" v-model="checked" >记住我</el-checkbox>
            <el-button type="primary" style="width: 100%" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>
 
<script>
    export default {
        name: "Login",
        data(){
            return{
                loginForm:{
                  username:"admin",
                  password:"123"
                },
                checked:true,
                rules:{
                    //required:true:用户名必填  如果没填就弹出““””“"请输入用户名",trigger:blur 触发的方式是blur
                    username:[{required:true,message:"用户名不能为空",trigger:"blur"}],
                    password:[{required:true,message:"密码不能为空",trigger:"blur"}],
                },
                loading:false  //加载样式
            }
        },
        methods:{
            submitLogin(){
                this.$refs.loginForm.validate((validate) =>{  // Element自带的校验
                    if(validate){
                        //显示加载样式
                        this.loading = true
                        //这是在api.js封装的请求
                        this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                             //隐藏加载样式
                             this.loading = false
                            if(resp){
                                //resp：从服务端拿到的数据  用户的数据要保存到哪里？ 保存在sessionStorage  关闭浏览器就没了
                                window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
                                //页面跳转  replace：替换  用push的话，可以使用后退按钮回到登录页，用replace不可以回到登录页
                                this.$router.replace('/admin/home')
                            }
                        })
                    }else {
                        this.$message.error("请输入所有字段");
                        return false;
                    }
                })
            }
        }
 
    }
</script>
 
<style scoped>
    .loginContainer{
        border-radius: 15px;
        background-clip:padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    .loginTitle{
        margin: 15px auto 20px auto;
        text-align: center;
        color: #505458;
    }
    .loginRember{
        text-align: left;
        margin: 0 0 25px 0;
    }
</style>