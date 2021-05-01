<template>
  <div>
          <!--底部-->
          <el-footer style="height: auto;color: #fff;background-color: #74787a;">
            <!-- opacity:0.6; -->
            <div style="text-align: center;">
              <el-row type="flex" class="row-bg" >
                <el-col :span="5" :offset="3">
                  <br>
                  <h4>联系我</h4>
                  <div style="font-size: 15px;line-height: 10px;">
                    <p>Email：1556187290@qq.com</p>
                    <p> QQ：1556187290</p>
                      
                  </div>
                </el-col>
                <!-- <el-divider direction="vertical"></el-divider> -->
                <el-col :span="4">
                  <div style="font-size: 15px;">
                    <h4>关注公众号</h4>
                    <img style="width: 100px; height: 100px" src="../assets/image/code.jpg">
                                        
                  </div>
                </el-col>
                <el-col :span="4">
                  <div style="font-size: 15px;">
                    <h4>交流群</h4>
                    <img style="width: 100px; height: 100px" src="../assets/image/code.jpg">
                      
                  </div>
                </el-col>
                <el-col :span="5">
                  <h4>站点信息</h4>
                  <div style="font-size: 15px;line-height: 10px;">
                    <p>文章总数：{{blogCount}} 篇</p>
                    <p>访问总数：{{viewCount}} 次</p>
                    <p>评论总数：{{commentCount}} 条</p>
                    <p>留言总数：{{messageCount}} 条</p>
                    
                      
                  </div>
                </el-col>
              </el-row>

            </div>
            <!-- <el-divider></el-divider> -->
            <!-- <div style="text-align: center;font-style: 15px;color: #df7137;padding-bottom: 5px;">
               小L星光站点已运营：0年10天5时35分40秒
            </div> -->

          </el-footer>
  </div>
</template>

<script>
export default {
  name: 'Foot',
  data () {
   return {
    blogCount: '',
    viewCount: '',
    commentCount: '',
    messageCount: '',
   }
  },
  mounted() {
    this.initData()
  },
  methods:{
    initData(){
      const _this = this
      this.axios.get('/blog/countBlog').then(resp=>{
        // console.log(resp)
        _this.blogCount = resp.data.obj.blogCount
        _this.viewCount = resp.data.obj.viewCount
        _this.commentCount = resp.data.obj.commentCount
        _this.messageCount = resp.data.obj.messageCount
      })
    }
  }
}
</script>

<style scoped>

</style>