package com.example.jkumar15.earthquakeSearch;

import java.util.Date;

public class Earthquake {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Earthquake(String title, String time, String url, Double mag, String lat, String lng) {
        this.title = title;
        this.time = new Date(Long.parseLong(time));
        this.url = url;
        this.mag = mag;
        this.lat = lat;
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private Date time;
    private String url;
    private double mag;
    private String lat;
    private String lng;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString(){
        return getTitle() + '\n' + getTime();
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
