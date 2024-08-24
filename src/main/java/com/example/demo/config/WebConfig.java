package com.example.demo.config;

import com.example.demo.interceptors.LoginInterceptoer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptoer loginInterceptoer;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登陆和注册不拦截
        registry.addInterceptor( loginInterceptoer).excludePathPatterns("/user/login","/user/register");
    }
}
