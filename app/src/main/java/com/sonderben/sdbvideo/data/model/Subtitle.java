package com.sonderben.sdbvideo.data.model;

import java.io.Serializable;
import java.util.Calendar;

public class Subtitle implements Serializable {

    Long id;
    String language;
    String subtitle;
    String uploadDate;
    String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Subtitle(Long id, String language, String subtitle, String uploadDate, String author) {
        this.id = id;
        this.language = language;
        this.subtitle = subtitle;
        this.uploadDate = uploadDate;
        this.author = author;
    }
}
