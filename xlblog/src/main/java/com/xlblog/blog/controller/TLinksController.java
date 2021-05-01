package com.xlblog.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xlblog.blog.entity.TLinks;
import com.xlblog.blog.service.TLinksService;
import com.xlblog.blog.utils.RespBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/links")
public class TLinksController {
    @Autowired
    TLinksService tLinksService;

    RespBean respBean = RespBean.build();

    /**
     * 新增友链
     * @param tLinks
     * @return
     */
    @PostMapping("/saveLink")
    public RespBean saveLink(@RequestBody TLinks tLinks){
        tLinks.setCreateTime(LocalDateTime.now());
        Boolean result = tLinksService.save(tLinks);
        if (result){
            respBean.setStatus(200);
            respBean.setMsg("添加友链成功！");
            return respBean;
        }else {
            respBean.setStatus(500);
            respBean.setMsg("添加友链失败！");
            return respBean;
        }

    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/getAllLink")
    public RespBean getAllLink(){
        List<TLinks> linksList = tLinksService.list();
        return RespBean.ok("查询成功！",linksList);
    }

    /**
     * 友链的分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getLinksByPage")
    @ApiOperation("友链的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
    })
    public RespBean getLinksByPage(Long current, Long size){
        return tLinksService.pageLinks(current, size);
    }

    /**
     * 通过名称查找友链
     * @param name
     * @return
     */
    @GetMapping("/getLinksByName")
    public RespBean getLinksByName(String name){
        return tLinksService.getLinksByName(name);
    }

    /**
     * 更新友链
     * @param tLinks
     * @return
     */
    @PutMapping("/updateLink")
    public RespBean updateLink(@RequestBody TLinks tLinks){
        return tLinksService.updateLink(tLinks);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("deleteLinkById")
    public RespBean deleteLinkById(Long id){
        return tLinksService.deleteLinkById(id);
    }

    /**
     * 根据id查询友链
     * @param id
     * @return
     */
    @GetMapping("/getLinksById")
    public RespBean getLinksById(Long id){
        return tLinksService.getLinksById(id);
    }
}
