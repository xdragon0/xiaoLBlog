<template>
  <div style="height: 100vh;overflow-x: hidden;" class="wrapper">
     <!-- 回到顶部 -->
      <el-backtop target=".wrapper">
        <i class="el-icon-caret-top"></i>
      </el-backtop>
      
      <!-- 头部导航 -->
      <Head aIndex="1"></Head>

      <!-- 中间内容 -->
      <div style="margin-top: 50px;">
        <el-row :gutter="30">

          <!-- 左侧 -->
          <el-col :span="13" :offset="3">
            <div class="grid-content bg-purple blog_bg_left"> 

              <el-row :gutter="20">
                <el-col :span="12"><div style="margin: 15px 0px 15px 20px;color: blueviolet;">文章</div></el-col>
                <el-col :span="12"><div style="float: right;margin: 10px 20px 0px 0px;">共 <span style="font-size: 28px;color: rgb(6, 154, 240);">{{total}}</span> 篇</div></el-col>
              </el-row>

              <!-- 分割线 -->
              <div class="fenge"></div>
              <!-- <el-divider></el-divider> -->

              <!-- 文章 -->
              <div>
                 <div class="blog_card" v-for="(item,index) in blogData" :key="index" >

                 <router-link :to="'/blog/'+item.id">
                  <el-card shadow="hover" :body-style="{ padding: '15px' }" >
                    <el-row :gutter="20">
                      <el-col :span="17">
                        <div>                        
                           <h4>{{item.title}}</h4>
                           <p style="font-size: 17px;">{{item.description}}</p>
                           <div>
                            <el-avatar style="vertical-align:middle" :src="item.tuser.avatar"></el-avatar>
                            <span style="margin-left: 5px;">{{item.tuser.username}}</span>
                            <i style="margin-left: 20px;" class="el-icon-date"><span style="font-size: 18px;"> {{item.createTime}}</span></i>
                            <i style="margin-left: 20px;" class="el-icon-view"><span style="font-size: 18px;"> {{item.views}}</span></i>
                            <i style="margin-left: 20px;" class="el-icon-chat-line-square"><span style="font-size: 18px;"> {{item.commentCount}}</span></i>
                           </div>

                        </div>
                      </el-col>
                      <el-col :span="7">
                        <div>
                          <el-image 
                            style="width: 200px; height: 115px"
                            :src="item.firstPicture" 
                            >
                          </el-image>
                          <div style="margin-bottom: 10px;">
                            <el-tag style="margin-right: 10px;" ><span> {{item.flag}}</span></el-tag>
                            <el-tag style="margin-right: 10px;" type="success"><span > {{item.ttype.name}}</span></el-tag>
                            <!-- <el-tag style="margin-right: 10px;" type="info" v-for="tag in item.tblogTags"><span> {{tag.ttag.name}}</span></el-tag> -->
                           
                          </div>
                        </div>
                      </el-col>
                    </el-row>
                    
                  </el-card>
                 </router-link>

                  <el-divider></el-divider>      

                 </div>
              </div>

              <div style="margin-left: 2%;padding-bottom: 2%;">
                  <!-- 分页 -->
                  <el-pagination
                  background
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page.sync="currentPage"
                  :page-size="size"
                  layout="total, prev, pager, next"
                  :total="total">
                  </el-pagination>
              </div>


            </div>

           
          </el-col>

          <!-- 右侧 -->
          <el-col :span="6">
            <div class="grid-content bg-purple ">
              <!-- 简介 -->
              <el-card class="box-card">
                 <div style="text-align: center;">
                   <img width="100" height="100" src="https://xdragon0.gitee.io/img/author.png" />
                   <p style="line-height: 0;">XiaoL</p>
                   <p style="font-size: 15px;">星光一点，点亮前方</p>
                  </div>
              </el-card>

              <!-- 分类专栏 -->
              <el-card class="box-card" style="margin-top: 20px;">
                <div slot="header" class="clearfix">
                  <span>分类专栏</span>
                  <router-link to="/blogType">
                    <el-button style="float: right; padding: 3px 0" type="text">more <i class="el-icon-d-arrow-right"></i></el-button>         
                  </router-link>
                </div>
                <div class="text item" v-for="(item,indexTypes) in types" :key="indexTypes" >             
                      <span style="font-size: 14px;"  @click="getType(item.id)">                    
                          <el-card v-if="item.id > 0" shadow="hover" style="margin-bottom: 5px;">
                            <el-link :underline="false">
                               {{item.name }}
                            </el-link>
                            <span style="float: right;"> {{item.countType}}</span>                            
                          </el-card>
                      </span>
                  <!-- <div v-if="index!=4" style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div> -->
                </div>
              </el-card>

              <!-- 热门文章 -->
              <el-card class="box-card" style="margin-top: 20px;">
                <div slot="header" class="clearfix">
                  <span>热门文章</span>
                  <el-button style="float: right; padding: 3px 0" type="text">Top5</el-button>         
                </div>
                <div class="text item" v-for="(item,index) in HotBlog" :key="index" >
                  <router-link :to="'/blog/'+ item.id">
                    <el-link :underline="false">
                      <span style="font-size: 14px;">
                        <el-tag size="small">{{index + 1}}</el-tag> {{item.title}}  
                        <i style="margin-left: 5px;" class="el-icon-view"><span style="font-size: 13px;"> {{item.views}}</span></i>
                      </span>
                    </el-link>
                  </router-link>
                  <div v-if="index!=4" style="background-color: #DCDFE6;height: 1px;margin: 12px 0;"></div>
                </div>
              </el-card>
            </div>
          </el-col>

          <!-- 空白占位 -->
          <el-col :span="3">
            <div class="grid-content bg-purple">
            </div>
          </el-col>
        </el-row>
      </div>

      <!-- <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop> -->

      <Foot style="margin-top: 80px;"></Foot>

  </div>
