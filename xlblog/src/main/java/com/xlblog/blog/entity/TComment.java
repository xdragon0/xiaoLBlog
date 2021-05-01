package com.xlblog.blog.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="TComment对象", description="")
public class TComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "评论id")
    private Long id;

    @ApiModelProperty(value = "是否为管理员评论")
    private Boolean adminComment;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "博客id")
    private Long blogId;

    @JsonSerialize(using = ToStringSerializer.class) //系统序列化时，保留相关精度
    @ApiModelProperty(value = "父评论id")
    private Long parentCommentId;

    //回复评论
    @TableField(exist = false) //查询时过滤非数据库字段
    private List<TComment> replyComments = new ArrayList<>();
    @TableField(exist = false) //查询时过滤非数据库字段
    private TComment parentComment;
    @TableField(exist = false) //查询时过滤非数据库字段
    private String parentNickname;

}
