<template>
  <div>
    <div>
        <div class="grid-content content bg-purple blog_bg "> 
          <div  style="height:3px; margin: 5px 0; border:none; border-top:3px solid  #1eb9e0;"></div>

            <el-form  :model="comment" :rules="rules" ref="comment" class="demo-ruleForm">
                <el-form-item prop="content">
                    <div style="width: 95%;margin: auto;padding-top: 1%;">
                        <el-input
                            type="textarea"
                            :rows="7"
                            ref="ipt"
                            :placeholder="placeholder"
                            v-model="comment.content">
                        </el-input> 
                    </div>
                </el-form-item>
                <el-form-item prop="nickname">
                    <el-col :span="6" style="margin-left: 2%;">
                        <el-input placeholder="请输入昵称" prefix-icon="el-icon-user-solid" v-model="comment.nickname"></el-input>
                    </el-col>
                    <el-col :span="10" style="margin-left: 2%;">
                        <el-input placeholder="请输入邮箱,也可不填（有问题我会通过邮箱回复）" prefix-icon="el-icon-s-promotion" v-model="comment.email"></el-input>
                    </el-col>
                    <el-col  :span="2" style="margin-left: 2%;">
                        <el-button type="primary" plain @click="resetForm('comment')">清空</el-button>
                    </el-col>
                    <el-col  :span="3">
                        <el-button type="primary"  @click="submitForm('comment')">发表</el-button>
                    </el-col>                 
                </el-form-item>
            </el-form>

              <!-- 分割线 -->
              <!-- <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div> -->
              <div  style="height:3px; margin: 5px 0; border:none; border-top:3px solid  #1eb9e0;"></div>

              <div style="width: 95%;margin: auto;padding-top: 1%;">
                 评论 <el-tag v-if="user">已登录</el-tag>  <el-tag type="info" @click="dialogFormVisible = true" style="float: right;">管理员评论</el-tag>
                 <div style="background-color: #DCDFE6;height: 1px;margin: 13px 0;"></div>
              </div>

              <!-- 评论区 -->

              <div v-for="(item1,index1) in commentData" :key=index1>
                  <!-- 父评论 -->
                  <div style="width: 95%;margin: auto;padding-top: 1%;">
                    <el-row :gutter="15">
                      <el-col :span="2">
                        <div>
                          <el-image 
                            style="width: 100%; height: 100%"
                            :src="item1.avatar"
                            >
                          </el-image>
                        </div>
                      </el-col>
                      <el-col :span="22">
                        <div style=" width: 100%;">
                          <!-- <span style="font-size: 12px;font-weight:bold;margin: 0px;" >2222</span> -->
                            <p style="font-size: 12px;line-height: 0;font-weight:bold;">{{item1.nickname}}
                              <span v-if="item1.adminComment" style="font-size: 10px;font-weight: normal;"><el-tag size="mini" type="danger">管理员</el-tag></span>
                              <span style="font-size: 10px;font-weight: normal;">  {{item1.createTime}}</span>
                            </p>
                          <p style="font-size: 13px; ">{{item1.content}}</p>
                          <p style="font-size: 10px;line-height: 0;" @click="reply(item1)"><el-link style="font-size: 5px;" :underline="false">回复</el-link></p>
                        </div>
                      </el-col>
                      
                    </el-row> 
                  </div>

                  <!-- 子评论 -->
                  <div  v-for="(item2,index2) in item1.replyComments" :key=index2
                   style="width: 88%;margin: auto;padding-top: 1%;margin-left: 10%;">
                    <el-row :gutter="15">
                      <el-col :span="2">
                        <div>
                          <el-image 
                            style="width: 100%; height: 100%"
                            :src="item2.avatar"
                            >
                          </el-image>
                        </div>
                      </el-col>
                      <el-col :span="22">
                        <div style=" width: 100%;">
                          <!-- <span style="font-size: 12px;font-weight:bold;margin: 0px;" >2222</span> -->
                            <p style="font-size: 12px;line-height: 0;font-weight:bold;">{{item2.nickname}}  
                              <span v-if="item2.adminComment" style="font-size: 10px;font-weight: normal;"><el-tag size="mini" type="danger">管理员</el-tag></span>
                              <span style="font-size: 10px;font-weight: normal;color: #007eff;">  @{{item2.parentNickname}}</span>
                              <span style="font-size: 10px;font-weight: normal;">  {{item2.createTime}}</span>
                            </p>
                            <p style="font-size: 13px; ">{{item2.content}}</p>
                            <p style="font-size: 13px;line-height: 0; " @click="reply(item2)"><el-link style="font-size: 5px;" :underline="false">回复</el-link></p>
                        </div>
                      </el-col>
                      
                    </el-row>
                  </div>
              </div>


        
        </div>

        
    </div>

        <!-- 管理登录评论 -->
        <div >
          <el-dialog  title="管理员登录" :visible.sync="dialogFormVisible" style="width: 50%;margin: auto;" >
            <el-form :model="loginForm" ref="loginForm" >
              <el-form-item prop="username"
                :rules="{ required: true, message: '请输入用户名', trigger: 'blur' }">
                  <el-input type="text" v-model="loginForm.username" auto-complete="off" 
                  placeholder="请输入用户名" @keydown.enter.native="submitLogin('loginForm')"></el-input>
              </el-form-item>
              <el-form-item prop="password"
                :rules="{ required: true, message: '请输入密码', trigger: 'blur' }">
                  <el-input type="password" v-model="loginForm.password" auto-complete="off" 
                  placeholder="请输入密码" @keydown.enter.native="submitLogin('loginForm')"></el-input>
              </el-form-item>
            </el-form>
            
            <div slot="footer" class="dialog-footer">
              <el-button v-if="user"  @click="subLogout">注 销</el-button>
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary"  @click="submitLogin('loginForm')">登 录</el-button>
            </div>
          </el-dialog>
        </div>
        

        
  </div>
