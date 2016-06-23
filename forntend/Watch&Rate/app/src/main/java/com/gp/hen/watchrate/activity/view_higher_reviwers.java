package com.gp.hen.watchrate.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gp.hen.watchrate.LoadImage;
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.ReviewEntity;
import com.gp.hen.watchrate.model.UserEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by noha magdy on 22-Jun-16.
 */
public class view_higher_reviwers extends Activity
{
    View user_card;
    LayoutInflater layoutInflater;
    LinearLayout list_View;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        layoutInflater= LayoutInflater.from(this);
        user_card= layoutInflater.inflate(R.layout.user_card, null);
        list_View = (LinearLayout) findViewById(R.id.userlistView);
        String Url = "http://watchandrate-fcigp.rhcloud.com";

        Bundle extras = getIntent().getExtras();
        String array = extras.getString("array");
        JSONParser parser = new JSONParser();
        JSONObject json=new JSONObject();
        JSONArray usersArray=new JSONArray();

        try
        {
            json = (JSONObject) parser.parse(array);
            usersArray=(JSONArray)json.get("results");
        }
        catch (Exception e)
        {}

        //System.out.println("reviews "+json.toString());
        ArrayList<String> userNames=new ArrayList<String>();
        ArrayList<String> userScores=new ArrayList<String>();
        ArrayList<String> userImages=new ArrayList<String>();
        ArrayList<String> userIDs=new ArrayList<String>();

        try
        {
            for(int i=0;i<usersArray.size();i++)
            {
                JSONObject object=new JSONObject();
                object = (JSONObject)usersArray.get(i);

                String userName=object.get("Name").toString();
                String userScore=object.get("Score").toString();
                String userImage=object.get("Image").toString();
                String userID=object.get("User_Id").toString();

                userIDs.add(userID);
                userNames.add(userName);
                userScores.add(userScore);
                userImages.add(userImage);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0;i<userNames.size();i++)
        {
            user_card.setId(Integer.valueOf(userIDs.get(i)));
            TextView userName = (TextView) user_card.findViewById(R.id.user_name);
            TextView userScore = (TextView) user_card.findViewById(R.id.user_score);
            ImageView userImage = (ImageView) user_card.findViewById(R.id.user_img);

            userName.setText(userNames.get(i));
            userScore.setText(userScores.get(i));
            LoadImage load = new LoadImage();
            load.Image(userImage);
            if(userImages.get(i).contains(".jpeg") || userImages.get(i).contains(".png"));
                load.execute("http://watchandrateimage.comxa.com/User_image/"+userImages.get(i));

            list_View.addView(user_card);
            user_card = layoutInflater.inflate(R.layout.user_card, null);
        }
    }
}
