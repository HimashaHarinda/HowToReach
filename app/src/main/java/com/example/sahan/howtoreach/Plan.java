package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/1/2017.
 */

public class Plan {
    private String planName;
    private String planDesc;
    private String tripId;
    private String userId;
    private CarRental carRental;
    private Railway railway;
    private GroundTransport groundTransport;
    private Restaurant restaurant;
    private Activits activits;

    public Plan() {
    }

    public Plan(String planName, String planDesc, String tripId, String userId, CarRental carRental, Railway railway, GroundTransport groundTransport, Restaurant restaurant, Activits activits) {
        this.planName = planName;
        this.planDesc = planDesc;
        this.tripId = tripId;
        this.userId = userId;
        this.carRental = carRental;
        this.railway = railway;
        this.groundTransport = groundTransport;
        this.restaurant = restaurant;
        this.activits = activits;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
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

    public CarRental getCarRental() {
        return carRental;
    }

    public void setCarRental(CarRental carRental) {
        this.carRental = carRental;
    }

    public Railway getRailway() {
        return railway;
    }

    public void setRailway(Railway railway) {
        this.railway = railway;
    }

    public GroundTransport getGroundTransport() {
        return groundTransport;
    }

    public void setGroundTransport(GroundTransport groundTransport) {
        this.groundTransport = groundTransport;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Activits getActivits() {
        return activits;
    }

    public void setActivits(Activits activits) {
        this.activits = activits;
    }
}
