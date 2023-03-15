package com.reggie.reggietakeoutapi.config;

import com.reggie.reggietakeoutapi.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig  extends WebMvcConfigurationSupport {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        // 跨域
        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).allowedMethods("GET","POST","PUT");
    }
    // 拦截器 - 用户鉴权
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/employee/login");
    }
}
