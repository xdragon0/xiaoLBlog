package com.xlblog.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.entity.TComment;
import com.xlblog.blog.entity.TUser;
import com.xlblog.blog.service.TBlogService;
import com.xlblog.blog.service.TCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/comment")
public class TCommentController {

    @Autowired
    private TCommentService commentService;
    @Autowired
    private TBlogService tBlogService;

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public List<TComment> comments(@PathVariable Long blogId) {
        return commentService.listCommentByBlogId(blogId);
    }

    //查询所有评论
    @GetMapping("/getCommentByPage")
    public Page<TComment> getCommentByPage(Long current, Long size){
        return commentService.getCommentByPage(current, size);
    }

    //根据评论人姓名查询评论
    @GetMapping("/comments")
    public List<TComment> findCommentByNickname(String nickname){
        return commentService.findCommentByNickname(nickname);
    }


    //新增评论
    @PostMapping("/comments")
    public List<TComment> post(@RequestBody TComment tComment,Authentication authentication,HttpServletRequest request) {
        //获取评论人的ip
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        Long blogId = tComment.getBlogId();
        if (authentication != null){
            TUser user = (TUser)authentication.getPrincipal();
            tComment.setAvatar(user.getAvatar());
            tComment.setAdminComment(true);
            tComment.setIp(ip);
        }
        else {
            //设置头像
            tComment.setAvatar("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2070453827,1163403148&fm=26&gp=0.jpg");
            tComment.setIp(ip);
        }
        if (tComment.getParentComment() != null) {
            tComment.setParentCommentId(tComment.getParentComment().getId());
        }
        commentService.saveComment(tComment);
        TBlog tBlog = tBlogService.getById(blogId);
        tBlog.setCommentCount(tBlog.getCommentCount() + 1);
        tBlogService.updateByComment(tBlog);
        return commentService.listCommentByBlogId(blogId);
    }

    //删除评论
    @DeleteMapping("/deleteComment")
    public String delete(Long id){
        commentService.deleteComment(id);
        return "OK";
    }

}
