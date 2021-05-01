package com.xlblog.blog.entity;

import java.io.Serializable;

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
@ApiModel(value="TBlogTags对象", description="")
public class TBlogTags implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "博客id")
    private Long blogsId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "标签id")
    private Long tagsId;

    @TableField(exist = false)
    @ApiModelProperty(value = "博客对象")
    private TBlog tBlog;

    @TableField(exist = false)
    @ApiModelProperty(value = "标签对象")
    private TTag tTag;


}
