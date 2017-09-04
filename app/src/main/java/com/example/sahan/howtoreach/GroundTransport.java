package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/4/2017.
 */

public class GroundTransport {
    private String routeNo;
    private String routeFrom;
    private String routeTo;

    public GroundTransport() {
    }

    public GroundTransport(String routeNo, String routeFrom, String routeTo) {
        this.routeNo = routeNo;
        this.routeFrom = routeFrom;
        this.routeTo = routeTo;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getRouteFrom() {
        return routeFrom;
    }

    public void setRouteFrom(String routeFrom) {
        this.routeFrom = routeFrom;
    }

    public String getRouteTo() {
        return routeTo;
    }

    public void setRouteTo(String routeTo) {
        this.routeTo = routeTo;
    }
}
