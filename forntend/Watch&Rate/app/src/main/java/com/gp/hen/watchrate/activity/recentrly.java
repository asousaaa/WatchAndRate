package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by noha magdy on 15-Jun-16.
 */
public class recentrly extends Activity {
    View recommendation_card;
    LayoutInflater layoutInflater;
    LinearLayout list_View;
    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        ActionBar ab = getActionBar();
        assert ab != null;
        ab.hide();

        // <editor-fold defaultstate="collapsed" desc="Sliding Panel Layout and swipeView .">
        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(recentrly.this);
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
        // </editor-fold>

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading...");
        prgDialog.setCancelable(false);
        prgDialog.show();

        layoutInflater = LayoutInflater.from(this);
        recommendation_card = layoutInflater.inflate(R.layout.search_card, null);
        list_View = (LinearLayout) findViewById(R.id.userlistView);

        TextView header = (TextView) findViewById(R.id.header_title);
        header.setText("Recently Movies");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

        Connect conn = new Connect();
        conn.tag = "movie_api";
        String query = "http://api.themoviedb.org/3/discover/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&sort_by=release_date.desc&primary_release_date.lte=" + strDate;
        query = query.replaceAll(" ", "%20");
        conn.execute(query);
    }


    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "", tag = "", mov_name;

        protected Void doInBackground(String... strings) {
            result = new Connection().sendrequest(strings[0], data);
            return null;
        }

        protected void onPostExecute(Void unused) {
            if (tag.equals("movie_api")) {
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(result);
                    JSONObject data = (JSONObject) obj;
                    result = data.get("results").toString();
                    obj = parser.parse(result);
                    JSONArray movies = (JSONArray) obj;
                    for (int i = 0; i < movies.size(); i++) {
                        obj = parser.parse(movies.get(i).toString());
                        JSONObject film = (JSONObject) obj;
                        addToList(film);
                    }
                    prgDialog.hide();
                    if (movies.size() == 0)
                        Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }


    public void addToList(final JSONObject film) {
        recommendation_card.setId(Integer.valueOf(film.get("id").toString()));
        ImageView mov_img = (ImageView) recommendation_card.findViewById(R.id.movie_img);
        TextView mov_title = (TextView) recommendation_card.findViewById(R.id.movie_title);
        TextView mov_year = (TextView) recommendation_card.findViewById(R.id.movie_year);
        TextView vote_count = (TextView) recommendation_card.findViewById(R.id.vote_count);
        TextView vote_avg = (TextView) recommendation_card.findViewById(R.id.vote_avg);
        LoadImage load = new LoadImage();
        load.Image(mov_img);
        load.execute("http://image.tmdb.org/t/p/w300" + film.get("poster_path"));
        mov_title.setText("Title : " + film.get("original_title").toString());
        mov_year.setText("Year : " + film.get("release_date").toString());
        vote_count.setText("vote : " + film.get("vote_count").toString());
        vote_avg.setText("average : " + film.get("vote_average").toString());

        list_View.addView(recommendation_card);

        final String jsonText = film.toJSONString();
        recommendation_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(recentrly.this, movie_info.class);
                System.out.println("movie id "+jsonText);
                intent.putExtra("movie", jsonText);
                startActivity(intent);
            }
        });

        recommendation_card = layoutInflater.inflate(R.layout.search_card, null);
    }
}
