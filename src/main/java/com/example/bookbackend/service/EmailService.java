package com.example.bookbackend.service;

import com.example.bookbackend.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
    private String sender;

    private static final String recipient = "397655952@qq.com" ;

    /**
     * 发送简单文本邮件
     */
    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者
        message.setFrom(sender);
        // 接收者
        message.setTo(recipient);
        //邮件主题
        message.setSubject("主题：文本邮件");
        // 邮件内容
        message.setText("骚扰邮件勿回");
        javaMailSender.send(message);
    }

    public void sendHtmlEmail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("主题：HTML邮件");
            StringBuffer sb = new StringBuffer();
            sb.append("<h1>大标题-h1</h1>")
                    .append("<p style='color:#F00'>红色字</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            helper.setText(sb.toString(), true);
        } catch (MessagingException e) {
            throw new RuntimeException("Messaging  Exception !", e);
        }
        javaMailSender.send(message);
    }

    /**
     * 发送模板邮件
     * @param email 接收方的email
     * @param template 模板的名称，有激活模板和找回密码两种
     * @param subject 邮件主题
     */

    public void sendTemplateMail(String email,String template,String subject) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //为了防止接收方丢入垃圾箱，可以在header里加上Outlook
            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //指定源、目的地、主题
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(subject);

            Context context = new Context();
            //设置模板中的变量
            context.setVariable("email",email);
            //指定template模板
            String emailContent = templateEngine.process(template, context);
            helper.setText(emailContent, true);
        } catch (MessagingException e) {
            throw new RuntimeException("Messaging  Exception !", e);
        }
        javaMailSender.send(message);
    }
}

