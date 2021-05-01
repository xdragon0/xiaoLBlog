//package com.xlblog.blog.utils;
//
//import com.xlblog.blog.entity.TBlog;
//import com.xlblog.blog.service.TBlogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@Configurable
//@EnableScheduling
//public class blogQuartz {
//
//    @Autowired
//    RedisTemplate redisTemplate;
//    @Autowired
//    TBlogService tBlogService;
//
//    /**
//     * 访问量定时器
//     */
//    @Scheduled(cron = "0 0 * * * ?")
//    public void blog(){
//        List<TBlog> blogLsit = tBlogService.list();
//        ValueOperations<String,TBlog> operations = redisTemplate.opsForValue();
//        for (TBlog blog: blogLsit ) {
//            if(blog.getId() != -1){
//                String key = "blog_" + blog.getId();
//                if (redisTemplate.hasKey(key)){
//                    //获取该缓存数据
//                    TBlog tBlog = operations.get(key);
//                    tBlogService.updateById(tBlog);
//                    System.out.println("更新访问量->数据库");
//                }
//
//            }
//
//        }
//
//
//
//    }
//}
