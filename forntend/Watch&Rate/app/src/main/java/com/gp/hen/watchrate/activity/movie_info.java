package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.hen.watchrate.Connection;
import com.gp.hen.watchrate.LoadImage;
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.UserEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by esraa ahmed on 3/14/2016.
 */
public class movie_info extends Activity {

    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;
    JSONObject json;
    View review_card;
    LayoutInflater layoutInflater;
    LinearLayout list_View;
    ProgressDialog prgDialog;
    String Url;
	TextView  story , direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ActionBar ab = getActionBar();
        assert ab != null;
        ab.hide();

        //  Url="http://10.0.3.2:8080/Watch_and_Rate";
//        Url ="http://192.168.1.6:8080/Watch_and_Rate";
        Url = "http://watchandrate-fcigp.rhcloud.com";

        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(movie_info.this);
        slide.setmSlidingPanel(mSlidingPanel);
        slide.setUser_img((ImageView) findViewById(R.id.slide_user_img));
        slide.setLogout((Button) findViewById(R.id.logout_btn));
        slide.setHome((Button) findViewById(R.id.home_btn));
        slide.setSetting((Button) findViewById(R.id.settings_btn));
        slide.setMy_review((Button) findViewById(R.id.myreview_btn));
        slide.setAbout((Button) findViewById(R.id.about_btn));
        slide.setProfile((Button) findViewById(R.id.profile_btn));
        slide.setUser_name((TextView) findViewById(R.id.slide_user_name));

        slide.init();

