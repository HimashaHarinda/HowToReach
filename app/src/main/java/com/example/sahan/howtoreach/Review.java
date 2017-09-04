package com.example.sahan.howtoreach;

/**
 * Created by Himasha on 9/4/2017.
 */

public class Review {
    private String review;
    private String postedUID;
    private String postedUname;

    public Review() {
    }

    public Review(String review, String postedUID, String postedUname) {
        this.review = review;
        this.postedUID = postedUID;
        this.postedUname = postedUname;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPostedUID() {
        return postedUID;
    }

    public void setPostedUID(String postedUID) {
        this.postedUID = postedUID;
    }

    public String getPostedUname() {
        return postedUname;
    }

    public void setPostedUname(String postedUname) {
        this.postedUname = postedUname;
    }
}
