<template>
  <div class="m_container">
   
    <!-- 博客内容 -->
    <div class="m_content">
      <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="editForm.title"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="editForm.description"></el-input>
        </el-form-item>
        <el-form-item label="首页图片" prop="first_picture">
          <el-input v-model="editForm.first_picture"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <mavon-editor ref=md v-model="editForm.content"/>
        </el-form-item>
        <el-form-item style="margin: auto;">
          <el-button type="info" @click="temporarySave()">暂时保存</el-button>
          <el-button type="primary" @click="tosubmitForm('editForm')"">发布文章</el-button>
          <!-- <el-button>取消</el-button> -->
        </el-form-item>
      </el-form>
    </div>

   <!-- 对话框内容 -->
   <el-dialog title="发布文章" :visible.sync="dialogFormVisible" width="35%">
      <el-form :model="editForm" ref="editForm2">
        <el-form-item label="文章标签"  :label-width="formLabelWidth">
          <!-- <el-input v-model="oldtags" placeholder="请选择标签，多个标签请用逗号隔开" width="80%"></el-input> -->
          <el-tag
            :key="tag"
            v-for="tag in editForm.tags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{tag}}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          >
          </el-input>
          <el-button v-else class="button-new-tag" style="margin-left: 10px;" size="small" @click="showInput">+ New Tag</el-button>
        </el-form-item>
        <el-form-item label="分类专栏" prop="type_id" :label-width="formLabelWidth"
            :rules="{
              required: true, message: '分类专栏不能为空', trigger: 'blur'
            }">
          <el-select v-model="editForm.type_id" placeholder="请选择分类专栏">
            <el-option v-for="(item,index) in getalltype" :key="item.index" :label="item.name" :value="item.id + ''"></el-option>
          </el-select>
          <el-button type="primary" size="small" @click="dialog2 = true" style="margin-left: 10px;">新建分类专栏</el-button>
        </el-form-item>
        <el-form-item label="文章类型" prop="flag" :label-width="formLabelWidth"
            :rules="{
              required: true, message: '文章类型不能为空', trigger: 'blur'
            }">
          <el-select v-model="editForm.flag" placeholder="请选择文章类型，默认为原创">
            <el-option label="原创" value="原创"></el-option>
            <el-option label="转载" value="转载"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布形式" prop="published" :label-width="formLabelWidth"
            :rules="{
              required: true, message: '发布形式不能为空', trigger: 'blur'
            }">
          <el-select v-model="editForm.published" placeholder="请选择发布形式，，默认为私密">
            <el-option label="私密" value=0></el-option>
            <el-option label="公开" value=1></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitBlog('editForm2')">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 新建分类专栏 -->
    <el-dialog
      title="新建分类专栏"
      :visible.sync="dialog2"
      width="30%"
      >
      <el-form status-icon :model="type" ref="type" :rules="rules2" label-width="120px">
        <el-form-item label="分类专栏名" prop="name">
          <el-input v-model="type.name" placeholder="请输入要新建的分类专栏名"></el-input>
        </el-form-item>
      </el-form>
       <span slot="footer" class="dialog-footer">
          <el-button @click="dialog2 = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('type')">确 定</el-button>
        </span>
    </el-dialog>


  </div>
</template>
<script>
  export default {
    name: "WriteBlog",
    data() {
      return {
        editForm: {  //博客文章表单
          id: null,
          title: '',
          description: '',
          first_picture: '',
          content: '',
          type_id: '',
          flag:'',
          published: null,
          tags: [],
        },
        editForm2: {  //用来校验的表单
          type_id: null,
          flag:'',
          published: null,
        },
        oldtags:'',  //字符串类型的标签
        type:{  //分类专栏
           name:''
        },
        getalltype:[], // 所有分类专栏
        dialogFormVisible: false,  //控制发布博客对话框
        dialog2: false,  //控制新增分类专栏对话框
        rules1: {  //表单校验
          title: [
            {required: true, message: '请输入标题', trigger: 'blur'},
            {min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请输入摘要', trigger: 'blur'}
          ],
          first_picture: [
            {required: true, message: '请输入图片地址', trigger: 'blur'}
          ],
          content: [
            {required: true, message: '请输入文章内容', trigger: 'blur'}
          ],
        },
        rules2:{ //表单校验
          name: [
            {required: true, message: '分类专栏名不能为空', trigger: 'blur'}
          ],
        },
        formLabelWidth: '120px',  // 输入框的宽度
        inputVisible: false,
        inputValue: ''
      }
    },
    created() {
     
    },
    methods: {
      //删除标签
      handleClose(tag) {
        this.editForm.tags.splice(this.editForm.tags.indexOf(tag), 1);
      },
      //输入标签
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },
      //回车添加标签
      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.editForm.tags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },
      //去发布文章
      toSubmit() {
        this.dialogFormVisible = true
        this.initType()
      },
      //初始化文章专栏
      initType(){
        const _this = this
        this.getRequest('/type/getAllType').then(resp=>{
          console.log(resp)
          _this.getalltype = resp.obj
        })
      },
      //添加新的分类专栏
      addNewType(){
        const _this = this 
        this.postRequest('/type/saveType',this.type).then(resp=>{
          if(resp){
            _this.type.name = ''
            this.initType()
            _this.dialog2 = false
          }
        })
      },
      //校验添加分类专栏表单
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.addNewType()
          } else {
            return false;
          }
        });
      },
      //校验博客基本内容表单
      tosubmitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.toSubmit()
          } else {
            return false;
          }
        });
      },
      //校验发布博客表单,校验成功后发布博客
      submitBlog(formName) {
        const _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //发布博客
            this.postRequest('/blog/savaBT',this.editForm).then(resp=>{
              if(resp){
                _this.dialogFormVisible = false
                alert("发布成功，点击确定跳转到博客列表页面!")
                _this.$router.push('/admin/allblogs')
              }
            })
          } else {
            return false;
          }
        });
      },
      //暂时保存博客
      temporarySave(){
        const _this = this
        console.log( this.editForm)
        this.postRequest('/blog/temporarySave',this.editForm).then(resp=>{
          if(resp){
            this.$message('已成功保存草稿成功');
          }
        })
      },
      //上传图片
      // 绑定@imgAdd event
      //  $imgAdd(pos, $file){
      //      console.log($file)
      //      var _this = this
      //      // 第一步.将图片上传到服务器.
      //      var formdata = new FormData();
      //      formdata.append('image', $file);
      //      this.postPicRequest('/blog/pic',formdata).then((resp) => {
      //          // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
      //          /**
      //          * $vm 指为mavonEditor实例，可以通过如下两种方式获取
      //          * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
      //          * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
      //          */
      //         //  $vm.$img2Url(pos, url);
      //         console.log(resp)
      //          if(resp.status === 200){
      //             let url = resp.obj
      //             _this.$refs.md.$img2Url(pos,url) 
      //           }
      //           console.log(url) 
      //      })
      //   },
    }
  }
</script>

<style>
  .m_container{
    margin-top: 20px;
  }
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
