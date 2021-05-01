package com.xlblog.blog.service;

import com.xlblog.blog.entity.TTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlblog.blog.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
public interface TTagService extends IService<TTag> {

    RespBean pageTags(Long current, Long size);

    RespBean getTagByName(String name);

    RespBean updateTag(TTag tTag);

    RespBean deleteTagById(Long id);

    RespBean getTagById(Long id);

    RespBean getAllTag();
}
