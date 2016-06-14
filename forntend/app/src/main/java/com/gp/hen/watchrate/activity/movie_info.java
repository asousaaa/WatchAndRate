package com.gp.hen.watchrate.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gp.hen.watchrate.LoadImage;
import com.gp.hen.watchrate.R;

import org.json.JSONStringer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Objects;
import java.util.jar.JarInputStream;

/**
 * Created by esraa ahmed on 3/14/2016.
 */
public class movie_info extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        // setContentView(R.layout.activity_movie_info);
        ImageButton back = (ImageButton) findViewById(R.id.menu_btn);
        ImageButton menu = (ImageButton) findViewById(R.id.back_btn);

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

        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(movie_info.this, add_review.class);
                finish();
                startActivity(intent);
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
}
