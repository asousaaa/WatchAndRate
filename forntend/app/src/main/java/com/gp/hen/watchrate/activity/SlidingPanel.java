package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.UserEntity;

/**
 * Created by hosam azzam on 14/06/2016.
 */
public class SlidingPanel extends Activity {
    ImageView user_img;
    TextView user_name, user_score;
    Button logout, home, setting, profile, my_review, about;
    Context context;
    UserEntity activuser;
    SlidingPaneLayout mSlidingPanel;

    public void setmSlidingPanel(SlidingPaneLayout mSlidingPanel) {
        this.mSlidingPanel = mSlidingPanel;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setUser_img(ImageView user_img) {
        this.user_img = user_img;
    }

    public void setLogout(Button logout) {
        this.logout = logout;
    }

    public void setUser_name(TextView user_name) {
        this.user_name = user_name;
    }

    public void setUser_score(TextView user_score) {
        this.user_score = user_score;
    }

    public void setHome(Button home) {
        this.home = home;
    }

    public void setSetting(Button setting) {
        this.setting = setting;
    }

    public void setProfile(Button profile) {
        this.profile = profile;
    }

    public void setMy_review(Button my_review) {
        this.my_review = my_review;
    }

    public void setAbout(Button about) {
        this.about = about;
    }

    public void init() {

        activuser = UserEntity.getCurrentUser();
        //   user_img.setBackground(context.getResources().getDrawable(R.drawable.diary_icon));
        user_name.setText(activuser.getName());
        user_score.setText(String.valueOf(activuser.getScore()));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(context.getClass()).contains("home")) {
                    Intent intent = new Intent(context, home.class);
                    context.startActivity(intent);
                    mSlidingPanel.closePane();
                }
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sorry not develop yet ^_^", Toast.LENGTH_SHORT).show();
            }
        });

        my_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(context.getClass()).contains("user_reviews")) {
                    Intent intent = new Intent(context, user_reviews.class);
                    context.startActivity(intent);
                    mSlidingPanel.closePane();
                }

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(context.getClass()).contains("user_profile")) {
                    Intent intent = new Intent(context, user_profile.class);
                    context.startActivity(intent);
                    mSlidingPanel.closePane();
                }
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sorry not develop yet ^_^", Toast.LENGTH_SHORT).show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity.removeCurrentUser();
                Intent intent = new Intent(context, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                mSlidingPanel.closePane();
            }
        });


    }
}
