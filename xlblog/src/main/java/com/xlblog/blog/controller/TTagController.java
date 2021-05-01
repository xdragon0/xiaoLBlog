package com.xlblog.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xlblog.blog.entity.TTag;
import com.xlblog.blog.entity.TType;
import com.xlblog.blog.service.TTagService;
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
@RequestMapping("/tag")
public class TTagController {

    @Autowired
    TTagService tTagService;

    RespBean respBean = RespBean.build();

    @PostMapping("/saveTag")
    public RespBean saveTag(@RequestBody TTag tTag){
        if (StringUtils.isEmpty(tTag.getName())){
            respBean.setStatus(500);
            respBean.setMsg("标签名称不能为空！");
            return respBean;
        }
        QueryWrapper<TTag> queryWrapper = new QueryWrapper<TTag>();
        queryWrapper.eq("name",tTag.getName());
        List<TTag> tagList = tTagService.list(queryWrapper);
        if (tagList.size() > 0){
            respBean.setStatus(500);
            respBean.setMsg("添加失败，已有该标签名称");
            return respBean;
        }
        Boolean result = tTagService.save(tTag);
        if (result){
            respBean.setStatus(200);
            respBean.setMsg("添加标签成功！");
            return respBean;
        }else {
            respBean.setStatus(500);
            respBean.setMsg("添加标签失败！");
            return respBean;
        }

    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping("/getAllTag")
    public RespBean getAllTag(){
        return tTagService.getAllTag();
    }



    /**
     * 标签的分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getTagByPage")
    @ApiOperation("标签的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
    })
    public RespBean getTagByPage(Long current, Long size){
        return tTagService.pageTags(current, size);
    }

    /**
     * 通过名称查找标签
     * @param name
     * @return
     */
    @GetMapping("/getTagByName")
    public RespBean getTagByName(String name){
        return tTagService.getTagByName(name);
    }

    /**
     * 更新标签
     * @param tTag
     * @return
     */
    @PutMapping("/updateTag")
    public RespBean updateTag(@RequestBody TTag tTag){
        if (StringUtils.isEmpty(tTag.getName())){
            respBean.setMsg("分类名称不能为空");
            return respBean;
        }
        return tTagService.updateTag(tTag);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("deleteTagById")
    public RespBean deleteTagById(Long id){
        return tTagService.deleteTagById(id);
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @GetMapping("/getTagById")
    public RespBean getTagById(Long id){
        return tTagService.getTagById(id);
    }
}
