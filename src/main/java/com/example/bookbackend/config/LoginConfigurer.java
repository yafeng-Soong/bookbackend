package com.example.bookbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class LoginConfigurer implements WebMvcConfigurer {

    @Autowired//自动注入一个拦截器对象
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("addInterceptors method is running !");
        /**
         * 在addPathPatterns方法添加需要拦截的路径
         * 由于需要拦截的路径较多，所以在addPathPatterns方法添加全部路径
         * 在excludePathPatterns方法中添加不用拦截的路径
         **/
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/user/signUp"
                ,"/user/login","/book/select**",
                "/user/activate","/user/reset",
                "/page/reset","/error");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/book/selectByEmail");
    }
}



