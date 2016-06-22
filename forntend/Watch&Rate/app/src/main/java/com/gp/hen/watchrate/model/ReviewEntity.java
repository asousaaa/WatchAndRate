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
    private ArrayList<RateEntity> rate;
    private ArrayList<CommentEntity> comment;
    
}
