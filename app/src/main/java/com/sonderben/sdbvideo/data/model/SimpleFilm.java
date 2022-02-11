package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;
import java.util.Set;

public class SimpleFilm {
    private Long id;
    private Set<TitleSynopsis> title;
    private String releaseDate;
    private String posterUrlMovie;
    private String trailerUrlMovie;
    private Integer duration;
    Long currentPlayingTime;

    @Override
    public String toString() {
        return "SimpleFilm{" +
                "id=" + id +
                ", title=" + title +
                ", releaseDate=" + releaseDate +
                ", posterUrlMovie='" + posterUrlMovie + '\'' +
                ", trailerUrlMovie='" + trailerUrlMovie + '\'' +
                ", duration=" + duration +
                ", currentPlayingTime=" + currentPlayingTime +
                '}';
    }

    public SimpleFilm(Long id, Set<TitleSynopsis> title, String releaseDate,
                      String posterUrlMovie, String trailerUrlMovie, Integer duration,
                      Long currentPlayingTime) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterUrlMovie = posterUrlMovie;
        this.trailerUrlMovie = trailerUrlMovie;
        this.duration = duration;
        this.currentPlayingTime = currentPlayingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TitleSynopsis> getTitle() {
        return title;
    }

    public void setTitle(Set<TitleSynopsis> title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrlMovie() {
        return posterUrlMovie;
    }

    public void setPosterUrlMovie(String posterUrlMovie) {
        this.posterUrlMovie = posterUrlMovie;
    }

    public String getTrailerUrlMovie() {
        return trailerUrlMovie;
    }

    public void setTrailerUrlMovie(String trailerUrlMovie) {
        this.trailerUrlMovie = trailerUrlMovie;
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

    public class TitleSynopsis{

        Long id;
        String title;
        String language;
        String Synopsis;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getSynopsis() {
            return Synopsis;
        }

        public void setSynopsis(String synopsis) {
            Synopsis = synopsis;
        }

        public TitleSynopsis(Long id, String title, String language, String synopsis) {
            this.id = id;
            this.title = title;
            this.language = language;
            Synopsis = synopsis;
        }
    }
}
