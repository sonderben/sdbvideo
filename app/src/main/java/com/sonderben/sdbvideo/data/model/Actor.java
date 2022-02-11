package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;

public class Actor {
    Long id;
    String fullName;
    String birthday;
    String nationality;
    String sex;
    String photo;///url

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Actor(Long id, String fullName, String birthday, String nationality, String sex, String photo) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.nationality = nationality;
        this.sex = sex;
        this.photo = photo;
    }
}
