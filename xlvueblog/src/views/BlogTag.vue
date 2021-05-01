<template>
    <div style="height: 100vh;overflow-x: hidden;" class="wrapper">
         <!-- 回到顶部 -->
        <el-backtop target=".wrapper">
            <i class="el-icon-caret-top"></i>
        </el-backtop>
        
        <!-- 头部菜单 -->
        <Head aIndex="3"></Head>
          
        <!-- 内容 -->
          <div>
              <div v-model="blogData" class="grid-content content bg-purple blog_bg "> 
                  
                  <el-row :gutter="20">
                      <el-col :span="12"><div style="margin: 15px 0px 15px 20px;color: blueviolet;">标签</div></el-col>
                      <el-col :span="12"><div style="float: right;margin: 10px 20px 0px 0px;">共 
                          <span style="font-size: 28px;color: rgb(6, 154, 240);">{{tags.length}}</span> 个</div></el-col>
                    </el-row>
                    
                    <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>
           
                      <el-button v-for="(tag,index) in tags" style="margin: 1% 0 1% 2%;" type="info" plain
                      @click="getType(tag.id)">{{tag.name}}  {{tag.countTag}}</el-button>
                      
                    
  
                   <!-- 分割线 -->
                   <div  style="height:3px; margin: 5px 0; border:none; border-top:3px solid  #1eb9e0;"></div>
  
                <!-- 文章 -->
                <div>
                  <div style=" width: 95%; margin: 2%;" v-for="(item,index) in blogData" :key="index" >
  
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
                             style="width: 100%; height: 100%"
                             :src="item.firstPicture" 
                             >
                           </el-image>
                           <div style="margin-bottom: 10px;">
                             <el-tag style="margin-right: 10px;" ><span> {{item.flag}}</span></el-tag>
                             <el-tag style="margin-right: 10px;" type="success"><span > {{item.ttype.name}}</span></el-tag>
                             <el-tag style="margin-right: 10px;" type="info" v-for="tag in item.tblogTags"><span> {{tag.ttag.name}}</span></el-tag>
                            
                           </div>
                         </div>
                       </el-col>
                     </el-row>
                     
                   </el-card>
                  </router-link>
  
                   <el-divider></el-divider>      
  
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
  
  
              
              </div>
          </div>
  
        <Foot></Foot>
    </div>
  </template>
  
  <script>
  import Head from '@/components/Head'
  import Foot from '@/components/Foot'
  export default {
    name: 'BlogTag',
    components:{ Head,Foot },
    data () {
     return {
         tags:[],
         tag_id:'',
         blogData:[], 
         total:1,
         currentPage:1,
         size:5,
     }
    },
    mounted() {
        this.getAllTag()
        this.initBlog()
    },
    methods:{
      //查询所有分类
      getAllTag(){
          const _this = this
          this.axios.get('/tag/getAllTag').then(resp=>{
              console.log(resp)
              _this.tags = resp.data.obj
          })
      },
      //获取分类数据
      getType(id){
          console.log(id)
          this.tag_id = id
          this.initBlog()
  
      },
      //初始化博客数据
      initBlog(){
        const _this = this
        this.axios.get('/blog/vuefindByPage?current=' + this.currentPage + '&size=' + this.size + '&tags_id=' + this.tag_id).then(resp=>{
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
        this.currentPage = val
        this.initBlog()
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