package com.xlblog.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.entity.TComment;
import com.xlblog.blog.entity.TTag;
import com.xlblog.blog.mapper.TBlogMapper;
import com.xlblog.blog.mapper.TCommentMapper;
import com.xlblog.blog.service.TCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@Service
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment> implements TCommentService {

    @Autowired
    private TCommentMapper commentMapper;

    @Autowired
    private TBlogMapper blogMapper;
    
    //存放迭代找出的所有子代的集合
    private List<TComment> tempReplys = new ArrayList<>();

    /**
     * @Description: 查询评论
     * @Param: blogId：博客id
     * @Return: 评论消息
     */
    @Override
    public List<TComment> listCommentByBlogId(Long blogId) {
        //查询出父节点
        List<TComment> comments = commentMapper.findByBlogIdParentIdNull(blogId, Long.parseLong("-1"));
        for(TComment comment : comments){
            Long id = comment.getId();
            String parentNickname1 = comment.getNickname();
            List<TComment> childComments = commentMapper.findByBlogIdParentIdNotNull(blogId,id);
            //查询出子评论
            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    /**
     * @Description: 查询出子评论
     * @Auther: ONESTAR
     * @Date: 10:43 2020/6/23
     * @Param: childComments：所有子评论
     * @Param: parentNickname1：父评论姓名
     * @Return:
     */
    private void combineChildren(Long blogId, List<TComment> childComments, String parentNickname1) {
        //判断是否有一级子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(TComment childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
                //查询出子二级评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    /**
     * @Description: 循环迭代找出子集回复
     * @Auther: ONESTAR
     * @Date: 10:44 2020/6/23
     * @Param: chileId:子评论id
     * @Param: parentNickname1:子评论姓名
     * @Return:
     */
    private void recursively(Long blogId, Long childId, String parentNickname1) {
        //根据子一级评论的id找到子二级评论
        List<TComment> replayComments = commentMapper.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(TComment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    //新增评论
    @Override
    public int saveComment(TComment comment) {
        comment.setCreateTime(LocalDateTime.now());
        int comments = commentMapper.saveComment(comment);
        //文章评论计数
//        blogMapper.getCommentCountById(comment.getBlogId());
        return comments;
    }

    //删除评论
    @Override
    public void deleteComment(Long id) {
        TComment comment = commentMapper.selectById(id);
        commentMapper.deleteComment(id);
        TBlog tBlog = blogMapper.selectById(comment.getBlogId());
        tBlog.setCommentCount(tBlog.getCommentCount() - 1);
        blogMapper.updateById(tBlog);
//        blogMapper.getCommentCountById(comment.getBlogId());
    }

    //根据昵称查询评论
    @Override
    public List<TComment> findCommentByNickname(String nickname) {
        QueryWrapper<TComment> queryWrapper = new QueryWrapper<TComment>();
        queryWrapper.like("nickname",nickname);
        //以创建时间排序（降序）
        queryWrapper.orderByDesc("create_time");
        List<TComment> tBlogList = commentMapper.selectList(queryWrapper);
        return tBlogList;
    }

    @Override
    public Page<TComment> getCommentByPage(Long current, Long size) {
        //创建Page对象
        Page<TComment> commentPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<TComment> wrapper = new QueryWrapper<>();
        //排序
        wrapper.orderByDesc("create_time");
        //调用mybatis plus分页方法进行查询
        commentMapper.selectPage(commentPage,wrapper);
        //通过Page对象获取分页信息
        List<TComment> typeList = commentPage.getRecords(); //每页的数据 list集合
        long pagesize = commentPage.getSize(); //每页显示的条数
        long total = commentPage.getTotal(); //总记录数
        long pages = commentPage.getPages(); //总页数
        return commentPage;
    }


}
