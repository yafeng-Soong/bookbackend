package com.example.bookbackend.model;

public class User {
    private Integer userId;
    private String name;
    private String email;
    private String pwd;
    //private String place;
    private String headPath;
    private Integer state;
    private float grade;

    public Integer getId() {
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

//    public String getPlace() {
//        return place;
//    }

    public String getPath() {
        return headPath;
    }

    public Integer getState() {
        return state;
    }

    public float getGrade() {
        return grade;
    }

    public void setId(Integer id) {
        this.userId = id;
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

//    public void setPlace(String place) {
//        this.place = place;
//    }

    public void setPath(String path) {
        this.headPath = path;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
