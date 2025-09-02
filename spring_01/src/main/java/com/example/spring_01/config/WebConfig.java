package com.example.spring_01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.spring_01.interceptors.loginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private loginInterceptor loginInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //登录和注册接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/ads/login","/ads/register");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
