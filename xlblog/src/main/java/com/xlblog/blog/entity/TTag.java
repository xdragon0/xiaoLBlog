package com.xlblog.blog.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TTag对象", description="")
public class TTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "名称")
    private String name;

    @TableField(exist = false)  //查询时过滤非数据库字段
    @ApiModelProperty(value = "博客标签集合")
    private List<TBlogTags> tBlogTags;

    @TableField(exist = false)  //查询时过滤非数据库字段
    private Integer countTag;



}
