package com.xlblog.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.entity.TType;
import com.xlblog.blog.service.TTypeService;
import com.xlblog.blog.utils.RespBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/type")
public class TTypeController {

    @Autowired
    TTypeService tTypeService;

    RespBean respBean = RespBean.build();

    /**
     * 保存分类专栏
     * @param tType
     * @return
     */
    @PostMapping("/saveType")
    public RespBean saveType(@RequestBody TType tType){
        if (StringUtils.isEmpty(tType.getName())){
            respBean.setStatus(500);
            respBean.setMsg("分类专栏名称不能为空！");
            return respBean;
        }
        QueryWrapper<TType> queryWrapper = new QueryWrapper<TType>();
        queryWrapper.eq("name",tType.getName());
        List<TType> tBlogList = tTypeService.list(queryWrapper);
        if (tBlogList.size() > 0){
            respBean.setStatus(500);
            respBean.setMsg("添加失败，已有该分类专栏名称");
            return respBean;
        }
        Boolean result = tTypeService.save(tType);
        if (result){
            respBean.setStatus(200);
            respBean.setMsg("添加成功！");
            return respBean;
        }else {
            respBean.setStatus(500);
            respBean.setMsg("添加失败！");
            return respBean;
        }

    }

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping("/getAllType")
    public RespBean getAllType(){
//        List<TType> typeList = tTypeService.list();
        return tTypeService.getAllType();
    }

    /**
     * 分类的分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getTypeByPage")
    @ApiOperation("分类的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
    })
    public RespBean getByPage(Long current, Long size){
        return tTypeService.pageTypes(current, size);
    }

    /**
     * 通过名称查找分类
     * @param name
     * @return
     */
    @GetMapping("/getTypeByName")
    public RespBean getTypeByName(String name){
        return tTypeService.getTypeByName(name);
    }

    /**
     * 更新分类
     * @param tType
     * @return
     */
    @PutMapping("/updateType")
    public RespBean updateType(@RequestBody TType tType){
        if (StringUtils.isEmpty(tType.getName())){
            respBean.setMsg("分类名称不能为空");
            return respBean;
        }
        return tTypeService.updateType(tType);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("deleteTypeById")
    public RespBean deleteTypeById(Long id){
        return tTypeService.deleteTypeById(id);
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/getTypeById")
    public RespBean getTypeById(Long id){
        return tTypeService.getTypeById(id);
    }

}