</template>

<script>
export default {
  name: 'comment',
  data () {
   return {
     textarea: '',//评论内容
     comment: {
         blogId: '',
         content: '',
         nickname: '',
         email: '',
         parentCommentId: -1,
     },
     placeholder: '请输入评论内容',
     commentData:[],
     rules:{
         content: { required: true, message: '请输入评论内容再提交', trigger: 'blur' },
         nickname: { required: true, message: '请输入昵称再提交', trigger: 'blur' },
     },
     loginForm:{
       username:'',
       password:''
     },
     dialogFormVisible: false,
     showLogout: false,
     user: false,
   }
  },
  props:['blogId'],
  mounted() {
    this.comment.blogId = this.blogId
    if(window.sessionStorage.getItem("user") != null){
       this.user = true
    }else{
      this.user = false
    }
   
    console.log(this.user)
    // console.log( this.comment.blogId )
    this.initComment()
  },
  methods:{
    //聚焦回复评论
    reply(item){
      // console.log(item)
      this.placeholder = '@'+ item.nickname + '  ' + item.content
      this.comment.parentCommentId = item.id
      this.$refs.ipt.focus()
    },

    //初始化评论数据
    initComment(){
      const _this = this
      this.axios.get('comment/comments/' + this.blogId).then(resp=>{
        console.log(resp)
        _this.commentData = resp.data
      })
    },
    //发表评论
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const _this = this
            this.axios.post('/comment/comments',this.comment).then(resp=>{
              console.log(resp)
              if(resp.status == 200){
                this.initComment()
                this.$message({ message: '已发表评论！', type: 'success'  });
                this.placeholder = "请输入评论内容"
              }else{
                this.$message.error('未知错误！');
              }
              this.comment.content = ''
              this.comment.nickname = ''
              this.comment.email = ''
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //重置评论
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.placeholder = "请输入评论内容"
      },
      //管理员登录
      submitLogin(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const _this = this
            this.axios.post('/doLogin?username=' + this.loginForm.username + '&password=' + this.loginForm.password).then(resp=>{
              console.log(resp)
              if(resp.data.status == 200){
                window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
                _this.loginForm.username = ''
                _this.loginForm.password = ''
                _this.$message({
                  message: '登录成功！',
                  type: 'success'
                });
                _this.dialogFormVisible = false
                _this.user = true
              }else{
                _this.$message.error({message: '登录失败！', });           
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //注销登录
      subLogout(){
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.axios.get("/logout")
            window.sessionStorage.removeItem("user")
            this.user = false
            this.$message({
              message: '注销成功！',
              type: 'success'
            });
            this.dialogFormVisible = false  
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消注销'
            });          
        });
      }
    }
  
}
</script>

<style scoped>
    .blog_bg{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 95%;
        margin-top: 5% !important;
        margin-bottom: 5% !important;
        padding-bottom: 5% !important;
        margin: auto;
        min-height: 500px;
    }

</style>