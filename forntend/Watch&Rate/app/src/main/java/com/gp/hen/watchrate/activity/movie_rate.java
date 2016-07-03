package com.gp.hen.watchrate.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;

import com.gp.hen.watchrate.R;

public class movie_rate extends Activity {
  //  String Url = "https://watchandrateserver-fcigp.rhcloud.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_details_card);

        RatingBar systemRate = (RatingBar) findViewById(R.id.ratingBar);
        Intent intent = getIntent();

        double rate = intent.getDoubleExtra("rate", 0.00);
        double movieid = intent.getDoubleExtra("movieid", 0.00);
         rate =Math.ceil(rate);
        float temp = (float) rate;
        systemRate.setRating(temp);
    }
}
