package com.example.bookbackend.controller;


import com.example.bookbackend.bean.Book;
import com.example.bookbackend.bean.Message;
import com.example.bookbackend.mapper.BookMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@EnableTransactionManagement  // 需要事务的时候加上
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 发布图书接口
     * @param book
     * @return
     */
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

    /**
     * 接收交易接口
     * @param bookId
     * @return
     */
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


    /**
     * 拒绝交易接口
     * @param bookId
     * @return
     */
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

    //各种查询接口
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
        Book book = bookMapper.selectWithComments(bookId);
        book.addImgPaths();//为web端设置图片列表
        return book;
    }

    @GetMapping("/selectBookList")
    public String selectBookList(HttpServletResponse response){
        return selectBookList(new Book(),response);
    }

    /**
     *复合查询接口
     * @param book 包含了Book类的复合条件
     * @return
     */
    @PostMapping("/selectBookList")
    public String selectBookList(@RequestBody(required = false) Book book, HttpServletResponse response){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        List<Book> books = bookMapper.selectBookList(book);

        String fileName = "book_data"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = { "图书ID", "书名", "分类", "简介"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (Book data: books) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(data.getBookId());
            row1.createCell(1).setCellValue(data.getName());
            row1.createCell(2).setCellValue(data.getType());
            row1.createCell(3).setCellValue(data.getIntroduction());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }catch (IOException e){
            return "导出失败"+e.getMessage();
        }
        return "导出成功";
    }

    /**
     * 下架图书接口
     * @param bookId
     * @return
     */
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
