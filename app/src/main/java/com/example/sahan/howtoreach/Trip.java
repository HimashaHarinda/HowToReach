package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 8/30/2017.
 */

public class Trip {
    private String destination;
    private String startDate;
    private String endDate;
    private String tripName;
    private String description;
    private String postedUserId;
    private String postedUserName;
    private String addedDate;
    Plan plan = new Plan();

    public Trip() {
    }

    public Trip(String destination, String startDate, String endDate, String tripName, String description, String postedUserId, String postedUserName, String addedDate, Plan plan) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripName = tripName;
        this.description = description;
        this.postedUserId = postedUserId;
        this.postedUserName = postedUserName;
        this.addedDate = addedDate;
        this.plan = plan;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedUserId() {
        return postedUserId;
    }

    public void setPostedUserId(String postedUserId) {
        this.postedUserId = postedUserId;
    }

    public String getPostedUserName() {
        return postedUserName;
    }

    public void setPostedUserName(String postedUserName) {
        this.postedUserName = postedUserName;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
