package com.xlblog.blog.service.impl;

import com.xlblog.blog.entity.TUser;
import com.xlblog.blog.mapper.TUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomUserServiceImpl implements UserDetailsService {
    @Resource
    TUserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.loadUserByUsername(s);
        if (s == null){  //如果用户名不存在，就抛出下面的信息
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //用户存在的话就返回hr对象
        return tUser;
    }
}
