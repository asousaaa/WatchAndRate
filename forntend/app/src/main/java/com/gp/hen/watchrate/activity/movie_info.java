package com.gp.hen.watchrate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;

import com.gp.hen.watchrate.LoadImage;
import com.gp.hen.watchrate.R;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Created by esraa ahmed on 3/14/2016.
 */
public class movie_info extends Activity {

    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);


        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(movie_info.this);
        slide.setmSlidingPanel(mSlidingPanel);
        slide.setUser_img((ImageView) findViewById(R.id.slide_user_img));
        slide.setLogout( (Button) findViewById(R.id.logout_btn));
        slide.setHome((Button) findViewById(R.id.home_btn));
        slide.setSetting((Button) findViewById(R.id.settings_btn));
        slide.setMy_review((Button) findViewById(R.id.myreview_btn));
        slide.setAbout((Button) findViewById(R.id.about_btn));
        slide.setProfile((Button) findViewById(R.id.profile_btn));
        slide.setUser_name((TextView) findViewById(R.id.slide_user_name));
        slide.setUser_score((TextView) findViewById(R.id.slide_user_score));
        slide.init();

        ImageButton menu = (ImageButton) findViewById((R.id.menu_btn));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSlidingPanel.openPane();
            }
        });
        // Sliding Panel Layout ( END )



        ImageButton add_review = (ImageButton) findViewById(R.id.add_review_btn);


        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        TextView movieYear = (TextView) findViewById(R.id.movie_year);
        TextView movieVoteCount = (TextView) findViewById(R.id.vote_count);
        TextView movieVote = (TextView) findViewById(R.id.vote_avg);
        TextView movieDes = (TextView) findViewById(R.id.movie_des);
        ImageView movieImage = (ImageView) findViewById(R.id.movie_img);

        Bundle b = getIntent().getExtras();
        String str="";
        if(b!=null)
        {
            str = b.getString("movie");
        }
        Intent intent=getIntent();
        String s=intent.getStringExtra("movie");
        JSONParser parser = new JSONParser();
        JSONObject json=new JSONObject();
        try
        {
            json = (JSONObject) parser.parse(s);
        }
        catch (Exception e)
        {}
        movieTitle.setText("Title : " + json.get("original_title").toString());
        movieYear.setText("Year : " + json.get("release_date").toString());
        movieVoteCount.setText("vote : " + json.get("vote_count").toString());
        movieVote.setText("average : " + json.get("vote_average").toString());
        movieDes.setText("description : " + json.get("overview").toString());

        LoadImage load = new LoadImage();
        load.Image(movieImage);
        load.execute("http://image.tmdb.org/t/p/w300" + json.get("poster_path"));

        add_review.setClickable(true);
        add_review.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(movie_info.this, add_review.class);
                finish();
                startActivity(intent);
            }
        });

    }
}
