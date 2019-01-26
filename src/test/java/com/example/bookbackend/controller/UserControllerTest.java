package com.example.bookbackend.controller;

import com.example.bookbackend.bean.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;//发送请求的容器
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
        User user =new User();
        session.setAttribute("user",user); //拦截器那边会判断用户是否登录，所以这里注入一个用户
    }

    @Test
    public void signUpTest() throws Exception{
        String body = "{\"name\":\"阿峰\",\"email\":\"397655952@qq.com\",\"pwd\":\"123456\"}";
        mvc.perform(MockMvcRequestBuilders.post("/user/signUp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(body.getBytes())//传json参数
        )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("103"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * post()指定方法和路径
     * contentType()指定发送报文格式
     * accept()指定接收报文格式
     * content()指定内容
     * session()指定session
     * @throws Exception 必须抛出一个异常
     */
    @Test
    public void updateTest() throws Exception{
        String body = "{\"email\":\"397655952@qq.com\",\"name\":\"啊哈\"}";
        mvc.perform(MockMvcRequestBuilders
                        .post("/user/updateInfo")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(body.getBytes())
                        .session(session)
        ).andDo(MockMvcResultHandlers.print());
    }
}