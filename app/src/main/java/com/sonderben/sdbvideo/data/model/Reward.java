package com.sonderben.sdbvideo.data.model;

import java.util.Calendar;

public class Reward {
    Long id;
    String name;
    String year;
    String donor;
    String Prize;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getPrize() {
        return Prize;
    }

    public void setPrize(String prize) {
        Prize = prize;
    }

    public Reward(Long id, String name, String year, String donor, String prize) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.donor = donor;
        Prize = prize;
    }
}
