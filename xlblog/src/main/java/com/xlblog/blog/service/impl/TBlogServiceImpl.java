package com.xlblog.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.entity.TBlogTags;
import com.xlblog.blog.entity.TTag;
import com.xlblog.blog.entity.Tcount;
import com.xlblog.blog.mapper.TBlogMapper;
import com.xlblog.blog.mapper.TBlogTagsMapper;
import com.xlblog.blog.mapper.TTagMapper;
import com.xlblog.blog.service.TBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlblog.blog.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@Transactional(rollbackFor=Exception.class)  //开启事务
@Service
public class TBlogServiceImpl extends ServiceImpl<TBlogMapper, TBlog> implements TBlogService {

    @Autowired
    TBlogMapper tBlogMapper;
    @Autowired
    TTagMapper tTagMapper;
    @Autowired
    TBlogTagsMapper tBlogTagsMapper;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 分页查询
     * @param current
     * @param limit
     * @param published
     * @param flag
     * @param share_statement
     * @param is_delete
     * @return
     */
    @Override
    public RespBean pageBlogs(Long current, Long limit, Boolean published, String flag,
                              Boolean share_statement, Boolean is_delete) {
        RespBean respBean = RespBean.build();
        //创建Page对象
        Page<TBlog> tBlogPage = new Page<>(current,limit);
        //构建条件
        QueryWrapper<TBlog> wrapper = new QueryWrapper<>();
        //获取传入讲师的条件是否为空
        //多条件组合查询
        //判断条件值是否为空,如果不为空拼接条件
        if (published != null){
            //构建条件
            wrapper.eq("published",published);
        }
        if (flag != null){
            //构建条件
            wrapper.eq("flag",flag);
        }
        if (share_statement != null){
            //构建条件
            wrapper.eq("share_statement",share_statement);
        }
        if (is_delete != null){
            //构建条件
            wrapper.eq("is_delete",is_delete);
        }
        //以更新时间排序（降序）
        wrapper.orderByDesc("update_time");
        //调用mybatis plus分页方法进行查询
        tBlogMapper.selectPage(tBlogPage,wrapper);

        //通过Page对象获取分页信息
        List<TBlog> tBlogList = tBlogPage.getRecords(); //每页的数据 list集合
        long size = tBlogPage.getSize(); //每页显示的条数
        long total = tBlogPage.getTotal(); //总记录数
        long pages = tBlogPage.getPages(); //总页数

        respBean.setStatus(200);
        respBean.setObj(tBlogPage);
        return respBean;
    }

    /**
     * 根据标题查询
     * @param title
     * @return
     */
    @Override
    public RespBean getByTitle(String title) {
        RespBean respBean = RespBean.build();
//        QueryWrapper<TBlog> queryWrapper = new QueryWrapper<TBlog>();
//        queryWrapper.like("title",title);
//        List<TBlog> tBlogList = tBlogMapper.selectList(queryWrapper);
        List<TBlog> tBlogList = tBlogMapper.getByTitle(title);
        respBean.setStatus(200);
        respBean.setObj(tBlogList);
        return respBean;
    }

    /**
     * 保存博客和标签
     * @param params
     * @return
     */
    @Override
    public RespBean saveBT(HashMap<String, Object> params) {
        RespBean respBean = RespBean.build();
        TBlog tBlog = new TBlog();
        tBlog.setTitle((String) params.get("title"));
        tBlog.setDescription((String) params.get("description"));
        tBlog.setFirstPicture((String) params.get("first_picture"));
        tBlog.setContent((String) params.get("content"));
        tBlog.setTypeId(Long.parseLong(params.get("type_id").toString()));
        tBlog.setFlag((String) params.get("flag"));

        String publish = (String) params.get("published");
        if (publish.equals("1")){
            tBlog.setPublished(true);
        }else {
            tBlog.setPublished(false);
        }
//        tBlog.setPublished(Boolean.valueOf(params.get("published").toString()));
        tBlog.setShareStatement(true); //设置状态为已发布
        //保存文章
        int result_blog = tBlogMapper.insert(tBlog);
        int result_tag = 0;
        int resule_bt = 0;
        List<String> tags = (List) params.get("tags");
        for (String tag: tags) {
            TTag tTag = new TTag();
            tTag.setName(tag);
            //保存关键词
            result_tag = tTagMapper.insert(tTag);
            TBlogTags tBlogTags = new TBlogTags();
            tBlogTags.setBlogsId(tBlog.getId());
            tBlogTags.setTagsId(tTag.getId());
            resule_bt = tBlogTagsMapper.insert(tBlogTags);
        }
        if (result_blog != 0 && result_tag != 0 && resule_bt !=0){
            respBean.setStatus(200);
            respBean.setMsg("添加成功！");
            return respBean;
        }
        respBean.setMsg("添加失败");
        return respBean;
    }

