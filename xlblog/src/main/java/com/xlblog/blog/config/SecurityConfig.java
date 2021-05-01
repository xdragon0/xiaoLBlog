package com.xlblog.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xlblog.blog.entity.TUser;
import com.xlblog.blog.service.impl.CustomUserServiceImpl;
import com.xlblog.blog.utils.RespBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    CustomUserServiceImpl customUserService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //要有一个configure方法吧hrService整进来
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/blog/vuefindByPage","/blog/getByBlogId",
                           "/type/getAllType","/tag/getAllTag","/blog/getByTitle",
                           "/comment/comments/{blogId}","/comment/comments",
                            "/blog/countBlog","/comment/getCommentByPage",
                            "/links/getAllLink","/blog/vuefindHotBlog").permitAll()
//                .antMatchers("/blog/getByBlogId").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        //如果登录成功就返回一段json
                        resp.setContentType("application/json;charset=utf-8");
                        //这是往出写的
                        PrintWriter out = resp.getWriter();
                        //登录成功的hr对象
                        TUser user = (TUser)authentication.getPrincipal();
                        //为了安全，这里返回给前端时将密码设置为null
                        user.setPassword(null);
                        RespBean ok = RespBean.ok("登录成功！",user);
                        //把Hr写成字符串
                        String s = new ObjectMapper().writeValueAsString(ok);
                        //把字符串写出去
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        //如果登录失败也返回一段json
                        resp.setContentType("application/json;charset=utf-8");
                        //设置状态码
                        resp.setStatus(401);
                        //这是往出写的
                        PrintWriter out = resp.getWriter();
                        RespBean respBean = RespBean.error("登录失败！");
                        if(exception instanceof LockedException){
                            respBean.setMsg("账户被锁定，请联系管理员！");
                        }else if (exception instanceof CredentialsExpiredException){
                            respBean.setMsg("密码过期，请联系管理员！");
                        }else if (exception instanceof DisabledException){
                            respBean.setMsg("账户被禁用，请联系管理员！");
                        }else if (exception instanceof BadCredentialsException){
                            respBean.setMsg("用户名或者密码输入错误，请重新输入！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();

                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
                                                Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功！")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }
}
