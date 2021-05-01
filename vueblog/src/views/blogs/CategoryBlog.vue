<template>
  <div>
      <div style="margin-top: 20px;">
          <!-- 搜索 -->
          <el-input size="small" v-model="input_name" placeholder="请输入分类名称，可回车搜索..." prefix-icon="el-icon-search"
          style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_name"></el-input>
          <el-button size="small" type="primary"  @click="search_name" icon="el-icon-search">搜索</el-button>
          <el-button size="small" type="success"  @click="dialog_add = true" icon="el-icon-plus">新增</el-button>
      </div>
      <div>
          <el-table
          :data="typeData"
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
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pagesize"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
      </div>

       <!-- 对话框 新建分类专栏 -->
       <el-dialog
       title="新建分类专栏"
       :visible.sync="dialog_add"
       width="30%"
       >
       <el-form status-icon  label-width="120px">
         <el-form-item label="分类专栏名">
           <el-input v-model="type_add.name" placeholder="请输入要新建的分类专栏名"
           :rules="{
            required: true, message: '文章类型不能为空', trigger: 'blur'
            }"></el-input>
         </el-form-item>
       </el-form>
        <span slot="footer" class="dialog-footer">
           <el-button @click="dialog_add = false">取 消</el-button>
           <el-button type="primary" @click="addNewType">确 定</el-button>
         </span>
     </el-dialog>

      <!-- 对话框 编辑分类专栏 -->
      <el-dialog
      title="编辑分类专栏"
      :visible.sync="dialog_edit"
      width="30%"
      >
      <el-form status-icon  label-width="120px">
        <el-form-item label="分类专栏名">
          <el-input v-model="type_edit.name"
          :rules="{
            required: true, message: '文章类型不能为空', trigger: 'blur'
            }"></el-input>
        </el-form-item>
      </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog_edit = false">取 消</el-button>
          <el-button type="primary" @click="updateType">确 定</el-button>
        </span>
      </el-dialog>

  </div>
</template>

<script>
export default {
  name: 'CategoryBlog',
  data () {
   return {
      typeData:[],  //分类数据
      currentPage: 1,  //当前页
      total:0, //总记录数
      pagesize:5, //页面大小
      input_name:'', //搜索框值
      dialog_add: false, //添加分类的对话框
      dialog_edit: false, //编辑分类的对话框
      type_add:{
        name:'',  //分类名称
      },
      type_edit:{
        id:'',
        name:''
      },
      showPage: true, //是否显示分页
   }
  },
  mounted() {
    this.initType();
  },
  methods:{
    //初始化分类数据
    initType(){
      const _this = this
      this.getRequest('/type/getTypeByPage?current=' + this.currentPage + '&size=' + this.pagesize).then(resp=>{
        console.log(resp)
        _this.typeData = resp.obj.records
        _this.total = resp.obj.total
      })
    },
    //编辑分类
    handleEdit(index, row) {
      const _this = this
      console.log(index, row);
      this.dialog_edit = true
      this.getRequest('/type/getTypeById?id=' + row.id).then(resp=>{
          console.log(resp)
          _this.type_edit = resp.obj
      })
    },
    //更新分类
    updateType(){
      const _this = this 
      this.putRequest('/type/updateType',this.type_edit).then(resp=>{
        if(resp){
          this.initType()
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
          this.deleteRequest('/type/deleteTypeById?id=' + row.id).then(resp=>{
            if(resp){
              this.initType()
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
    addNewType(){
      const _this = this
      this.postRequest('/type/saveType',this.type_add).then(resp=>{
        console.log(resp)
        _this.input_name =''
        _this.dialog_add = false
      })
    },
    //搜索分类
    search_name(){
       const _this = this
       this.getRequest('/type/getTypeByName?name=' + this.input_name).then(resp=>{
         console.log(resp)
         if(_this.input_name == ''){
           _this.initType();
           _this.showPage = true
         }else{
          _this.typeData = resp.obj
          _this.showPage = false
          _this.input_name = ''
         }
         

       })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
      this.initType()
    }
  }
}
</script>

<style scoped>

</style>