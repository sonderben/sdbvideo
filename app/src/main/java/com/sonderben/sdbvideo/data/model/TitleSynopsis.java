package com.sonderben.sdbvideo.data.model;

public class TitleSynopsis {
    Long id;
    String title;
    String language;
    String synopsis;

    public TitleSynopsis(Long id, String title, String language, String synopsis) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.synopsis = synopsis;
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getLanguage() {
        return language;
    }
    public String getSynopsis() {
        return synopsis;
    }




}
