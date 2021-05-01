package com.xlblog.blog.mapper;

import com.xlblog.blog.entity.TBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
public interface TBlogMapper extends BaseMapper<TBlog> {
    //根据id查找博客
    TBlog getByBlogId(@Param("id") String id);
    //前台分页查询
    List<TBlog> vuefindByPage(@Param("current") Long current, @Param("size")Long size,
                              @Param("typeid") Long type_id, @Param("tagsid") Long tags_id);
    //前台查询所有博客
    List<TBlog> findAllBlog();
    //前台根据标题查找博客
    List<TBlog> getByTitle(String title);

//    void updateByComment(TBlog tBlog);
}
