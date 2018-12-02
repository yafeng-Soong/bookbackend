package com.example.bookbackend.bean;

import java.util.Date;

public class Comment {
    private int commentId;
    private String writerEmail;
    private String bookId;
    private String content;
    private Date date;
    private int good;
    private int bad;

    public int getCommentId() {
        return commentId;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public String getBookId() {
        return bookId;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getGood() {
        return good;
    }

    public int getBad() {
        return bad;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public void setBookId(String bookId) {
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
}

