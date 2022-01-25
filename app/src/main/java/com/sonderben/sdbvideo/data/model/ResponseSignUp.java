package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;
import java.util.List;

public class ResponseSignUp {
    Long id;
    String email;
    Profile mainProfile;

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

    public Profile getMainProfile() {
        return mainProfile;
    }

    public void setMainProfile(Profile mainProfile) {
        this.mainProfile = mainProfile;
    }

    public ResponseSignUp(Long id, String email, Profile mainProfile) {
        this.id = id;
        this.email = email;
        this.mainProfile = mainProfile;
    }
}
