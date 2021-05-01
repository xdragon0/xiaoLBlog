package com.xlblog.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlblog.blog.utils.RespBean;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
public interface TBlogService extends IService<TBlog> {
    //分页查询
    RespBean pageBlogs(Long current, Long limit, Boolean published, String flag, Boolean share_statement, Boolean is_delete);
    //根据博客标题查询
    RespBean getByTitle(String title);
    //添加博客和标签
    RespBean saveBT(HashMap<String, Object> params);
    //暂时保存博客
    RespBean temporarySave(HashMap<String, Object> params);
    //更新博客
    RespBean updateBlog(HashMap<String,Object> params);
    //删除博客（逻辑删除）
    RespBean logicDeleteBlog(String id);
    //删除博客和标签（彻底删除）
    RespBean deleteBT(String id);
    //还原博客
    RespBean recoveryBlog(String id);
    //根据博客id查询
    RespBean getByBlogId(String id);
    //分页查询博客（前台）
    RespBean vuefindByPage(Long current, Long size, Long type_id, Long tags_id);
    //更新评论总数
    void updateByComment(TBlog tBlog);
    //统计数据
    RespBean countBlog();
    //热门top文章
    RespBean vuefindHotBlog();

}
