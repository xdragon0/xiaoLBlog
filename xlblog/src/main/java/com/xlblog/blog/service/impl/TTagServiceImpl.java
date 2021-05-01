package com.xlblog.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TTag;
import com.xlblog.blog.entity.TType;
import com.xlblog.blog.mapper.TTagMapper;
import com.xlblog.blog.service.TTagService;
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
public class TTagServiceImpl extends ServiceImpl<TTagMapper, TTag> implements TTagService {

    @Autowired
    TTagMapper tTagMapper;

    @Override
    public RespBean pageTags(Long current, Long size) {
        RespBean respBean = RespBean.build();
        //创建Page对象
        Page<TTag> tagPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<TTag> wrapper = new QueryWrapper<>();
        //调用mybatis plus分页方法进行查询
        tTagMapper.selectPage(tagPage,wrapper);
        //通过Page对象获取分页信息
        List<TTag> typeList = tagPage.getRecords(); //每页的数据 list集合
        long pagesize = tagPage.getSize(); //每页显示的条数
        long total = tagPage.getTotal(); //总记录数
        long pages = tagPage.getPages(); //总页数

        respBean.setStatus(200);
        respBean.setObj(tagPage);
        return respBean;
    }

    @Override
    public RespBean getTagByName(String name) {
        RespBean respBean = RespBean.build();
        QueryWrapper<TTag> queryWrapper = new QueryWrapper<TTag>();
        queryWrapper.like("name",name);
        List<TTag> tagList = tTagMapper.selectList(queryWrapper);
        respBean.setStatus(200);
        respBean.setObj(tagList);
        return respBean;
    }

    @Override
    public RespBean updateTag(TTag tTag) {
        RespBean respBean = RespBean.build();
        if (tTagMapper.updateById(tTag) == 1){
            respBean.setMsg("更新标签成功");
            respBean.setStatus(200);
            return respBean;
        }
        respBean.setMsg("更新标签失败");
        return respBean;
    }

    @Override
    public RespBean deleteTagById(Long id) {
        RespBean respBean = RespBean.build();
        if (tTagMapper.deleteById(id) == 1){
            respBean.setStatus(200);
            respBean.setMsg("删除标签成功");
            return respBean;
        }
        respBean.setMsg("删除标签失败");
        return respBean;
    }

    @Override
    public RespBean getTagById(Long id) {
        RespBean respBean = RespBean.build();
        TTag tTag = tTagMapper.selectById(id);
        respBean.setStatus(200);
        respBean.setObj(tTag);
        return respBean;
    }

    @Override
    public RespBean getAllTag() {
        RespBean respBean = RespBean.build();
        List<TTag> tagList = tTagMapper.getAllTag();
        respBean.setStatus(200);
        respBean.setObj(tagList);
        return respBean;
    }
}
