package com.example.bookbackend.controller;

import com.example.bookbackend.mapper.UserMapper;
import com.example.bookbackend.model.Message;
import com.example.bookbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
@ResponseBody
public class FileController {

    @Autowired
    private UserMapper userMapper;

    //@ResponseBody
    @PostMapping("/heads")
    public Message uploadHead(@RequestParam("file") MultipartFile file,@RequestParam("email") String email) {
        String contentType = file.getContentType();
        //System.out.println(contentType);
        String filename = email+"."+contentType.split("/")[1];
        String filePath = "/imgs/heads/";
        try {
            file.transferTo(new File(filePath+filename));
            User user = new User();
            user.setPath(filePath+filename);
            user.setEmail(email);
            userMapper.updateHead(user);
            return new Message("201","上传成功");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message("202","上传失败");
    }

    @PostMapping("/books")
    public Message uploadBook(@RequestParam("file") MultipartFile file,@RequestParam("bookId") Integer id){

        String filename = file.getOriginalFilename();
        String filePath = "/imgs/books/"+id.toString()+"/";
        File path = new File(filePath);
        if (!path.exists()){
            path.mkdir();
        }
        try {
            file.transferTo(new File(filePath+filename));
            return new Message("201","上传成功");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message("202","上传失败");
    }

}
