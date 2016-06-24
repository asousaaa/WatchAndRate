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
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.UserEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by hosam azzam on 11/06/2016.
 */
public class own_reviews extends Activity {
    View review_card;
    LayoutInflater layoutInflater;
    String Url;
    UserEntity user;
    LinearLayout list_View;
    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;
    ProgressDialog prgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);
        ActionBar ab = getActionBar();
        assert ab != null;
        ab.hide();
        user = UserEntity.getCurrentUser();
        //  Url="http://10.0.3.2:8080/Watch_and_Rate";
//        Url ="http://192.168.1.6:8080/Watch_and_Rate";
        Url = "http://watchandrate-fcigp.rhcloud.com";
        list_View = (LinearLayout) findViewById(R.id.userlistView);

        layoutInflater = LayoutInflater.from(this);

      review_card = layoutInflater.inflate(R.layout.review_card_own, null);


        System.out.println("loool");
        Connect conn = new Connect();
        JSONObject jobj = new JSONObject();
        jobj.put("userid", user.getUser_Id());
        conn.data = "data=" + jobj.toString();
        conn.execute(Url + "/Viewown");
        System.out.println(conn.data);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading...");
        prgDialog.setCancelable(false);
        prgDialog.show();

        ImageButton refresh = (ImageButton) findViewById(R.id.refresh_btn);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list_View.removeAllViews();
                System.out.println("loool");
                Connect conn = new Connect();
                JSONObject jobj = new JSONObject();
                jobj.put("userid", user.getUser_Id());
                conn.data = "data=" + jobj.toString();
                conn.execute(Url + "/Viewown");
                System.out.println(conn.data);
            }
        });


        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(own_reviews.this);
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


    }


    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "", tag = "";

        protected Void doInBackground(String... strings) {
            result = new Connection().sendrequest(strings[0], data);
            return null;
        }

        protected void onPostExecute(Void unused) {
            System.out.println(result);
            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(result);
                JSONObject data = (JSONObject) obj;
                if (data.get("status").toString().equals("movie")) {
                    Intent intent = new Intent(own_reviews.this, movie_info.class);
                    intent.putExtra("movie", data.toString());
                    startActivity(intent);
                } else if (data.get("status").toString().equals("ownreview")) {

                    result = data.get("results").toString();

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
                } else if (data.get("status").toString().equals("deleted")) {
                    Toast.makeText(getApplicationContext(), "your review deleted successfully ", Toast.LENGTH_SHORT).show();
                    list_View.removeAllViews();
                    System.out.println("loool");
                    Connect conn = new Connect();
                    JSONObject jobj = new JSONObject();
                    jobj.put("userid", user.getUser_Id());
                    conn.data = "data=" + jobj.toString();
                    conn.execute(Url + "/Viewown");
                    System.out.println(conn.data);

                } else {
                    Toast.makeText(getApplicationContext(), "something wrong ,Try again..", Toast.LENGTH_SHORT).show();
                    prgDialog.dismiss();
                }


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
        ImageButton rev_open_mov = (ImageButton) review_card.findViewById(R.id.review_open_mov_btn);

        rev_open_mov.setId(Integer.valueOf(review.get("mov_id").toString()));
        rev_open_mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
                Connect conn = new Connect();
                JSONObject jobj = new JSONObject();
                jobj.put("movieid", view.getId());
                conn.data = "data=" + jobj.toString();
                conn.execute(Url + "/Get_Info");
                System.out.println(conn.data);
            }
        });

            ImageButton rev_remove = (ImageButton) review_card.findViewById(R.id.review_remove_btn);
            rev_remove.setId(Integer.valueOf(review.get("rev_id").toString()));
            rev_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(view.getId());
                    Connect conn = new Connect();
                    JSONObject jobj = new JSONObject();
                    jobj.put("userid", user.getUser_Id());
                    jobj.put("reviewid", view.getId());
                    conn.data = "data=" + jobj.toString();
                    conn.execute(Url + "/DeleteReview");
                    System.out.println(conn.data);
                    prgDialog.show();
                }
            });


        mov_name.setText("Movie : " + review.get("mov_name").toString());
        review_title.setText("Title : " + review.get("review_title").toString());
        rev_data.setText("Date : " + review.get("date").toString());
        rev_contnet.setText("Description : " + review.get("content").toString());
        list_View.addView(review_card);

        review_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
                Intent intent = new Intent(own_reviews.this, show_review.class);
                intent.putExtra("rev_id", view.getId());
                startActivity(intent);

            }
        });


        review_card = layoutInflater.inflate(R.layout.review_card_own, null);
    }


}
