package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/4/2017.
 */

public class CarRental {
    private String carNo;
    private String carModel;
    private String carAgent;
    private String carRentalStartDate;
    private String carRentalEndDate;

    public CarRental() {
    }

    public CarRental(String carNo, String carModel, String carAgent, String carRentalStartDate, String carRentalEndDate) {
        this.carNo = carNo;
        this.carModel = carModel;
        this.carAgent = carAgent;
        this.carRentalStartDate = carRentalStartDate;
        this.carRentalEndDate = carRentalEndDate;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarAgent() {
        return carAgent;
    }

    public void setCarAgent(String carAgent) {
        this.carAgent = carAgent;
    }

    public String getCarRentalStartDate() {
        return carRentalStartDate;
    }

    public void setCarRentalStartDate(String carRentalStartDate) {
        this.carRentalStartDate = carRentalStartDate;
    }

    public String getCarRentalEndDate() {
        return carRentalEndDate;
    }

    public void setCarRentalEndDate(String carRentalEndDate) {
        this.carRentalEndDate = carRentalEndDate;
    }
}
