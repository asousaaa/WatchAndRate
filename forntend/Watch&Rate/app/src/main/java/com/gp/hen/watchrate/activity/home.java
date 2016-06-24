package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by esraa ahmed on 11/30/2015.
 */
public class home extends Activity {
    //nyt api=key = 28bcb54b06ae2bb40882d7a5e5dba212:4:74287520
    //http://api.nytimes.com/svc/movies/v2/reviews/?query=taken+3&api-key=28bcb54b06ae2bb40882d7a5e5dba212:4:74287520picks.xmls
    LinearLayout search_content, list_View, global_list_View, recomlist, recentlylist, toplist, reveiw_list, notfiy_panal,homepanel;

    LayoutInflater layoutInflater;
    View search_card, review_card, user_card, lastreview_card;
    ArrayList movies_id;
    String Url;
    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;
    ImageButton show_notfy;
    Button show_recom, show_recen, show_recent_review, show_top;
    ProgressDialog prgDialog;
    private SwipeRefreshLayout swipeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar ab = getActionBar();
        assert ab != null;
        ab.hide();
        // Url="http://10.0.3.2:8080/Watch_and_Rate";
//        Url ="http://192.168.1.6:8080/Watch_and_Rate";
        Url = "http://watchandrate-fcigp.rhcloud.com";

// <editor-fold defaultstate="collapsed" desc="Sliding Panel Layout and swipeView .">
        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(home.this);
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

        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.BLACK);
        swipeView.setDistanceToTriggerSync(10);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used

        swipeView.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 200);

        // </editor-fold>

        Button recom = (Button) findViewById(R.id.recom_btn);
        recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, recommend.class);
                startActivity(intent);
            }
        });

        Button recent = (Button) findViewById(R.id.recently_btn);
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, recentrly.class);
                startActivity(intent);
            }
        });

        Button lastreviwes = (Button) findViewById(R.id.recently_rev__btn);
        lastreviwes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, lastreviews.class);
                startActivity(intent);
            }
        });

        Button topreviewer = (Button) findViewById(R.id.top_btn);
        topreviewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, topreviewers.class);
                startActivity(intent);
            }
        });

        movies_id = new ArrayList();
        UserEntity user = UserEntity.getCurrentUser();
        System.out.println(user.getName() + " " + user.getEmail());
        //http://image.tmdb.org/t/p/w300/hO1R1TI429PjkOjby4dTPBrWFwn.jpg
        //http://api.themoviedb.org/3/movie/260346?api_key=23386b0753dd348bcb87ab9f516da5d5
        //The reviews api key (NYT API)
        //  28bcb54b06ae2bb40882d7a5e5dba212:4:74287520

        notfiy_panal = (LinearLayout) findViewById(R.id.notfcationpanel);
        homepanel = (LinearLayout) findViewById(R.id.homepanal);

        show_notfy = (ImageButton) findViewById(R.id.notfiy_btn);
        show_notfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
                if (notfiy_panal.getVisibility() == View.GONE) {
                    notfiy_panal.setVisibility(View.VISIBLE);
                } else {
                    notfiy_panal.setVisibility(View.GONE);
                }
            }
        });


        layoutInflater = LayoutInflater.from(this);
        search_card = layoutInflater.inflate(R.layout.search_card, null);
        review_card = layoutInflater.inflate(R.layout.review_card, null);
        lastreview_card = layoutInflater.inflate(R.layout.review_card_user, null);
        user_card = layoutInflater.inflate(R.layout.user_card, null);

        recentlylist = (LinearLayout) findViewById(R.id.recentrlylistview);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

        Connect conn = new Connect();
        conn.tag = "movie_api";
        conn.listview = recentlylist;
        conn.limit = 5;
        String query = "http://api.themoviedb.org/3/discover/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&sort_by=release_date.desc&primary_release_date.lte=" + strDate;
        query = query.replaceAll(" ", "%20");
        conn.execute(query);

        reveiw_list = (LinearLayout) findViewById(R.id.recentrlyreviewslistview);

        conn = new Connect();
        conn.tag = "review_db";
        conn.limit = 5;
        query = "http://watchandrate-fcigp.rhcloud.com/LastReviews";
        conn.execute(query);


        recomlist = (LinearLayout) findViewById(R.id.recommlistview);

        conn = new Connect();
        conn.tag = "movie_api";
        conn.listview = recomlist;
        conn.limit = 5;
        query = "http://api.themoviedb.org/3/discover/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&sort_by=popularity.desc&sort_by=vote_count.desc";
        query = query.replaceAll(" ", "%20");
        conn.execute(query);


        toplist = (LinearLayout) findViewById(R.id.topuserlistview);
        conn = new Connect();
        conn.tag = "higherReviewers";
        conn.limit = 5;
        conn.execute("http://watchandrate-fcigp.rhcloud.com/higher_reviewers");


        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading...");
        prgDialog.setCancelable(false);
        prgDialog.show();

        show_recen = (Button) findViewById(R.id.show_hide_recen_btn);
        show_recen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recentlylist.getVisibility() == View.VISIBLE) {
                    recentlylist.setVisibility(View.GONE);
                    show_recen.setText("Show");
                } else {
                    recentlylist.setVisibility(View.VISIBLE);
                    show_recen.setText("Hide");
                }
            }
        });

        show_recent_review = (Button) findViewById(R.id.show_hide_recen_rev_btn);
        show_recent_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reveiw_list.getVisibility() == View.VISIBLE) {
                    reveiw_list.setVisibility(View.GONE);
                    show_recent_review.setText("Show");
                } else {
                    reveiw_list.setVisibility(View.VISIBLE);
                    show_recent_review.setText("Hide");
                }
            }
        });

        show_top = (Button) findViewById(R.id.show_hide_top_btn);
        show_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toplist.getVisibility() == View.VISIBLE) {
                    toplist.setVisibility(View.GONE);
                    show_top.setText("Show");
                } else {
                    toplist.setVisibility(View.VISIBLE);
                    show_top.setText("Hide");
                }
            }
        });

        show_recom = (Button) findViewById(R.id.show_hide_recomm_btn);
        show_recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recomlist.getVisibility() == View.VISIBLE) {
                    recomlist.setVisibility(View.GONE);
                    show_recom.setText("Show");
                } else {
                    recomlist.setVisibility(View.VISIBLE);
                    show_recom.setText("Hide");
                }
            }
        });


        list_View = (LinearLayout) findViewById(R.id.searchlistView);
        search_content = (LinearLayout) findViewById(R.id.search_table);
        ImageButton search_btn = (ImageButton) findViewById(R.id.search_btn);
        final Button Search_info_btn = (Button) findViewById(R.id.Search_info_btn);
        final Button Reviews_btn = (Button) findViewById(R.id.Reviews_btn);
        final EditText search_box = (EditText) findViewById(R.id.search_box);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!search_box.getText().toString().equals("")) {
                    Search_info_btn.setBackground(getResources().getDrawable(R.drawable.tab_box));
                    list_View.removeAllViews();
                    search_content.setVisibility(View.VISIBLE);
                    Connect conn = new Connect();

                    conn.tag = "movie_api";
                    conn.listview = list_View;
                    String query = "http://api.themoviedb.org/3/search/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&query=" + search_box.getText().toString();
                    query = query.replaceAll(" ", "%20");
                    conn.execute(query);
                    swipeView.setRefreshing(true);
                    swipeView.setEnabled(true);
                }
            }
        });
        Search_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movies_id = new ArrayList();
                Search_info_btn.setBackground(getResources().getDrawable(R.drawable.tab_box));
                Reviews_btn.setBackground(getResources().getDrawable(R.drawable.button_box));
                list_View.removeAllViews();
                search_content.setVisibility(View.VISIBLE);
                Connect conn = new Connect();
                conn.tag = "movie_api";
                conn.listview = list_View;
                String query = "http://api.themoviedb.org/3/search/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&query=" + search_box.getText().toString();
                query = query.replaceAll(" ", "%20");
                conn.execute(query);
                swipeView.setRefreshing(true);
                swipeView.setEnabled(true);

            }
        });
        Reviews_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movies_id = new ArrayList();
                Search_info_btn.setBackground(getResources().getDrawable(R.drawable.button_box));
                Reviews_btn.setBackground(getResources().getDrawable(R.drawable.tab_box));
                list_View.removeAllViews();
                //  search_content.setVisibility(View.VISIBLE);
                //   Toast.makeText(getApplicationContext(),"not implement yet", Toast.LENGTH_SHORT).show();
                swipeView.setRefreshing(true);
                swipeView.setEnabled(true);

                Connect conn = new Connect();
                conn.tag = "review_movie_api";
                String query = "http://api.themoviedb.org/3/search/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&query=" + search_box.getText().toString();
                query = query.replaceAll(" ", "%20");
                conn.execute(query);


            }
        });


    }


    @Override
    public void onBackPressed() {
        if (search_content.getVisibility() == View.VISIBLE) {
            search_content.setVisibility(View.GONE);
        }
        else if (notfiy_panal.getVisibility() == View.VISIBLE) {
            notfiy_panal.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }

    }

    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "", tag = "", mov_name;
        LinearLayout listview;
        int limit = 0;

        protected Void doInBackground(String... strings) {
            //   System.out.println("dfklsdk;sd");
            result = new Connection().sendrequest(strings[0], data);
            return null;
        }

        protected void onPostExecute(Void unused) {
            if (tag.equals("movie_api") || tag.equals("review_movie_api") || tag.equals("review_db") || tag.equals("higherReviewers")) {
                System.out.println(result);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(result);
                    JSONObject data = (JSONObject) obj;
                    result = data.get("results").toString();
                    System.out.println(result);
                    obj = parser.parse(result);
                    JSONArray movies = (JSONArray) obj;
                    global_list_View = listview;
                    if (limit != 0) {
                        if (movies.size() < limit) {
                            limit = movies.size();
                        }
                    } else {
                        limit = movies.size();
                    }
                    for (int i = 0; i < limit; i++) {
                        obj = parser.parse(movies.get(i).toString());
                        JSONObject json = (JSONObject) obj;
                        System.out.println(json.toString());
                        if (tag.equals("review_movie_api")) {
                            movies_id.add(json.get("original_title"));
                            movies_id.add(json.get("id"));
                        } else if (tag.equals("review_db")) {
                            addlastreviewtolist(json);
                        } else if (tag.equals("higherReviewers")) {
                            addusersToList(json);
                        } else {
                            addToList(json);
                        }
                    }
                    prgDialog.dismiss();
                    if (movies.size() == 0) {
                        Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_SHORT).show();

                    }

                    if (tag.equals("review_movie_api")) {
                        for (int i = 0; i < movies_id.size(); i++) {
                            Connect conn2 = new Connect();
                            conn2.tag = "review_api";
                            conn2.mov_name = movies_id.get(i).toString();
                            i++;
                            conn2.execute(Url + "/Search?id=" + movies_id.get(i));
                        }
                        if (movies_id.size() == 0) {
                            swipeView.setRefreshing(false);
                            swipeView.setEnabled(false);

                        }
                    }


                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                    prgDialog.dismiss();
                    e.printStackTrace();

                }
                if (swipeView.isRefreshing()) {
                    System.out.println("false ");
                    swipeView.setRefreshing(false);
                    swipeView.setEnabled(false);
                }
            }
            if (tag.equals("review_api")) {
                System.out.println(result);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(result);
                    JSONObject data = (JSONObject) obj;
                    result = data.get("results").toString();
                    System.out.println(result);
                    obj = parser.parse(result);
                    JSONArray reviews = (JSONArray) obj;
                    for (int i = 0; i < reviews.size(); i++) {
                        obj = parser.parse(reviews.get(i).toString());
                        JSONObject review = (JSONObject) obj;
                        System.out.println(review.toString());
                        addReviewToList(review, mov_name, i);
                    }
                    if (reviews.size() == 0) {
                        //   Toast.makeText(getApplicationContext(),"no result", Toast.LENGTH_SHORT).show();

                    }

                } catch (ParseException e) {
                    // Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    swipeView.setRefreshing(false);
                    swipeView.setEnabled(false);
                }
                if (swipeView.isRefreshing()) {
                    System.out.println("false ");
                    swipeView.setRefreshing(false);
                    swipeView.setEnabled(false);
                }
            }
            if (tag.equals("movie_info")) {
                System.out.println(result);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(result);
                    JSONObject data = (JSONObject) obj;
                    if (data.get("status").toString().equals("movie")) {
                        Intent intent = new Intent(home.this, movie_info.class);
                        intent.putExtra("movie", data.toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "something wrong ,Try again..", Toast.LENGTH_SHORT).show();
                        prgDialog.dismiss();
                    }


                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }


        }
    }

    public void addToList(final JSONObject film) {

        search_card.setId(Integer.valueOf(film.get("id").toString()));
        ImageView mov_img = (ImageView) search_card.findViewById(R.id.movie_img);
        TextView mov_title = (TextView) search_card.findViewById(R.id.movie_title);
        TextView mov_year = (TextView) search_card.findViewById(R.id.movie_year);
        TextView vote_count = (TextView) search_card.findViewById(R.id.vote_count);
        TextView vote_avg = (TextView) search_card.findViewById(R.id.vote_avg);
        LoadImage load = new LoadImage();
        load.Image(mov_img);
        load.context = home.this;
        load.execute("http://image.tmdb.org/t/p/w300" + film.get("poster_path"));

        mov_title.setText("Title : " + film.get("original_title").toString());
        mov_year.setText("Year : " + film.get("release_date").toString());
        vote_count.setText("vote : " + film.get("vote_count").toString());
        vote_avg.setText("average : " + film.get("vote_average").toString());
        global_list_View.addView(search_card);
        final String jsonText = film.toJSONString();
        search_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hey");
                Intent intent = new Intent(home.this, movie_info.class);
                intent.putExtra("movie", jsonText);
                startActivity(intent);
            }
        });
        search_card = layoutInflater.inflate(R.layout.search_card, null);
    }

    public void addReviewToList(JSONObject review, String mov_name, int index) {
        review_card.setId(index);
        TextView mov_title = (TextView) review_card.findViewById(R.id.movie_title);
        TextView rev_author = (TextView) review_card.findViewById(R.id.review_author);
        TextView rev_contnet = (TextView) review_card.findViewById(R.id.review_content);
        mov_title.setText("Title : " + mov_name);
        System.out.println(review.get("author").toString());
        rev_author.setText("Author : " + review.get("author").toString());
        System.out.println(review.get("content").toString());
        rev_contnet.setText("Content : \n" + review.get("content").toString());
        list_View.addView(review_card);
        review_card = layoutInflater.inflate(R.layout.review_card, null);
    }

    public void addlastreviewtolist(JSONObject review) {
        lastreview_card.setId(Integer.valueOf(review.get("rev_id").toString()));
        TextView mov_name = (TextView) lastreview_card.findViewById(R.id.movie_title);
        TextView review_title = (TextView) lastreview_card.findViewById(R.id.review_title);
        TextView rev_data = (TextView) lastreview_card.findViewById(R.id.review_date);
        TextView rev_contnet = (TextView) lastreview_card.findViewById(R.id.review_content);
        TextView review_author = (TextView) lastreview_card.findViewById(R.id.review_author);
        ImageButton rev_open_mov = (ImageButton) lastreview_card.findViewById(R.id.review_open_mov_btn);

        rev_open_mov.setId(Integer.valueOf(review.get("mov_id").toString()));
        rev_open_mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
                Connect conn = new Connect();
                JSONObject jobj = new JSONObject();
                jobj.put("movieid", view.getId());
                conn.data = "data=" + jobj.toString();
                conn.tag = "movie_info";
                conn.execute(Url + "/Get_Info");
                System.out.println(conn.data);
            }
        });


        mov_name.setText("Movie : " + review.get("mov_name").toString());
        review_title.setText("Title : " + review.get("review_title").toString());
        rev_data.setText("Date : " + review.get("date").toString());
        review_author.setText("Author : " + review.get("username").toString());

        rev_contnet.setText("Description : " + review.get("content").toString());
        reveiw_list.addView(lastreview_card);

        lastreview_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
                Intent intent = new Intent(home.this, show_review.class);
                intent.putExtra("rev_id", view.getId());
                startActivity(intent);

            }
        });

        lastreview_card = layoutInflater.inflate(R.layout.review_card_user, null);
    }

    public void addusersToList(JSONObject user) {
        user_card.setId(Integer.valueOf(user.get("User_Id").toString()));
        TextView userName = (TextView) user_card.findViewById(R.id.user_name);
        TextView userScore = (TextView) user_card.findViewById(R.id.user_score);
        ImageView userImage = (ImageView) user_card.findViewById(R.id.user_img);

        userName.setText("Name : " + user.get("Name").toString());
        userScore.setText("Score : " + user.get("Score").toString());

        if (!user.get("Image").equals("none")) {
            LoadImage load = new LoadImage();
            load.Image(userImage);
            load.context = home.this;
            load.execute("http://watchandrateimage.comxa.com/User_image/" + user.get("Image"));
        }
        toplist.addView(user_card);
        user_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, user_reviews.class);
                intent.putExtra("userid",view.getId());

                startActivity(intent);
            }
        });

        user_card = layoutInflater.inflate(R.layout.user_card, null);

    }

}


