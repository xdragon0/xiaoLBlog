package com.xlblog.blog.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value="TBlog对象", description="")
//@TableName(value = "t_blog" ,resultMap = "MyBlogMap")
public class TBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "博客id")
    private Long id;

//    @ApiModelProperty(value = "是否开启赞赏")
//    private Boolean appreciation;
//
//    @ApiModelProperty(value = "是否开启评论")
//    private Boolean commentabled;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime ;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "首图地址")
    private String firstPicture;

    @ApiModelProperty(value = "原创")
    private String flag;

    @ApiModelProperty(value = "是否公开")
    private Boolean published;

//    @ApiModelProperty(value = "是否可以回复")
//    private Boolean recommend;

    @ApiModelProperty(value = "草稿")
    private Boolean shareStatement;

    @ApiModelProperty(value = "标题")
    private String title;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "分类id")
    private Long typeId;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "评论次数")
    private Long commentCount;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDelete;

    @TableField(exist = false)  //查询时过滤非数据库字段
    @ApiModelProperty(value = "博客标签集合")
    private List<TBlogTags> tBlogTags;

    @TableField(exist = false) //查询时过滤非数据库字段
    @ApiModelProperty(value = "分类专栏对象")
    private TType tType;

    @TableField(exist = false) //查询时过滤非数据库字段
    @ApiModelProperty(value = "用户对象")
    private TUser tUser;


}
