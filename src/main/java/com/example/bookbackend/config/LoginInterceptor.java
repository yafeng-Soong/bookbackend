package com.example.bookbackend.config;

import com.alibaba.fastjson.JSON;
import com.example.bookbackend.bean.Message;
import com.example.bookbackend.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //这里的User是登陆时放入session的
        User user = (User) session.getAttribute("user");
        String token = request.getHeader("token");
        //若是嗅探方法直接放行
        if(request.getMethod().equals("OPTIONS")) {
            logger.info("来自"+request.getRemoteAddr()+":"+request.getRemotePort()+"的嗅探请求"+request.getRequestURL()+"被放行");
            return true;
        }
        //如果请求未携带session或者携带的session与当前登录用户不符合则拦截
        if (user == null || token == null || !token.equals(user.getEmail())){
            //这个方法返回false表示忽略当前请求，如果一个用户调用了需要登陆才能使用的接口，如果他没有登陆这里会直接忽略掉
            //通过response给用户返回提示信息，告诉他没登陆
            Message msg =new Message("100","您还未登录！");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(msg));
            writer.close();
            logger.warn("来自"+request.getRemoteAddr()+":"+request.getRemotePort()+"的请求"+request.getRequestURL()+"被拦截");
            return false;
        }else {
            //如果session里有user，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