    /**
     * 暂时保存博客
     * @param params
     * @return
     */
    @Override
    public RespBean temporarySave(HashMap<String, Object> params) {
        RespBean respBean = RespBean.build();
        TBlog tBlog = new TBlog();
        tBlog.setTitle((String) params.get("title"));
        tBlog.setDescription((String) params.get("description"));
        tBlog.setFirstPicture((String) params.get("first_picture"));
        tBlog.setContent((String) params.get("content"));
        //保存文章
        int result_blog = tBlogMapper.insert(tBlog);
        if (result_blog != 0){
            respBean.setStatus(200);
            respBean.setMsg("添加成功！");
            return respBean;
        }
        respBean.setMsg("添加失败");
        return respBean;
    }

    /**
     * 更新文章
     * @param params
     * @return
     */
    @Override
    public RespBean updateBlog(HashMap<String, Object> params) {
        RespBean respBean = RespBean.build();
        //根据id查询文章
        TBlog tBlog = tBlogMapper.selectById(Long.parseLong(params.get("id").toString()));
        tBlog.setTitle((String) params.get("title"));
        tBlog.setDescription((String) params.get("description"));
        tBlog.setFirstPicture((String) params.get("first_picture"));
        tBlog.setContent((String) params.get("content"));
        tBlog.setTypeId(Long.parseLong(params.get("type_id").toString()));
        tBlog.setFlag((String) params.get("flag"));
        tBlog.setPublished(Boolean.valueOf(params.get("published").toString()));
        tBlog.setUpdateTime(LocalDateTime.now());

        //更新文章
        int result_blog = tBlogMapper.updateById(tBlog);
        int result_tag = 0;
        int resule_bt = 0;
        //先删除与这篇文章相关的标签和中间表记录
        QueryWrapper<TBlogTags> queryWrapper1 = new QueryWrapper<TBlogTags>();
        queryWrapper1.eq("blogs_id",tBlog.getId());
        List<TBlogTags> blogTagsList =  tBlogTagsMapper.selectList(queryWrapper1);
        for (TBlogTags bt : blogTagsList) {
            //先删除标签表的数据
            tTagMapper.deleteById(bt.getTagsId());
            //再删除博客标签关联表的数据
            QueryWrapper<TBlogTags> queryWrapper = new QueryWrapper<TBlogTags>();
            queryWrapper.eq("tags_id",bt.getTagsId());
            //再删除博客标签关联表的数据
            tBlogTagsMapper.delete(queryWrapper);
        }
        //然后再重新添加标签
        List<String> tags = (List) params.get("tags");
        for (String tag : tags) {
            //保存标签
            TTag tTag = new TTag();
            tTag.setName(tag);
            result_tag = tTagMapper.insert(tTag);
            //保存中间表记录
            TBlogTags tBlogTags = new TBlogTags();
            tBlogTags.setBlogsId(tBlog.getId());
            tBlogTags.setTagsId(tTag.getId());
            resule_bt = tBlogTagsMapper.insert(tBlogTags);
        }
        if (result_blog != 0 && result_tag != 0 && resule_bt !=0){
            respBean.setStatus(200);
            respBean.setMsg("更新博客成功！");
            return respBean;
        }
        respBean.setMsg("更新博客失败");
        return respBean;
    }

    /**
     * 逻辑删除博客
     * @param id
     * @return
     */
    @Override
    public RespBean logicDeleteBlog(String id) {
        RespBean respBean = RespBean.build();
        TBlog tBlog = tBlogMapper.selectById(id);
        if (tBlog != null){
            tBlog.setIsDelete(true);
            tBlog.setUpdateTime(LocalDateTime.now());
            tBlogMapper.updateById(tBlog);
            respBean.setStatus(200);
            respBean.setMsg("删除博客成功！");
            return respBean;
        }else {
            respBean.setStatus(500);
            respBean.setMsg("删除博客失败");
            return respBean;
        }
    }

