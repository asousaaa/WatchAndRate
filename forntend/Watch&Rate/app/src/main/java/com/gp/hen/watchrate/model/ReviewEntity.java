package com.gp.hen.watchrate.model;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hosam azzam
 */
public class ReviewEntity {

    private int reviewId;
    private String title;
    private String Body;
    private double systemRate;
    private int userID;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    private ArrayList<RateEntity> rate;
    private ArrayList<CommentEntity> comment;

    public ArrayList<CommentEntity> getComment() {
        return comment;
    }

    public void setComment(ArrayList<CommentEntity> comment) {
        this.comment = comment;
    }

    public int getUserID() {

        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;

    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public void setTitle(String title) {

        this.title = title;
    }
}
