package com.example.bookbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class LoginConfigurer implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("addInterceptors method is running !");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/signUp"
                ,"/user/login","/book/select**","/user/activate","/user/reset","/page/reset");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/book/selectByEmail");
    }
}
