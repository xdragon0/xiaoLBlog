package com.xlblog.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TLinks;
import com.xlblog.blog.entity.TTag;
import com.xlblog.blog.mapper.TLinksMapper;
import com.xlblog.blog.service.TLinksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlblog.blog.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TLinksServiceImpl extends ServiceImpl<TLinksMapper, TLinks> implements TLinksService {

    @Autowired
    TLinksMapper tLinksMapper;

    @Override
    public RespBean pageLinks(Long current, Long size) {
        RespBean respBean = RespBean.build();
        //创建Page对象
        Page<TLinks> tLinksPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<TLinks> wrapper = new QueryWrapper<>();
        //以创建时间排序（降序）
        wrapper.orderByDesc("create_time");
        //调用mybatis plus分页方法进行查询
        tLinksMapper.selectPage(tLinksPage,wrapper);
        //通过Page对象获取分页信息
        List<TLinks> typeList = tLinksPage.getRecords(); //每页的数据 list集合
        long pagesize = tLinksPage.getSize(); //每页显示的条数
        long total = tLinksPage.getTotal(); //总记录数
        long pages = tLinksPage.getPages(); //总页数

        respBean.setStatus(200);
        respBean.setObj(tLinksPage);
        return respBean;
    }

    @Override
    public RespBean getLinksByName(String name) {
        RespBean respBean = RespBean.build();
        QueryWrapper<TLinks> queryWrapper = new QueryWrapper<TLinks>();
        queryWrapper.like("blog_name",name);
        List<TLinks> tLinksList = tLinksMapper.selectList(queryWrapper);
        respBean.setStatus(200);
        respBean.setObj(tLinksList);
        return respBean;
    }

    @Override
    public RespBean updateLink(TLinks tLinks) {
        RespBean respBean = RespBean.build();
        if (tLinksMapper.updateById(tLinks) == 1){
            respBean.setMsg("更新友链成功");
            respBean.setStatus(200);
            return respBean;
        }
        respBean.setMsg("更新友链失败");
        return respBean;
    }

    @Override
    public RespBean deleteLinkById(Long id) {
        RespBean respBean = RespBean.build();
        if (tLinksMapper.deleteById(id) == 1){
            respBean.setStatus(200);
            respBean.setMsg("删除友链成功");
            return respBean;
        }
        respBean.setMsg("删除友链失败");
        return respBean;
    }

    @Override
    public RespBean getLinksById(Long id) {
        RespBean respBean = RespBean.build();
        TLinks tLinks = tLinksMapper.selectById(id);
        respBean.setStatus(200);
        respBean.setObj(tLinks);
        return respBean;
    }
}
