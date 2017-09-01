package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/1/2017.
 */

public class Plan {
    private String planName;
    private String hotelName;
    private String carNo;
    private String tripId;
    private String userId;

    public Plan() {
    }

    public Plan(String planName, String hotelName, String carNo, String tripId, String userId) {
        this.planName = planName;
        this.hotelName = hotelName;
        this.carNo = carNo;
        this.tripId = tripId;
        this.userId = userId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
