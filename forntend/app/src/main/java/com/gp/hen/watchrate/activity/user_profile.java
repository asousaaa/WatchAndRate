package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.hen.watchrate.Connection;
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.UserEntity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by hosam azzam on 11/06/2016.
 */
public class user_profile extends Activity {
    UserEntity user;
    EditText name, email, pass, score;
   public String Url;
    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ActionBar ab= getActionBar();

        assert ab != null;
        ab.hide();
        Url="http://10.0.3.2:8080/Watch_and_Rate";
        // Url ="http://192.168.1.6:8080/Watch_and_Rate";
        //   Url = "http://watchandrate-fcigp.rhcloud.com";

        Button save_btn,delete_btn;
        ImageButton image_bnt = (ImageButton) findViewById(R.id.chosse_image_btn);
        ImageView image;
        user = UserEntity.getCurrentUser();

        name = (EditText) findViewById(R.id.user_name);
        email = (EditText) findViewById(R.id.user_email);
        pass = (EditText) findViewById(R.id.user_pass);
        image = (ImageView) findViewById(R.id.user_img);
        score = (EditText) findViewById(R.id.user_score);

        delete_btn = (Button) findViewById(R.id.delete_account_btn);




        save_btn = (Button) findViewById(R.id.update_btn);


        name.setText(user.getName());
        pass.setText(user.getPass());
        email.setText(user.getEmail());


        score.setText("Score : "+String.valueOf(user.getScore()));
        // image not handel yet

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("loool");
                Connect conn = new Connect();
                JSONObject jobj = new JSONObject();

                jobj.put("email", email.getText().toString());
                jobj.put("pass", pass.getText().toString());
                jobj.put("username", name.getText().toString());
                jobj.put("userid", user.getUser_Id());
                jobj.put("userimage", user.getImage());
                conn.data = "data=" + jobj.toString();
                conn.execute(Url+"/Update_profile");
                System.out.println(conn.data);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("loool");
                Connect conn = new Connect();
                JSONObject jobj = new JSONObject();
                jobj.put("userid", user.getUser_Id());
                conn.data = "data=" + jobj.toString();
                conn.execute(Url+"/Delete_account");
                System.out.println(conn.data);
            }
        });

        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(user_profile.this);
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
                System.out.println("hhhh");
                mSlidingPanel.openPane();
            }
        });
        // Sliding Panel Layout ( END )


    }


    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "";

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

                JSONObject object = (JSONObject) obj;
                if (object.get("status").toString().equals("updated")) {
                    Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    user.setName(name.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setPass(pass.getText().toString());
                    slide.init();
                }
                if(object.get("status").toString().equals("failed")){
                    Toast.makeText(getApplicationContext(), "Something wrong ,Try again..", Toast.LENGTH_SHORT).show();
                }
                if(object.get("status").toString().equals("deleted")){
                    Toast.makeText(getApplicationContext(), "Account deleted sorry for say that :D don't come back again :P", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(user_profile.this,StartActivity.class);
                    finish();
                    startActivity(intent);

                }




            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}