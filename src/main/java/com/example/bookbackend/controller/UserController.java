package com.example.bookbackend.controller;

import com.example.bookbackend.mapper.UserMapper;
import com.example.bookbackend.bean.Message;
import com.example.bookbackend.bean.User;
import com.example.bookbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@EnableTransactionManagement  // 需要事务的时候加上
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

    public String sendMail(String email,String template,String subject)throws SendFailedException{
        try {
            emailService.sendTemplateMail(email,template,subject);
        }catch (Exception e){
            throw new SendFailedException();//发送失败抛出异常
        }
        return "发送成功";
    }

    @PostMapping(path="/signUp")
    @Transactional(rollbackFor = Exception.class)//事物回滚
    public Message insert(@RequestBody User user){
        //Message message = new Message("105","注册成功！");
        try {
            String subject = "尊敬的" + user.getName() + "!请激活您的账号";
            userMapper.insert(user);
            sendMail(user.getEmail(), "emailTemplate", subject);
        }catch (DataAccessException e){
            e.printStackTrace();
            return new Message("111","该邮箱已被注册！");
        }catch (SendFailedException e){
            e.printStackTrace();
            return new Message("107","无效邮箱！");
        }
//        }catch (DuplicateKeyException duplicateKeyException){
//            return new Message("106","该邮箱已被注册！");
//        }catch (SendFailedException e){
//            System.out.println(e.getMessage());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事物
//            return new Message("107","无效邮箱！");
//        }
        return new Message("105","注册成功！");
    }

    @GetMapping("/reset")
    public Message sendResetMail(@RequestParam("email") String email){
        try {
            sendMail(email,"resetTemplate","重置密码");
        }catch (SendFailedException e){
            return new Message("107","无效邮箱！");
        }
        return new Message("110","请前往邮箱修改密码！");
    }

    @PostMapping("/reset")
    public Message resetPwd(@RequestParam("email") String email,@RequestParam("pwd") String pwd){
        try{
            userMapper.resetPwd(email,pwd);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("109","更新失败");
        }
        return new Message("108","更新成功");
    }

    @GetMapping("/selectAll")
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
    @GetMapping("/selectByEmail")
    public User selectByEmail(@RequestParam("email") String email){
        return userMapper.selectByEmail(email);
    }

    @GetMapping("/activate")
    public String active(@RequestParam("email") String email){
        if(userMapper.activate(email)==1){
            return "success";
        }else return "false";
    }

    @GetMapping("/is_login")
    public Message is_login(){
        return new Message("103","已经登录！");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "logout!";
    }

    @PostMapping("/login")
    public Message login(@RequestParam("email") String email, @RequestParam("pwd") String pwd, HttpServletRequest request){
        User user = userMapper.selectByEmail(email);
        if (user == null){
            //若user为空表示没有查到该用户
            return new Message("101","不存在该用户！");
        }else if (user.getState() == 0){
            return new Message("102","该账号还未激活！");
        }else if (pwd.equals(user.getPwd())){
            request.getSession().setAttribute("user",user);
            request.getSession().setMaxInactiveInterval(259200);//没有活动3天后，session将失效
            return new Message("103","登录成功！");
        }else {
            return new Message("104","密码错误！");
        }
    }

    @PostMapping("/updateInfo")
    public Message updateInfo(@RequestBody User user){
        try{
            System.out.println(user.getName());
            userMapper.updateInfo(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("109","更新失败！");
        }
        return new Message("108","更新成功！");
    }

}