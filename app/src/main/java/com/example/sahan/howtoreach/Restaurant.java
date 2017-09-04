package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/4/2017.
 */


public class Restaurant {

    private String restName;
    private String venue;
    private String restDate;

    public Restaurant() {
    }

    public Restaurant(String restName, String venue, String restDate) {
        this.restName = restName;
        this.venue = venue;
        this.restDate = restDate;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getRestDate() {
        return restDate;
    }

    public void setRestDate(String restDate) {
        this.restDate = restDate;
    }
}
