package com.example.bookbackend.bean;

import java.util.List;

public class User {
    private Integer userId;
    private String name;
    private String email;
    private String pwd;
    private String headPath;
    private Integer state;
    private float grade;
    private String signature;
    private String sex;

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getHeadPath() {
        return headPath;
    }

    public Integer getState() {
        return state;
    }

    public float getGrade() {
        return grade;
    }

    public String getSignature() {
        return signature;
    }

    public String getSex() {
        return sex;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
