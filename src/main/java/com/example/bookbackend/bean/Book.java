package com.example.bookbackend.bean;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class Book {
    private Integer bookId;
    private String email;//拥有者邮箱
    private String name;//图书名字
    private String type;//图书类型
    private int state;//图书状态
    private String introduction;//简介
    private Date date;//发布日期
    private String imgPath;
    private String imgPath2;
    private String imgPath3;
    private int good;//喜欢收藏数
    private int bad;//不喜欢数
    private Timestamp udDate;//修改时间

    private List<Comment> comments;

    public Integer getBookId() {
        return bookId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Date getDate() {
        return date;
    }
    public String getImgPath() {
        return imgPath;
    }

    public String getImgPath2() {
        return imgPath2;
    }

    public String getImgPath3() {
        return imgPath3;
    }

    public int getGood() {
        return good;
    }

    public int getBad() {
        return bad;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Timestamp getUdDate() {
        return udDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
