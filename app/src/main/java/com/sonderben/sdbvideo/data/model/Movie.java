package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class Movie {
    Long id;
    List<TitleSynopsis> titleSynopses;
    String availability;
    String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TitleSynopsis> getTitleSynopses() {
        return titleSynopses;
    }

    public void setTitleSynopses(List<TitleSynopsis> titleSynopses) {
        this.titleSynopses = titleSynopses;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Subtitle> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<Subtitle> subtitles) {
        this.subtitles = subtitles;
    }

    List<Reward> rewards;

    public Movie(Long id, List<TitleSynopsis> titleSynopses, String availability, String url, List<Reward> rewards, List<Actor> actors, List<Subtitle> subtitles) {
        this.id = id;
        this.titleSynopses = titleSynopses;
        this.availability = availability;
        this.url = url;
        this.rewards = rewards;
        this.actors = actors;
        this.subtitles = subtitles;
    }

    List<Actor>actors;
    List<Subtitle> subtitles;
}
