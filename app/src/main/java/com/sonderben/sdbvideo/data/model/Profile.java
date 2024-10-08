package com.sonderben.sdbvideo.data.model;

import java.util.List;

public class Profile {
    private Long id;
    private String name;
    private Boolean isMainProfile;
    private String urlImg;
    private String pin;
    private String defaultLanguage;
    private int ageCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMainProfile() {
        return isMainProfile;
    }

    public void setMainProfile(Boolean mainProfile) {
        isMainProfile = mainProfile;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Profile(Long id, String name, Boolean isMainProfile, String urlImg, String pin, String defaultLanguage, int ageCategory) {
        this.id = id;
        this.name = name;
        this.isMainProfile = isMainProfile;
        this.urlImg = urlImg;
        this.pin = pin;
        this.defaultLanguage = defaultLanguage;
        this.ageCategory = ageCategory;
    }
    public Profile (){

    }

    public class Image{
        String url;
        String name;
        String category;
        String size;

        public Image(String url, String name, String category, String size) {
            this.url = url;
            this.name = name;
            this.category = category;
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
