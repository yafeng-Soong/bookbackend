package com.example.bookbackend.controller;


import com.example.bookbackend.bean.Comment;
import com.example.bookbackend.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableTransactionManagement  // 需要事务的时候加上
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/selectComments")
    public List<Comment> selectComments(@RequestParam("bookId") Integer bookId){return commentMapper.selectByBookId(bookId);}

    @PostMapping("/insert")
    public int insert(@RequestBody Comment comment){
        System.out.println(comment.getWriterEmail());
        commentMapper.insert(comment);
        return comment.getCommentId();
    }

    @GetMapping("/myComments")
    public List<Comment> selectByEmial(@RequestParam("email") String email){
        return commentMapper.selectMyComments(email);
    }
}
