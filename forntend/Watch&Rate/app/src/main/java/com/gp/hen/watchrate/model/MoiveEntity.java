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
public class MoiveEntity {
    private int Moive_ID;
    private String Name;
    private String Year;
    private String Description;
    private double calculatedRate;
    private ArrayList<ReviewEntity> review;
}
