<template>
  <div>
    <Head aIndex="5"></Head>
    <!-- 内容 -->
    <div>
        <el-card class="box-card link_message" >
            <h4 style="text-align: center;">友链入账须知</h4>
            <div>
              <h6>注意事项：</h6>
              <p  style="font-size: 13px;">网站能长期正常访问(如访问不了了需发邮箱联系我)</p>
              <p  style="font-size: 13px;">如需交换友链，请先将本站添加到您的网站中，再根据下面的格式，发邮箱联系我~</p>
              <h6>申请格式：</h6>
              <p  style="font-size: 13px;">博客标题：小L星光</p>
              <p  style="font-size: 13px;">博客地址：小L星光</p>
              <p  style="font-size: 13px;">图片地址：小L星光</p>
            </div>
         </el-card>


        <div v-model="BlogLinks" class="grid-content content bg-purple blog_bg "> 
            
            <el-row :gutter="20">
                <el-col :span="12"><div style="margin: 15px 0px 15px 20px;color: blueviolet;">友情链接</div></el-col>
                <el-col :span="12"><div style="float: right;margin: 10px 20px 0px 0px;">共 
                    <span style="font-size: 28px;color: rgb(6, 154, 240);">{{length}}</span> 个</div>
                </el-col>
            </el-row>
                
            <div style="background-color: #DCDFE6;height: 1px;margin: 5px 0;"></div>

            <!-- 分割线 -->
            <!-- <div  style="height:3px; margin: 5px 0; border:none; border-top:3px solid  #1eb9e0;"></div> -->
            <div style="width: 60%;margin: auto; margin-top: 2%;" v-for="(item,indexLink) in BlogLinks" :key="indexLink">
                 <a :href="item.blogAddress" >
                  <el-card shadow="hover" >
                    <el-row :gutter="15">
                        <el-col :span="5">
                        <div>
                            <!-- <img :src="item.pictureAddress" width="50" height="50"> -->
                            <el-image 
                            style="width: 100%; height: 100%"
                            :src="item.pictureAddress"
                            >
                            </el-image>
                        </div>
                        </el-col>
                        <el-col :span="19">
                        <div>
                            <p style="font-size: 15px;line-height: 0; font-weight:bold;">{{item.blogName}}
                            </p>
                            <p style="font-size: 13px;display:block; overflow:hidden; white-space:nowrap; text-overflow:ellipsis; ">
                               {{item.blogAddress}}
                            </p>
                             <el-tag size="mini"><p style="font-size: 10px;line-height: 0; font-weight: normal;">  {{item.createTime}}</p></el-tag>
                            <!-- <p style="font-size: 10px;line-height: 0;"><el-link style="font-size: 5px;" :underline="false">回复</el-link></p> -->
                        </div>
                        </el-col>
                        
                    </el-row> 
               </el-card>
            </a>
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
  name: 'BlogLinks',
  components:{ Head,Foot },
  data () {
   return {
    BlogLinks:'',
    length: '',
   }
  },
  mounted() {
      this.initLinks()
  },
  methods:{
    initLinks(){
        const _this = this
        this.axios.get('/links/getAllLink').then(resp=>{
            console.log(resp)
            _this.BlogLinks = resp.data.obj
            _this.length = resp.data.obj.length
        })
    }
  }
}
</script>

<style scoped>
    .blog_bg{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 50%;
        margin-top: 2% !important;
        margin-bottom: 5% !important;
        margin: auto;
        min-height: 500px;
    }
    .link_message{
        width: 50%;
        margin-top: 2% !important;
        margin: auto;
    }
</style>