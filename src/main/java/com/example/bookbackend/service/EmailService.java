package com.example.bookbackend.service;

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

    public void sendTemplateMail(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject("验证");

            Context context = new Context();
            //context.setVariable("id", "wenter");
            context.setVariable("email",email);
            String emailContent = templateEngine.process("emailTemplate", context);
            helper.setText(emailContent, true);
        } catch (MessagingException e) {
            throw new RuntimeException("Messaging  Exception !", e);
        }
        javaMailSender.send(message);
    }
}
