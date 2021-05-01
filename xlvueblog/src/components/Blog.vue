<template>
  <div class="wrapper" style="height: 100vh;overflow-x: hidden;">
        <!-- 回到顶部 -->
        <el-backtop target=".wrapper">
            <i class="el-icon-caret-top"></i>
         </el-backtop>
   
        <Head></Head>
        <div v-model="blogData" class="grid-content content bg-purple blog_bg "> 

            <div style="padding: 1%;">
                <el-avatar style="vertical-align:middle" :src="blogData.tuser.avatar"></el-avatar>
                <span style="margin-left: 5px;">{{blogData.tuser.username}}</span>
                <i style="margin-left: 20px;" class="el-icon-date"><span style="font-size: 18px;"> {{blogData.updateTime}}</span></i>
                <i style="margin-left: 20px;" class="el-icon-view"><span style="font-size: 18px;"> {{blogData.views}}</span></i>
                <i style="margin-left: 20px;" class="el-icon-chat-line-square"><span style="font-size: 18px;"> {{blogData.commentCount}}</span></i>
                <el-button size="mini" style="float: right;margin: 1%;">
                    <router-link to="/home">返回 <i class="el-icon-d-arrow-right"></i></router-link>
                </el-button>
            </div>
            <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
            <div>
                <img style="width: 96%;height: 400px;margin: 2%;" :src="blogData.firstPicture">
            </div>
            <div style="text-align: center;">
                <h4 >{{blogData.title}}</h4>
                <el-tag>{{blogData.flag}}</el-tag>
                <el-tag type="success" style="margin-left: 10px;">{{blogData.ttype.name}}</el-tag>
                <el-tag v-for="(tag,index) in blogData.tblogTags" :key="index" type="info" style="margin-left: 10px;">{{tag.ttag.name}}</el-tag>
                
            </div>
            <div class="markdown-body" v-html="blogData.content" style="padding: 40px;"></div>
                    <!-- 评论 -->
        <Comment :blogId=id style="padding-bottom: 3px;"></Comment>
        </div>

        <!-- 页脚 -->
        <Foot></Foot>
  </div>
</template>

<script>
import Head from '@/components/Head'
import Foot from '@/components/Foot'
import Comment from '@/components/Comment'
import 'github-markdown-css'
export default {
  name: 'Blog',
  components:{ Head,Foot,Comment },
  data () {
   return {
       blogData:'', //博客数据
   }
  },
  props:['id'], //接收路由传过来的id
  mounted() {
      this.initBlog()
  },
  methods:{
      initBlog(){
          const _this = this
          this.axios.get('/blog/getByBlogId?id=' + this.id).then(resp=>{
              console.log(resp)
              _this.blogData = resp.data.obj

              var MardownIt = require("markdown-it")
              var md = new MardownIt()
              var result = md.render(resp.data.obj.content)
              _this.blogData.content = result
              console.log(_this.blogData)
          })
      }
  }
}
</script>

<style scoped>
    .blog_bg{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 60%;
        margin-top: 5% !important;
        margin-bottom: 5% !important;
        margin: auto;
        min-height: 500px;
    }
</style>