package com.gp.hen.watchrate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.ReviewEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by noha magdy on 17-Jun-16.
 */
public class view_reviews extends Activity
{
    View review_card;
    LayoutInflater layoutInflater;
    LinearLayout list_View;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        layoutInflater=LayoutInflater.from(this);
        review_card= layoutInflater.inflate(R.layout.review_card_for_movie, null);
        list_View = (LinearLayout) findViewById(R.id.userlistView);
        String Url = "http://watchandrate-fcigp.rhcloud.com";

        Bundle extras = getIntent().getExtras();
        System.out.print("aaaaaaaaa");
        String array = extras.getString("array");
        JSONParser parser = new JSONParser();
        JSONObject json=new JSONObject();
        JSONArray reviewsArray=new JSONArray();

        try
        {
            json = (JSONObject) parser.parse(array);
            reviewsArray=(JSONArray)json.get("results");
        }
        catch (Exception e)
        {}

        System.out.println("reviews "+json.toString());
        ArrayList<ReviewEntity> reveiws=new ArrayList<ReviewEntity>();
        ArrayList<String> movieNames=new ArrayList<String>();
        ArrayList<String> reveiwsDates=new ArrayList<String>();
        ArrayList<String> reveiwsUsers=new ArrayList<String>();

        try
        {
            for(int i=0;i<reviewsArray.size();i++)
            {
                JSONObject object=new JSONObject();
                object = (JSONObject)reviewsArray.get(i);
                //System.out.println("data"+object.toString());
                ReviewEntity review=new ReviewEntity();
                String movieName=object.get("mov_name").toString();
                String reviewTitle=object.get("review_title").toString();
                String reviewDate=object.get("date").toString();
                String reviewAuthor= object.get("username").toString();
                int reviewID= Integer.parseInt(object.get("rev_id").toString());
                String reviewContent=object.get("content").toString();

                review.setBody(reviewContent);
                review.setTitle(reviewTitle);
                review.setReviewId(reviewID);

                reveiws.add(review);
                movieNames.add(movieName);
                reveiwsDates.add(reviewDate);
                reveiwsUsers.add(reviewAuthor);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0;i<reveiws.size();i++)
        {
            review_card.setId(Integer.valueOf(reveiws.get(i).getReviewId()));
            TextView movieName = (TextView) review_card.findViewById(R.id.movie_title);
            TextView reviewTitle = (TextView) review_card.findViewById(R.id.review_title);
            TextView reviewDate = (TextView) review_card.findViewById(R.id.review_date);
            TextView reviewAuthor = (TextView) review_card.findViewById(R.id.review_author);
            TextView reviewContent = (TextView) review_card.findViewById(R.id.review_content);

            movieName.setText(movieNames.get(i));
            reviewTitle.setText(reveiws.get(i).getTitle());
            reviewDate.setText(reveiwsDates.get(i));
            reviewAuthor.setText(reveiwsUsers.get(i));
            reviewContent.setText(reveiws.get(i).getBody());
            list_View.addView(review_card);
            review_card = layoutInflater.inflate(R.layout.review_card_for_movie, null);
        }
    }
}
