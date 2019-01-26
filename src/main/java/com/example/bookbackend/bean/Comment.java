package com.example.bookbackend.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Comment {
    private int commentId;
    private String writerEmail;
    private Integer bookId;
    private String content;
    private Date date;
    private int good;
    private int bad;
    private User user;
    private Book book;

    public int getCommentId() {
        return commentId;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getContent() {
        return content;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getDate() {
        return date;
    }

    public int getGood() {
        return good;
    }

    public int getBad() {
        return bad;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

