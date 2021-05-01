<template>
  <div>
      <div style="margin-top: 20px;">
          <!-- 搜索 -->
          <el-input size="small" v-model="input_name" placeholder="请输入友链名称，可回车搜索..." prefix-icon="el-icon-search"
          style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_name"></el-input>
          <el-button size="small" type="primary"  @click="search_name" icon="el-icon-search">搜索</el-button>
          <el-button size="small" type="success"  @click="dialog_add = true" icon="el-icon-plus">新增</el-button>
      </div>
      <div>
          <el-table
          :data="linkData"
          >
          <el-table-column
            label="编号"
            width="200">
            <template slot-scope="scope"> 
              <span style="margin-left: 10px">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="友链图片"
            width="90">
            <template slot-scope="scope">
              <img :src="scope.row.pictureAddress" width="50" height="50" />
            </template>
          </el-table-column>
          <el-table-column
            label="友链名称"
            width="200">
            <template slot-scope="scope">
              <el-tag size="medium">{{ scope.row.blogName }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="友链地址"
            >
            <template slot-scope="scope">
              <el-tag size="medium">{{ scope.row.blogAddress }}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" fixed="right">
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

       <!-- 对话框 新建友链 -->
       <el-dialog
       title="新建友链"
       :visible.sync="dialog_add"
       width="30%"
       >
       <el-form status-icon  label-width="100px" :model="link_add" ref="link_add">
        <el-form-item label="友链图片" prop="pictureAddress"
          :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
          <el-input v-model="link_add.pictureAddress" placeholder="请输入友链图片"></el-input>
      
        </el-form-item>
         <el-form-item label="友链名称" prop="blogName"
          :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
           <el-input v-model="link_add.blogName" placeholder="请输入友链名称"></el-input>
         </el-form-item>
         <el-form-item label="友链地址" prop="blogAddress"
           :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
            <el-input v-model="link_add.blogAddress" placeholder="请输入友链名称"></el-input>
            
          </el-form-item>
       </el-form>
        <span slot="footer" class="dialog-footer">
           <el-button @click="dialog_add = false">取 消</el-button>
           <el-button type="primary" @click="addLink('link_add')">确 定</el-button>
         </span>
     </el-dialog>

      <!-- 对话框 编辑友链 -->
      <el-dialog
      title="编辑友链"
      :visible.sync="dialog_edit"
      width="30%"
      >
      <el-form status-icon  label-width="100px" :model="link_edit" ref="link_edit">
        <el-form-item label="友链图片" prop="pictureAddress"
          :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
          <el-input v-model="link_edit.pictureAddress" placeholder="请输入友链图片"></el-input>
      
        </el-form-item>
         <el-form-item label="友链名称" prop="blogName"
          :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
           <el-input v-model="link_edit.blogName" placeholder="请输入友链名称"></el-input>
         </el-form-item>
         <el-form-item label="友链地址" prop="blogAddress"
           :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
            <el-input v-model="link_edit.blogAddress" placeholder="请输入友链名称"></el-input>
            
          </el-form-item>
       </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog_edit = false">取 消</el-button>
          <el-button type="primary" @click="editLink('link_edit')">确 定</el-button>
        </span>
      </el-dialog>

  </div>
</template>

<script>
export default {
  name: 'LinksBlog',
  data () {
   return {
      linkData:[],  //友链数据
      currentPage: 1,  //当前页
      total:0, //总记录数
      pagesize:5, //页面大小
      input_name:'', //搜索框值
      dialog_add: false, //添加友链的对话框
      dialog_edit: false, //编辑友链的对话框
      link_add:{
        blogName:'',  //友链名称
        pictureAddress:'',
        blogAddress:'',
      },
      link_edit:{
        blogName:'',  //友链名称
        pictureAddress:'',
        blogAddress:'',
      },
      showPage: true, //是否显示分页
   }
  },
  mounted() {
    this.initLink();
  },
  methods:{
    //初始化友链数据
    initLink(){
      const _this = this
      this.getRequest('/links/getLinksByPage?current=' + this.currentPage + '&size=' + this.pagesize).then(resp=>{
        console.log(resp)
        _this.linkData = resp.obj.records
        _this.total = resp.obj.total
      })
    },
    //添加友链
    addLink(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          this.postRequest('/links/saveLink',this.link_add).then(resp=>{
            console.log(resp)
            _this.initLink()
            _this.link_add = ''
            _this.dialog_add = false
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //编辑友链
    handleEdit(index, row) {
      console.log(index, row);
      this.link_edit = row
      this.dialog_edit = true
    },
    //更新友链
    editLink(formName2) {
      this.$refs[formName2].validate((valid) => {
        if (valid) {
          const _this = this
          this.putRequest('/links/updateLink',this.link_edit).then(resp=>{
            console.log(resp)
            _this.initLink()
            _this.dialog_edit = false
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //删除友链
    handleDelete(index, row) {
      console.log(index, row);
      const _this = this
      this.$confirm('此操作将永久删除《' + row.blogName  + '》, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/links/deleteLinkById?id=' + row.id).then(resp=>{
            if(resp){
              this.initLink()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
     
    },
    //搜索友链
    search_name(){
       const _this = this
       this.getRequest('/links/getLinksByName?name=' + this.input_name).then(resp=>{
         console.log(resp)
         if(_this.input_name == ''){
           _this.initLink();
           _this.showPage = true
         }else{
          _this.linkData = resp.obj
          _this.showPage = false
          _this.input_name = ''
         }
         

       })
    },
    //分页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
      this.initLink()
    }
  }
}
</script>

<style scoped>

</style>