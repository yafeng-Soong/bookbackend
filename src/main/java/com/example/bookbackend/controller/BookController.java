package com.example.bookbackend.controller;


import com.example.bookbackend.bean.Book;
import com.example.bookbackend.bean.Message;
import com.example.bookbackend.mapper.BookMapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 图书交换接口
     * @param bookId
     * @param changer
     * @param changeId
     * @return 成功信息
     */
    @GetMapping("/change")
    public Message changeBook(@RequestParam("bookId") Integer bookId,
                              @RequestParam("changer") String changer,
                              @RequestParam("changeId") Integer changeId){
        try {
            bookMapper.changeBook(bookId, changer,changeId,2);
            String owner = bookMapper.selectOwner(bookId);
            bookMapper.changeBook(changeId, owner, bookId,3);
        }catch (Exception e){
            e.getMessage();
            return new Message("400","交易出错");
        }
        return new Message("401","发起交易成功，等待对方回应！");
    }

    @GetMapping("/confirm")
    public Message confirmChange(@RequestParam("bookId") Integer bookId){
        try {
            bookMapper.confirmChange(bookId);
            Integer changeId = bookMapper.selectChangeID(bookId);
            bookMapper.confirmChange(changeId);
        }catch (Exception e){
            e.getMessage();
            return new Message("400","交易出错");
        }
        return new Message("402","交易成功");
    }


    @GetMapping("/refuse")
    public Message refuseChange(@RequestParam("bookId") Integer bookId){
        try {
            bookMapper.refuseChange(bookId);
            Integer changeId = bookMapper.selectChangeID(bookId);
            bookMapper.refuseChange(changeId);
        }catch (Exception e){
            e.getMessage();
            return new Message("400","交易出错");
        }
        return new Message("403","拒绝成功");
    }

    @GetMapping("/selectChanges")
    public List<Book> selectChanges(@RequestParam("email") String email){
        return bookMapper.selectChanges(email);
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

    @GetMapping("/delete")
    public Message delete(@RequestParam("bookId") Integer bookId){
        try{
            bookMapper.deleteById(bookId);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("501","下架失败！");
        }
        return new Message("502","下架成功！");
    }

}