        ImageButton menu = (ImageButton) findViewById((R.id.menu_btn));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSlidingPanel.isOpen()) {
                    mSlidingPanel.closePane();
                } else {
                    mSlidingPanel.openPane();
                }
            }
        });
        // Sliding Panel Layout ( END )


        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading...");
        prgDialog.setCancelable(false);

        layoutInflater = LayoutInflater.from(this);
        review_card = layoutInflater.inflate(R.layout.review_card_user, null);
        list_View = (LinearLayout) findViewById(R.id.listView);

        ImageButton add_review = (ImageButton) findViewById(R.id.add_review_btn);


        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        TextView movieYear = (TextView) findViewById(R.id.movie_year);
        TextView movieVoteCount = (TextView) findViewById(R.id.vote_count);
        TextView movieVote = (TextView) findViewById(R.id.vote_avg);
        TextView movieDes = (TextView) findViewById(R.id.movie_des);
        ImageView movieImage = (ImageView) findViewById(R.id.movie_img);
        Button reviewsButton = (Button) findViewById(R.id.Reviews_btn);

		 story = (TextView) findViewById(R.id.StoryP);
        direction = (TextView) findViewById(R.id.DirectionP);

        Intent intent = getIntent();
        String s = intent.getStringExtra("movie");
        JSONParser parser = new JSONParser();

        try {
            System.out.println("entered ");
            json = (JSONObject) parser.parse(s);
			Connect conn = new Connect();
            JSONObject jobj = new JSONObject();
            jobj.put("movieid", json.get("id").toString());
            conn.data = "data=" + jobj.toString();
            conn.execute(Url + "/Get_Info");
            movieTitle.setText("Title : " + json.get("original_title").toString());
            movieYear.setText("Year : " + json.get("release_date").toString());
            movieVoteCount.setText("vote : " + json.get("vote_count").toString());
            movieVote.setText("average : " + json.get("vote_average").toString());
            movieDes.setText("description : " + json.get("overview").toString());


            LoadImage load = new LoadImage();
            load.Image(movieImage);
            load.context=movie_info.this;
            load.execute("http://image.tmdb.org/t/p/w300" + json.get("poster_path"));
            System.out.println("http://image.tmdb.org/t/p/w300" + json.get("poster_path"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        reviewsButton.setClickable(true);
        reviewsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Connect conn = new Connect();
                JSONObject obj = new JSONObject();
                obj.put("movieid", json.get("id").toString());
                conn.data = "data=" + obj.toString();
                conn.execute(Url + "/ViewReviews");
                prgDialog.show();
            }
        });


        add_review.setClickable(true);
        add_review.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                UserEntity user;
                user = UserEntity.getCurrentUser();
                if (user.getUser_Id() != 0) {
                    Intent intent = new Intent(movie_info.this, add_review.class);
                    intent.putExtra("movie", String.valueOf(json.toJSONString()));
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "you must sign up to use this feature :P ", Toast.LENGTH_SHORT).show();
                }
            }
        });
		
		    rateButton.setClickable(true);
        rateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Connect conn = new Connect();
                JSONObject obj = new JSONObject();
                obj.put("movieid", json.get("id").toString());
                conn.data = "data=" + obj.toString();
                conn.execute(Url + "/calculateRate");
                prgDialog.show();
            }
        });

    }

    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "";

        protected Void doInBackground(String... strings) {
            result = new Connection().sendrequest(strings[0], data);
            return null;
        }

        protected void onPostExecute(Void unused) {

            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(result);

                JSONObject object = (JSONObject) obj;
                if (object.get("status").toString().equals("reviewlist")) {

                    result = object.get("results").toString();

                    obj = parser.parse(result);
                    JSONArray reviews = (JSONArray) obj;
                    for (int i = 0; i < reviews.size(); i++) {
                        obj = parser.parse(reviews.get(i).toString());
                        JSONObject review = (JSONObject) obj;
                        System.out.println(review.toString());
                        addReviewToList(review);
                    }
                    prgDialog.dismiss();
                    if (reviews.size() == 0) {
                        Toast.makeText(getApplicationContext(), "You don't have any reviews yet, please write some reviews", Toast.LENGTH_SHORT).show();

                    }
                }
				else if (object.get("status").toString().equals("rated")) {

                    double rate = Double.parseDouble(object.get("rate").toString());
                    double movie_id = Double.parseDouble(object.get("movieid").toString());
                    DecimalFormat format = new DecimalFormat("#0.0");
                    rate = Double.parseDouble(format.format(rate));
                    Intent intent = new Intent(movie_info.this, movie_rate.class);

                    intent.putExtra("movieid", movie_id);
                    intent.putExtra("rate",rate);

                    startActivity(intent);
                }
				else if (object.get("status").toString().equals("movie")) {


                    story.setText( object.get("story").toString()+"%");
                    direction.setText( object.get("direction").toString()+"%");

                }				
				else
                    Toast.makeText(getApplicationContext(), object.get("status").toString(), Toast.LENGTH_SHORT).show();
                prgDialog.dismiss();
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                prgDialog.dismiss();
                e.printStackTrace();
            }
        }
    }

    public void addReviewToList(final JSONObject review) {
        review_card.setId(Integer.valueOf(review.get("rev_id").toString()));
        TextView mov_name = (TextView) review_card.findViewById(R.id.movie_title);
        TextView review_title = (TextView) review_card.findViewById(R.id.review_title);
        TextView rev_data = (TextView) review_card.findViewById(R.id.review_date);
        TextView rev_contnet = (TextView) review_card.findViewById(R.id.review_content);
        TextView review_author = (TextView) review_card.findViewById(R.id.review_author);
        ImageButton rev_open_mov = (ImageButton) review_card.findViewById(R.id.review_open_mov_btn);

        rev_open_mov.setVisibility(View.GONE);

        mov_name.setText("Movie : " + review.get("mov_name").toString());
        review_title.setText("Title : " + review.get("review_title").toString());
        rev_data.setText("Date : " + review.get("date").toString());
        review_author.setText("Author : " + review.get("username").toString());

        rev_contnet.setText("Description : \n" + review.get("content").toString());
        list_View.addView(review_card);

        review_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
                Intent intent = new Intent(movie_info.this, show_review.class);
                intent.putExtra("rev_id", view.getId());
                startActivity(intent);

            }
        });

        review_card = layoutInflater.inflate(R.layout.review_card_user, null);
    }


}