    /**
     * 删除博客和标签（彻底删除）
     * @param id
     * @return
     */
    @Override
    public RespBean deleteBT(String id) {
        RespBean respBean = RespBean.build();
        TBlog tBlog = tBlogMapper.selectById(id);
        //删除与这篇文章相关的标签和中间表记录
        QueryWrapper<TBlogTags> queryWrapper1 = new QueryWrapper<TBlogTags>();
        queryWrapper1.eq("blogs_id",tBlog.getId());
        List<TBlogTags> blogTagsList =  tBlogTagsMapper.selectList(queryWrapper1);
        int r_tag = 0;
        int r_blog_tag = 0;
        for (TBlogTags bt : blogTagsList) {
            //先删除标签表的数据
            r_tag = tTagMapper.deleteById(bt.getTagsId());
            //再删除博客标签关联表的数据
            QueryWrapper<TBlogTags> queryWrapper = new QueryWrapper<TBlogTags>();
            queryWrapper.eq("tags_id",bt.getTagsId());
            //再删除博客标签关联表的数据
            r_blog_tag = tBlogTagsMapper.delete(queryWrapper);
        }
        //删除博客文章
        int r_blog = tBlogMapper.deleteById(id);
        if (r_tag != 0 && r_blog_tag!= 0 && r_blog!= 0){
            respBean.setStatus(200);
            respBean.setMsg("删除博客和标签成功");
            return respBean;
        }else {
            respBean.setStatus(500);
            respBean.setMsg("删除博客和标签失败");
            return respBean;
        }

    }

    /**
     * 还原删除的博客
     * @param id
     * @return
     */
    @Override
    public RespBean recoveryBlog(String id) {
        RespBean respBean = RespBean.build();
        TBlog tBlog = tBlogMapper.selectById(id);
        if (tBlog != null){
            tBlog.setIsDelete(false);
            tBlog.setUpdateTime(LocalDateTime.now());
            tBlogMapper.updateById(tBlog);
            respBean.setStatus(200);
            respBean.setMsg("还原博客成功！");
            return respBean;
        }else {
            respBean.setStatus(500);
            respBean.setMsg("还原博客失败");
            return respBean;
        }
    }

    /**
     * 根据id查找博客
     * @param id
     * @return
     */
    @Override
    public RespBean getByBlogId(String id) {
        RespBean respBean = RespBean.build();
        TBlog tBlog = tBlogMapper.getByBlogId(id);
        tBlog.setViews(tBlog.getViews() + 1);
        tBlogMapper.updateById(tBlog);
        respBean.setStatus(200);
        respBean.setObj(tBlog);
        return respBean;
    }



    @Override
    public RespBean vuefindByPage(Long current, Long size, Long type_id, Long tags_id) {
        RespBean respBean = RespBean.build();
        current = current ==null? 0 : current;
        size = size==null? 4 : size;

        HashMap<String,Object> map = new HashMap<>();

        //分页查询出当前页面显示的数据
        List<TBlog> blogList = tBlogMapper.vuefindByPage((current-1) * size,size,type_id,tags_id);
        if (blogList == null){
            respBean.setMsg("没有数据");
            return respBean;
        }
        //查询数据的总条数
        List<TBlog> allBlogs = tBlogMapper.findAllBlog();
        Integer totals = allBlogs.size();
        //计算总页数
        // 如果总数据条数可以整除每一页数据个数, 说明结果正好为总页数
        // 如果总数据条数无法整除每一页数据个数, 说明总页数需要结果 + 1
        Long totalPage = totals % current == 0 ? totals/size : (totals/size) + 1;
        //将数据添加到集合
        map.put("current",current);
        map.put("size",size);
        map.put("totals",totals);
        map.put("totalPage",totalPage);
        map.put("blogList",blogList);
        respBean.setStatus(200);
        respBean.setObj(map);
        return respBean;
    }

    @Override
    public void updateByComment(TBlog tBlog) {
        tBlogMapper.updateById(tBlog);
    }

    //统计数据
    @Override
    public RespBean countBlog() {
        RespBean respBean = RespBean.build();
        Tcount tcount = new Tcount();
        List<TBlog> blogList = tBlogMapper.findAllBlog();
        int views = 0;
        long comments = 0;
        long messages = 0;
        TBlog tBlog = tBlogMapper.selectById(-1);
        messages = tBlog.getCommentCount();
        for (TBlog blog:blogList) {
            views += blog.getViews();
            comments += blog.getCommentCount();
        }
        tcount.setBlogCount(blogList.size());
        tcount.setViewCount(views);
        tcount.setCommentCount(comments);
        tcount.setMessageCount(messages);
        respBean.setObj(tcount);
        return respBean;
    }

    @Override
    public RespBean vuefindHotBlog() {
        RespBean respBean = RespBean.build();
        QueryWrapper<TBlog> queryWrapper = new QueryWrapper<TBlog>();
        queryWrapper.orderByDesc("views");
        queryWrapper.last("limit 0,5");
        respBean.setObj(tBlogMapper.selectList(queryWrapper));
        return respBean;
    }

}
