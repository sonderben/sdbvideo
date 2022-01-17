package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;
import java.util.List;

public class ResponseSignUp {
    Long id;
    String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResponseSignUp{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResponseSignUp(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
