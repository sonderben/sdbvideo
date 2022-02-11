package com.sonderben.sdbvideo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class Video implements Serializable {

    Long id;
    private Long idVideo;
    private Boolean isMovie;
    private String releaseDate;
    private String poster;
    private String trailer;
    private Integer duration;
    private Long currentPlayingTime;
    private String description;
    private int ageCategory;
    private List<String> categories;
    private Long idEpisode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Long idVideo) {
        this.idVideo = idVideo;
    }

    public Boolean isMovie() {
        return isMovie;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getCurrentPlayingTime() {
        return currentPlayingTime;
    }

    public void setCurrentPlayingTime(Long currentPlayingTime) {
        this.currentPlayingTime = currentPlayingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Long getIdEpisode() {
        return idEpisode;
    }

    public void setIdEpisode(Long idEpisode) {
        this.idEpisode = idEpisode;
    }

    public Video(Long id, Long idVideo, Boolean isMovie, String releaseDate, String poster, String trailer, Integer duration, Long currentPlayingTime, String description, int ageCategory, List<String> categories, Long idEpisode) {
        this.id = id;
        this.idVideo = idVideo;
        this.isMovie = isMovie;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.trailer = trailer;
        this.duration = duration;
        this.currentPlayingTime = currentPlayingTime;
        this.description = description;
        this.ageCategory = ageCategory;
        this.categories = categories;
        this.idEpisode = idEpisode;
    }
}
