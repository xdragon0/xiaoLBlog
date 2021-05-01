package com.xlblog.blog.controller;

import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.form.BlogForm;
import com.xlblog.blog.mapper.TBlogMapper;
import com.xlblog.blog.service.TBlogService;
import com.xlblog.blog.utils.RespBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/blog")
public class TBlogController {

    @Autowired
    TBlogService tBlogService;

    RespBean respBean = RespBean.build();

    /**
     * 分页查询
     * @param current
     * @param size
     * @param published
     * @param flag
     * @param share_statement
     * @param is_delete
     * @return
     */
    @GetMapping("/getByPage")
    @ApiOperation("博客分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "published",value = "是否公开"),
            @ApiImplicitParam(name = "flag",value = "原创或转载"),
            @ApiImplicitParam(name = "share_statement",value = "草稿"),
            @ApiImplicitParam(name = "is_delete",value = "是否已删除"),
    })
    public RespBean getByPage(Long current, Long size, Boolean published,
                              String flag, Boolean share_statement, Boolean is_delete){
        return tBlogService.pageBlogs(current, size,published,flag,share_statement,is_delete);
    }

    /**
     * 前台分页
     * @param current
     * @param size
     * @param type_id
     * @param tags_id
     * @return
     */
    @GetMapping("/vuefindByPage")
    public RespBean vuefindByPage(Long current, Long size, Long type_id, Long tags_id){
        return tBlogService.vuefindByPage(current, size,type_id,tags_id);
    }

    @GetMapping("/vuefindHotBlog")
    public RespBean vuefindHotBlog(){
        return tBlogService.vuefindHotBlog();
    }

    /**
     * 根据博客标题查询
     * @param title
     * @return
     */
    @GetMapping("/getByTitle")
    @ApiOperation("通过文章标题查询")
    @ApiImplicitParam(name = "title",value = "文章的标题")
    public RespBean findByTitle(String title){
        return tBlogService.getByTitle(title);
    }

    /**
     * 保存文章和标签
     * @param params
     * @return
     */
    @PostMapping("/savaBT")
    public RespBean saveBlogTag(@RequestBody HashMap<String,Object> params) {
        RespBean check = BlogForm.check(params);
        if (check.getStatus() == 500) {
            return check;
        }else {
            return tBlogService.saveBT(params);
        }
    }

    /**
     * 暂时保存博客
     * @param params
     * @return
     */
    @PostMapping("/temporarySave")
    public RespBean temporarySave(@RequestBody HashMap<String,Object> params){
        RespBean checkTemporaryBlog = BlogForm.checkTemporaryBlog(params);
        if (checkTemporaryBlog.getStatus() == 500) {
            return checkTemporaryBlog;
        }
        else {
            return tBlogService.temporarySave(params);
        }
    }

    /**
     * 更新博客和标签
     * @param params
     * @return
     */
    @PutMapping("/updateBlog")
    public RespBean updateBlog(@RequestBody HashMap<String,Object> params){
        RespBean checkUpdateBlog = BlogForm.check(params);
        if (checkUpdateBlog.getStatus() == 500) {
            return checkUpdateBlog;
        }
        else {
            return tBlogService.updateBlog(params);
        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/logicDeleteBlog")
    public RespBean logicDeleteBlog(String id){
        TBlog tBlog = tBlogService.getById(id);
        if (tBlog != null){
            return tBlogService.logicDeleteBlog(id);
        }else {
            respBean.setStatus(500);
            respBean.setMsg("没有数据");
            return respBean;
        }
    }

    /**
     * 删除博客和对应标签
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBlog")
    public RespBean deleteBlog(String id){
        TBlog tBlog = tBlogService.getById(id);
        if (tBlog != null){
            return tBlogService.deleteBT(id);
        }else {
            respBean.setStatus(500);
            respBean.setMsg("没有数据");
            return respBean;
        }
    }

    /**
     * 还原删除的博客
     * @param id
     * @return
     */
    @GetMapping("/recoveryBlog")
    public RespBean recoveryBlog(String id){
        return tBlogService.recoveryBlog(id);
    }

    /**
     * 根据id查找博客
     * @param id
     * @return
     */
    @GetMapping("/getByBlogId")
    public RespBean getByBlogId(String id){
        return tBlogService.getByBlogId(id);
    }

    /**
     * 统计数据
     * @return
     */
    @GetMapping("/countBlog")
    public RespBean countBlog(){
        return tBlogService.countBlog();
    }


    /**
     * 上传图片到本地服务器
     */
//    final static String PIC_PATH = "static/pics/"; //图片存放的相对于项目的相对位置
//
//    @CrossOrigin
//    @PostMapping("/pic")
//    @ResponseBody
//    public RespBean uploadPic(MultipartHttpServletRequest mutiRequest, HttpServletRequest request){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //生成日期格式
//        String datePrefix = dateFormat.format(new Date()); //生成当前日期作为前缀
//        String savePath = "src/main/resources/" + PIC_PATH; // 存储路径
//        File folder = new File(savePath+datePrefix); //生成带当前日期的文件路径
//        if(!folder.isDirectory()){
//            folder.mkdirs();
//        }
//        String originationalName = mutiRequest.getFile("image").getOriginalFilename(); //获取图片名
//        //生成随机数确保唯一性，并加上图片后缀
//        String saveName = UUID.randomUUID().toString() + originationalName.substring(originationalName.lastIndexOf("."),originationalName.length());
//        String absolutePath = folder.getAbsolutePath(); //转换成绝对路径
//        try {
//            File fileToSave = new File(absolutePath + File.separator + saveName);
//            mutiRequest.getFile("image").transferTo(fileToSave); //图片存储到服务端
//            String returnPath = request.getScheme() + "://"
//                    + request.getServerName()+":"+request.getServerPort()
//                    +"/pics/" //映射路径
//                    + datePrefix +"/"+ saveName;
//            respBean.setStatus(200);
//            respBean.setMsg("上传图片成功！");
//            respBean.setObj(returnPath);
//            return respBean;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        respBean.setStatus(500);
//        respBean.setMsg("上传图片失败");
//        return respBean;
//    }

}
