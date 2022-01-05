package com.sonderben.sdbvideo.entity;




public class Episode {
    private String url_poster;
    private int num;
    private long duration;
    private Long currentPlayingTime;
    private String synopsis;
    private String title;

    public String getUrl_poster() {
        return url_poster;
    }
    public String getTitle() {
        return num+". "+title;
    }

    public void setUrl_poster(String url_poster) {
        this.url_poster = url_poster;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Long getCurrentPlayingTime() {
        return currentPlayingTime;
    }

    public void setCurrentPlayingTime(Long currentPlayingTime) {
        this.currentPlayingTime = currentPlayingTime;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Episode(String title,String url_poster, int num, long duration, Long currentPlayingTime, String synopsis) {
        this.title=title;
        this.url_poster = url_poster;
        this.num = num;
        this.duration = duration;
        this.currentPlayingTime = currentPlayingTime;
        this.synopsis = synopsis;
    }
}
