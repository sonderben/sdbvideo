package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;

public class User {
    Access access;
    boolean allProfilesCanCreateNewProfile;
    String email;
    String firstName;
    String lastName;
    Calendar birthday;
    String telephone;
    String country;
    String region;
    String city;
    String department;
    String postalCode;
    String password;
    String sex;

    public Access getAccess() {
        return access;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User(){
        allProfilesCanCreateNewProfile=false;
        //birthday=C;
        region="";
    }


    public Access geAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public boolean isAllProfilesCanCreateNewProfile() {
        return allProfilesCanCreateNewProfile;
    }

    public void setAllProfilesCanCreateNewProfile(boolean allProfilesCanCreateNewProfile) {
        this.allProfilesCanCreateNewProfile = allProfilesCanCreateNewProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                ", access=" + access +
                ", allProfilesCanCreateNewProfile=" + allProfilesCanCreateNewProfile +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", telephone='" + telephone + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User( Access access, boolean allProfilesCanCreateNewProfile, String email,
                String firstName, String lastName, Calendar birthday, String telephone,
                String country, String region, String city, String department, String postalCode,
                String password, String sex) {

        this.access = access;
        this.allProfilesCanCreateNewProfile = allProfilesCanCreateNewProfile;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.telephone = telephone;
        this.country = country;
        this.region = region;
        this.city = city;
        this.department = department;
        this.postalCode = postalCode;
        this.password = password;
        this.sex = sex;
    }
}
