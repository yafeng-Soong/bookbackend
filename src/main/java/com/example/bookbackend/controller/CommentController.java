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

    @PostMapping("/insertComment")
    public int insert(@RequestBody Comment comment){
        commentMapper.insert(comment);
        return comment.getCommentId();
    }
}
