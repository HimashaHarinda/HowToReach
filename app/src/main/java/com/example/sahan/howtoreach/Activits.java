package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/4/2017.
 */

public class Activits {
    private String activityName;
    private String activityDetails;

    public Activits() {
    }

    public Activits(String activityName, String activityDetails) {
        this.activityName = activityName;
        this.activityDetails = activityDetails;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(String activityDetails) {
        this.activityDetails = activityDetails;
    }
}
