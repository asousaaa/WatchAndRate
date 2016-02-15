package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by esraa ahmed on 11/30/2015.
 */
public class home extends Activity {
    RelativeLayout search_content;
    LinearLayout list_View;
    LayoutInflater layoutInflater;
    View search_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar ab = getActionBar();
        ab.hide();
        UserEntity user = UserEntity.getCurrentUser();
        System.out.println(user.getName() + " " + user.getEmail());
        //http://image.tmdb.org/t/p/w300/hO1R1TI429PjkOjby4dTPBrWFwn.jpg
        //http://api.themoviedb.org/3/movie/260346?api_key=23386b0753dd348bcb87ab9f516da5d5

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
         layoutInflater = LayoutInflater.from(this);
         search_card = layoutInflater.inflate(R.layout.search_card, null);

        list_View = (LinearLayout) findViewById(R.id.searchlistView);
        search_content = (RelativeLayout) findViewById(R.id.search_table);
        ImageButton search_btn = (ImageButton) findViewById(R.id.search_btn);
        final EditText search_box = (EditText) findViewById(R.id.search_box);
        search_content.setAnimation(animation);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list_View.removeAllViews();
                search_content.setVisibility(View.VISIBLE);
                Connect conn = new Connect();
                conn.tag = "api";
                String query = "http://api.themoviedb.org/3/search/movie?api_key=23386b0753dd348bcb87ab9f516da5d5&query=" + search_box.getText().toString();
               query = query.replaceAll(" ","%20");
                conn.execute(query);
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (search_content.getVisibility() == View.VISIBLE) {
            search_content.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }


    }

    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "", tag = "";

        protected Void doInBackground(String... strings) {
            //   System.out.println("dfklsdk;sd");
            result = new Connection().sendrequest(strings[0], data);
            return null;
        }
        // oly en code dh gahz 3shn m7sesh en 7omara :D Asma2 l fun ah bs el content la :v la mo25za wnta 3rft mnnen klam dh
        // search w mtnsesh ane android developer :P aywa 3rfa :3 bs 3rft 7tt api w klam dh mnen wresult l fo2
        // de mesh api d code 3ade bta3 t send request w teget data men server msh dh so2ale ma 3lina  hwa fen code servie

        protected void onPostExecute(Void unused) {
            if (tag.equals("api")) {
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
                    for (int i = 0; i < movies.size(); i++) {
                        obj = parser.parse(movies.get(i).toString());
                        JSONObject film = (JSONObject) obj;
                        System.out.println(film.toString());
                        addToList(film, i);
                    }
                    if(movies.size()==0){
                        Toast.makeText(getApplicationContext(),"no result", Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public void addToList(JSONObject film,int index) {
        search_card.setId(Integer.valueOf(index));
        ImageView mov_img = (ImageView) search_card.findViewById(R.id.movie_img);
        TextView mov_title = (TextView) search_card.findViewById(R.id.movie_title);
        TextView mov_year = (TextView) search_card.findViewById(R.id.movie_year);
        TextView vote_count = (TextView) search_card.findViewById(R.id.vote_count);
        TextView vote_avg = (TextView) search_card.findViewById(R.id.vote_avg);
        LoadImage load = new LoadImage();
        load.Image(mov_img);
        load.execute("http://image.tmdb.org/t/p/w300" + film.get("poster_path"));
        mov_title.setText("Title : "+film.get("original_title").toString());
        mov_year.setText("Year : "+film.get("release_date").toString());
        vote_count.setText("vote : "+film.get("vote_count").toString());
        vote_avg.setText("average : "+film.get("vote_average").toString());
                list_View.addView(search_card);
        search_card = layoutInflater.inflate(R.layout.search_card, null);
    }
}
