package com.xlblog.blog.entity;

import java.io.Serializable;
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
@ApiModel(value="TContact对象", description="")
public class TContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信支付二维码")
    private String payByWechat;

    @ApiModelProperty(value = "支付宝二维码")
    private String payByZhi;

    @ApiModelProperty(value = "qq账号")
    private String qq;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "微信二维码")
    private String wechatCode;


}
