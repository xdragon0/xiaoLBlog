package com.xlblog.blog.form;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xlblog.blog.utils.RespBean;

import java.util.HashMap;
import java.util.List;

/**
 * 表单校验
 */
public class BlogForm {
    public static RespBean check(HashMap<String,Object> params) {
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String first_picture = (String) params.get("first_picture");
        String content = (String) params.get("content");
        List<String> tags = (List) params.get("tags");
        Long type_id = Long.parseLong(params.get("type_id").toString());
        String flag = (String) params.get("flag");
//        Boolean published = (Boolean) params.get("published");
        RespBean info = new RespBean();
        if (StringUtils.isEmpty(title)) {
            info.setStatus(500);
            info.setMsg("title 不能为空");
        }else if (StringUtils.isEmpty(description)) {
            info.setStatus(500);
            info.setMsg("description 不能为空");
        }else if (StringUtils.isEmpty(first_picture)) {
            info.setStatus(500);
            info.setMsg("first_picture 不能为空");
        }else if (StringUtils.isEmpty(content)) {
            info.setStatus(500);
            info.setMsg("content 不能为空");
        }else if (tags.size() == 0) {
            info.setStatus(500);
            info.setMsg("tags 不能为空");
        }else if (type_id == null) {
            info.setStatus(500);
            info.setMsg("type_id 不能为空");
        }else if (StringUtils.isEmpty(flag)) {
            info.setStatus(500);
            info.setMsg("flag 不能为空");
        }else {
            info.setMsg("校验成功");
            info.setStatus(200);
        }
        return info;
    }

    public static RespBean checkTemporaryBlog(HashMap<String,Object> params) {
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String first_picture = (String) params.get("first_picture");
        String content = (String) params.get("content");
        RespBean info = new RespBean();
        if (StringUtils.isEmpty(title)) {
            info.setStatus(500);
            info.setMsg("title 不能为空");
        }else if (StringUtils.isEmpty(description)) {
            info.setStatus(500);
            info.setMsg("description 不能为空");
        }else if (StringUtils.isEmpty(first_picture)) {
            info.setStatus(500);
            info.setMsg("first_picture 不能为空");
        }else if (StringUtils.isEmpty(content)) {
            info.setStatus(500);
            info.setMsg("content 不能为空");
        }
        return info;
    }
}
