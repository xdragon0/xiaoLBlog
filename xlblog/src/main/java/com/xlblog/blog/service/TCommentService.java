package com.xlblog.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
public interface TCommentService extends IService<TComment> {
    //根据博客id查询评论信息
    List<TComment> listCommentByBlogId(Long blogId);

    //添加保存评论
    int saveComment(TComment comment);

    //删除评论
    void deleteComment(Long id);

    List<TComment> findCommentByNickname(String nickname);

    Page<TComment> getCommentByPage(Long current, Long size);

}