</template>

<script>
import Head from '@/components/Head'
import Foot from '@/components/Foot'
export default {
  name: 'Home',
  components:{ Head,Foot },
  data () {
   return {
     blogData:[],
     HotBlog:[], //热门文章
     types: [],
     total:0,
     currentPage:1,
     size:5,
     mytype:'',
     myblogTags:[],
     btags:null,
     aIndex: '1'
   }
  },
  mounted() {
    this.initBlog()   //页面加载时调用
    this.initHotBlog()   //页面加载时调用
    this.initAllType()   //页面加载时调用
  },
  methods:{
    // 初始化博客列表数据
    initBlog(){
      const _this = this
      this.axios.get('/blog/vuefindByPage?current=' + this.currentPage + '&size=' + this.size).then(resp=>{
        console.log(resp)
        _this.blogData = resp.data.obj.blogList
        _this.total = resp.data.obj.totals

      })
    },
    //初始化热门文章
    initHotBlog(){
      const _this = this
      this.axios.get('/blog/vuefindHotBlog').then(resp=>{
        console.log(resp)
        _this.HotBlog = resp.data.obj

      })
    },
    //查询所有分类
    initAllType(){
      const _this = this
      this.axios.get('/type/getAllType').then(resp=>{
          console.log(resp)
          _this.types = resp.data.obj
      })
    },
    //获取分类数据
    getType(id){
      console.log(id)
      this.type_id = id
      this.initBlogType()
    },
    //初始化博客数据
    initBlogType(){
      const _this = this
      this.axios.get('/blog/vuefindByPage?current=' + this.currentPage + '&size=' + this.size + '&type_id=' + this.type_id).then(resp=>{
        // console.log(resp)
        _this.blogData = resp.data.obj.blogList
        _this.total = resp.data.obj.blogList.length

      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    //分页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      const _this = this
      this.axios.get('/blog/vuefindByPage?current=' + val + '&size=' + this.size).then(resp=>{
        console.log(resp)
        _this.blogData = resp.data.obj.blogList
        _this.total = resp.data.obj.totals

      })
    }
  }
}
</script>

<style scoped>
  /* 卡片 */
  .blog_card{
    width: 95%;
    margin: 2%;
  }
  /* logo */
  .logo{
    margin-left: 150px;
    font-size: 30px;
    font-family: 华文行楷;
    color: #409eff !important; 
  }
  /* 左侧 */
  .blog_bg_left{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 500px;
  }
  /* 右侧 */
  .blog_bg_right{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 500px;
  }
  /* 分割线 */
  .fenge{ height:3px; border:none; border-top:3px solid  #1eb9e0;}
</style>