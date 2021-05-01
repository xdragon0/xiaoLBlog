package com.xlblog.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.entity.TType;
import com.xlblog.blog.mapper.TTypeMapper;
import com.xlblog.blog.service.TTypeService;
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
public class TTypeServiceImpl extends ServiceImpl<TTypeMapper, TType> implements TTypeService {

    @Autowired
    TTypeMapper tTypeMapper;

    @Override
    public RespBean pageTypes(Long current, Long size) {
        RespBean respBean = RespBean.build();
        //创建Page对象
        Page<TType> typePage = new Page<>(current,size);
        //构建条件
        QueryWrapper<TType> wrapper = new QueryWrapper<>();
        //调用mybatis plus分页方法进行查询
        tTypeMapper.selectPage(typePage,wrapper);
        //通过Page对象获取分页信息
        List<TType> typeList = typePage.getRecords(); //每页的数据 list集合
        long pagesize = typePage.getSize(); //每页显示的条数
        long total = typePage.getTotal(); //总记录数
        long pages = typePage.getPages(); //总页数

        respBean.setStatus(200);
        respBean.setObj(typePage);
        return respBean;
    }

    /**
     * 通过名称查询分类
     * @param name
     * @return
     */
    @Override
    public RespBean getTypeByName(String name) {
        RespBean respBean = RespBean.build();
        QueryWrapper<TType> queryWrapper = new QueryWrapper<TType>();
        queryWrapper.like("name",name);
        List<TType> typeList = tTypeMapper.selectList(queryWrapper);
        respBean.setStatus(200);
        respBean.setObj(typeList);
        return respBean;
    }

    @Override
    public RespBean deleteTypeById(Long id) {
        RespBean respBean = RespBean.build();
        if (tTypeMapper.deleteById(id) == 1){
            respBean.setStatus(200);
            respBean.setMsg("删除分类成功");
            return respBean;
        }
        respBean.setMsg("删除分类失败");
        return respBean;
    }

    @Override
    public RespBean updateType(TType tType) {
        RespBean respBean = RespBean.build();
        if (tTypeMapper.updateById(tType) == 1){
            respBean.setMsg("更新分类成功");
            respBean.setStatus(200);
            return respBean;
        }
        respBean.setMsg("更新分类失败");
        return respBean;
    }

    @Override
    public RespBean getTypeById(Long id) {
        RespBean respBean = RespBean.build();
        TType tType = tTypeMapper.selectById(id);
        respBean.setStatus(200);
        respBean.setObj(tType);
        return respBean;
    }

    @Override
    public RespBean getAllType() {
        RespBean respBean = RespBean.build();
        List<TType> tType = tTypeMapper.getAllType();
        respBean.setStatus(200);
        respBean.setObj(tType);
        return respBean;
    }
}
