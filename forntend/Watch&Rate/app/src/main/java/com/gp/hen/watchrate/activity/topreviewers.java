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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by noha magdy on 15-Jun-16.
 */
public class topreviewers extends Activity {
    View user_card;
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
        slide.setContext(topreviewers.this);
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
        user_card = layoutInflater.inflate(R.layout.user_card, null);
        list_View = (LinearLayout) findViewById(R.id.userlistView);

        TextView header = (TextView) findViewById(R.id.header_title);
        header.setText("Top Reviewers");

        Connect conn = new Connect();
        conn.tag="higherReviewers";
        conn.execute("http://watchandrate-fcigp.rhcloud.com/higher_reviewers");
    }


    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "",tag="";

        protected Void doInBackground(String... strings) {
            result = new Connection().sendrequest(strings[0], data);
            return null;
        }

        protected void onPostExecute(Void unused) {
            if(tag.equals("higherReviewers")) {
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(result);
                    JSONObject data = (JSONObject) obj;
                    result = data.get("results").toString();
                    obj = parser.parse(result);
                    JSONArray users = (JSONArray) obj;
                    for (int i = 0; i < users.size(); i++) {
                        obj = parser.parse(users.get(i).toString());
                        JSONObject user = (JSONObject) obj;
                        addToList(user);
                    }
                    prgDialog.dismiss();
                    if (users.size() == 0)
                        Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                    prgDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }
    }


    public void addToList(final JSONObject user) {
        user_card.setId(Integer.valueOf(user.get("User_Id").toString()));
        TextView userName = (TextView) user_card.findViewById(R.id.user_name);
        TextView userScore = (TextView) user_card.findViewById(R.id.user_score);
        ImageView userImage = (ImageView) user_card.findViewById(R.id.user_img);

        userName.setText("Name : "+user.get("Name").toString());
        userScore.setText("Score : "+user.get("Score").toString());

        if(!user.get("Image").equals("none")) {
            LoadImage load = new LoadImage();
            load.Image(userImage);
            load.context = topreviewers.this;
            load.execute("http://watchandrateimage.comxa.com/User_image/" + user.get("Image"));
        }
        list_View.addView(user_card);

        user_card = layoutInflater.inflate(R.layout.user_card, null);

    }
}
