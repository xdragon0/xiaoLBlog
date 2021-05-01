package com.xlblog.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Tcount {
    private static final long serialVersionUID = 1L;
    private Integer blogCount;
    private Integer viewCount;
    private Long commentCount;
    private Long messageCount;
}
