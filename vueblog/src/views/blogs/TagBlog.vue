<template>
  <div>
      <div style="margin-top: 20px;">
          <!-- 搜索 -->
          <el-input size="small" v-model="input_name" placeholder="请输入标签名称，可回车搜索..." prefix-icon="el-icon-search"
          style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_name"></el-input>
          <el-button size="small" type="primary"  @click="search_name" icon="el-icon-search">搜索</el-button>
          <el-button size="small" type="success"  @click="dialog_add = true" icon="el-icon-plus">新增</el-button>
      </div>
      <div>
          <el-table
          :data="tagData"
          style="width: 70%">
          <el-table-column
            label="编号"
            width="230">
            <template slot-scope="scope"> 
              <span style="margin-left: 10px">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="名称"
            width="230">
            <template slot-scope="scope">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <div style="margin-top: 20px;" v-if="showPage">
        <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pagesize"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
      </div>

       <!-- 对话框 新建标签 -->
       <el-dialog
       title="新建标签"
       :visible.sync="dialog_add"
       width="30%"
       >
       <el-form status-icon  label-width="120px">
         <el-form-item label="标签名称">
           <el-input v-model="tag_add.name" placeholder="请输入要新建的标签名称"
           :rules="{
            required: true, message: '不能为空', trigger: 'blur'
            }"></el-input>
         </el-form-item>
       </el-form>
        <span slot="footer" class="dialog-footer">
           <el-button @click="dialog_add = false">取 消</el-button>
           <el-button type="primary" @click="addNewTag">确 定</el-button>
         </span>
     </el-dialog>

      <!-- 对话框 编辑标签 -->
      <el-dialog
      title="编辑标签"
      :visible.sync="dialog_edit"
      width="30%"
      >
      <el-form status-icon  label-width="120px">
        <el-form-item label="标签名称">
          <el-input v-model="tag_edit.name"
          :rules="{
            required: true, message: '不能为空', trigger: 'blur'
            }"></el-input>
        </el-form-item>
      </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog_edit = false">取 消</el-button>
          <el-button type="primary" @click="updateTag">确 定</el-button>
        </span>
      </el-dialog>

  </div>
</template>

<script>
export default {
  name: 'TagBlog',
  data () {
   return {
      tagData:[],  //标签数据
      currentPage: 1,  //当前页
      total:0, //总记录数
      pagesize:5, //页面大小
      input_name:'', //搜索框值
      dialog_add: false, //添加标签的对话框
      dialog_edit: false, //编辑标签的对话框
      tag_add:{
        name:'',  //标签名称
      },
      tag_edit:{
        id:'',
        name:''
      },
      showPage: true, //是否显示分页
   }
  },
  mounted() {
    this.initTag();
  },
  methods:{
    //初始化分类数据
    initTag(){
      const _this = this
      this.getRequest('/tag/getTagByPage?current=' + this.currentPage + '&size=' + this.pagesize).then(resp=>{
        console.log(resp)
        _this.tagData = resp.obj.records
        _this.total = resp.obj.total
      })
    },
    //编辑分类
    handleEdit(index, row) {
      const _this = this
      console.log(index, row);
      this.dialog_edit = true
      this.getRequest('/tag/getTagById?id=' + row.id).then(resp=>{
          console.log(resp)
          _this.tag_edit = resp.obj
      })
    },
    //更新分类
    updateTag(){
      const _this = this 
      this.putRequest('/tag/updateTag',this.tag_edit).then(resp=>{
        if(resp){
          this.initTag()
          _this.dialog_edit = false
        }
      })
    },
    //删除分类
    handleDelete(index, row) {
      console.log(index, row);
      const _this = this
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/tag/deleteTagById?id=' + row.id).then(resp=>{
            if(resp){
              this.initTag()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
     
    },
    //添加分类
    addNewTag(){
      const _this = this
      this.postRequest('/tag/saveTag',this.tag_add).then(resp=>{
        console.log(resp)
        _this.input_name =''
        _this.dialog_add = false
      })
    },
    //搜索分类
    search_name(){
       const _this = this
       this.getRequest('/tag/getTagByName?name=' + this.input_name).then(resp=>{
         console.log(resp)
         if(_this.input_name == ''){
           _this.initTag();
           _this.showPage = true
         }else{
          _this.tagData = resp.obj
          _this.showPage = false
          _this.input_name = ''
         }
         

       })
    },
    //分页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
      this.initTag()
    }
  }
}
</script>

<style scoped>

</style>