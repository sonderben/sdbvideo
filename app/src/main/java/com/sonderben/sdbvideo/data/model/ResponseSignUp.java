package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;
import java.util.List;

public class ResponseSignUp {
    Long id;
    String email;
    String firstName;
    String LastName;
    Calendar birthday;
    String telephone;
    String country;
    String region;
    String city;
    String department;
    String postalCode;
    String password;
    Calendar dateClientCreate;
    Access access;
    Boolean allProfilesCanCreateNewProfile;

    @Override
    public String toString() {
        return "ResponseSignUp{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", birthday=" + birthday +
                ", telephone='" + telephone + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", password='" + password + '\'' +
                ", dateClientCreate=" + dateClientCreate +
                ", access=" + access +
                ", allProfilesCanCreateNewProfile=" + allProfilesCanCreateNewProfile +
                '}';
    }
}
