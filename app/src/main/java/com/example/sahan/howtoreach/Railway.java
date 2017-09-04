package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/4/2017.
 */

public class Railway {
    private String fromStation;
    private String toStation;
    private String railwayDate;

    public Railway() {
    }

    public Railway(String fromStation, String toStation, String railwayDate) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.railwayDate = railwayDate;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getRailwayDate() {
        return railwayDate;
    }

    public void setRailwayDate(String railwayDate) {
        this.railwayDate = railwayDate;
    }
}
