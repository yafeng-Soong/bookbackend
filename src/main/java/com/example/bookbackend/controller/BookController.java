package com.example.bookbackend.controller;


import com.example.bookbackend.bean.Book;
import com.example.bookbackend.bean.Message;
import com.example.bookbackend.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableTransactionManagement  // 需要事务的时候加上
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookMapper bookMapper;

    @PostMapping("/publish")
    @Transactional
    public Message insert(@RequestBody Book book){
        Integer bookId;
        try{
            System.out.println(book.getName());
            System.out.println(book.getType());
            bookMapper.insert(book);
            bookId = book.getBookId();
        }catch (Exception e){
            e.printStackTrace();
            return new Message("301","发布失败");
        }
        return new Message("302",bookId.toString());
    }

    @GetMapping("/selectByEmail")
    public List<Book> selectByEmail(@RequestParam("email") String email){
        return bookMapper.selectByEmail(email);
    }
    @GetMapping("/selectByName")
    public List<Book> selectByName(@RequestParam("name") String name){return bookMapper.selectByName("%"+name+"%");}
    @GetMapping("/selectByType")
    public List<Book> selectByType(@RequestParam("type") String type){return bookMapper.selectByType(type);}
    @GetMapping("/selectAll")
    public List<Book> selectAll(){return bookMapper.selectAll();}
    @GetMapping("/selectWithComments")
    public Book selectWithComments(@RequestParam("bookId") Integer bookId){
        return bookMapper.selectWithComments(bookId);
    }

}
