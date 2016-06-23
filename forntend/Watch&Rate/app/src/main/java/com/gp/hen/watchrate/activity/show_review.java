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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
 * Created by hosam azzam on 17/06/2016.
 */
public class show_review extends Activity {

    SlidingPaneLayout mSlidingPanel;
    SlidingPanel slide;
    String Url;
    ImageView rev_img;
    TextView rev_title, rev_aother, rev_des;
    LinearLayout List_view;
    View comment_card;
    LayoutInflater layoutInflater;
    ProgressDialog prgDialog;
    UserEntity user;
    int rev_id;
    int flagecomment=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review_comments);
        ActionBar ab = getActionBar();
        assert ab != null;
        ab.hide();

        user = UserEntity.getCurrentUser();

        //     Url="http://10.0.3.2:8080/Watch_and_Rate";
//        Url ="http://192.168.1.6:8080/Watch_and_Rate";
        Url = "http://watchandrate-fcigp.rhcloud.com";

        // <editor-fold defaultstate="collapsed" desc=" Sliding Panel Layout. Click on the + sign on the left to edit the code.">
        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(show_review.this);
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

        List_view = (LinearLayout) findViewById(R.id.commentlistView);

        layoutInflater = LayoutInflater.from(this);

        comment_card = layoutInflater.inflate(R.layout.user_comment_card, null);


        Intent intent = getIntent();
         rev_id = intent.getIntExtra("rev_id", 0);
        Connect conn = new Connect();
        JSONObject jobj = new JSONObject();
        jobj.put("rev_id", rev_id);
        conn.data = "data=" + jobj.toString();
        conn.execute(Url + "/ReviewInfo");
        System.out.println(conn.data);


        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading...");
        prgDialog.setCancelable(false);
        try {
            prgDialog.show();
        }catch (Exception e){

        }

        rev_img = (ImageView) findViewById(R.id.review_img);
        rev_aother = (TextView) findViewById(R.id.review_author);
        rev_title = (TextView) findViewById(R.id.review_title);
        rev_des = (TextView) findViewById(R.id.review_desc);

        ImageButton comm_btn = (ImageButton) findViewById(R.id.comment_add_btn);
        final EditText comment_box = (EditText) findViewById(R.id.comment_box);

        comm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!comment_box.getText().equals("")){
                    if(flagecomment==1) {
                        Connect conn = new Connect();
                        JSONObject jobj = new JSONObject();
                        jobj.put("revid", rev_id);
                        jobj.put("userid", user.getUser_Id());
                        jobj.put("hasurl", "0");
                        jobj.put("content", comment_box.getText().toString());
                        conn.data = "data=" + jobj.toString();
                        conn.execute(Url + "/Write_comment");
                        System.out.println(conn.data);
                        prgDialog.show();
                        comment_box.setText("");
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Sorry comments disable for this review", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //  rev_rate = (RatingBar) findViewById(R.id.review_rate);

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
                if (data.get("status").toString().equals("reviewinfo")) {


                    rev_aother.setText(data.get("username").toString());
                    rev_title.setText("Title : " + data.get("review_title").toString());

                    rev_des.setText("Description :- \n" + data.get("content").toString());

                    int commt_flage = Integer.valueOf(data.get("rev_comment").toString());
                    flagecomment=commt_flage;
                    if (commt_flage == 1) {
                        Connect conn = new Connect();
                        JSONObject jobj = new JSONObject();
                        jobj.put("rev_id", data.get("rev_id"));
                        conn.data = "data=" + jobj.toString();
                        conn.execute(Url + "/GetComments");
                        System.out.println(conn.data);
                    } else {
                        prgDialog.dismiss();
                    }

                    if (!data.get("rev_img").toString().equals("none")) {
                        LoadImage load = new LoadImage();
                        load.Image(rev_img);
                        load.context = show_review.this;
                        load.execute("http://watchandrateimage.comxa.com/post_image/" + data.get("rev_img"));
                    }

                }
                else if (data.get("status").toString().equals("commentlist")) {
                    result = data.get("results").toString();

                    obj = parser.parse(result);
                    JSONArray comments = (JSONArray) obj;
                    for (int i = 0; i < comments.size(); i++) {
                        obj = parser.parse(comments.get(i).toString());
                        JSONObject comment = (JSONObject) obj;
                        System.out.println(comment.toString());
                        addCommentToList(comment);
                    }
                    prgDialog.dismiss();
                    if (comments.size() == 0) {
                        Toast.makeText(getApplicationContext(), "this reviews don't have any comments.", Toast.LENGTH_SHORT).show();

                    }

                }
              else  if(data.get("status").toString().equals("inserted")){
                    Toast.makeText(getApplicationContext(), "Your comment added successfully", Toast.LENGTH_SHORT).show();
                    List_view.removeAllViews();
                    Connect conn = new Connect();
                    JSONObject jobj = new JSONObject();
                    jobj.put("rev_id",rev_id );
                    conn.data = "data=" + jobj.toString();
                    conn.execute(Url + "/GetComments");
                    System.out.println(conn.data);
                }
                else{
                    Toast.makeText(getApplicationContext(), "something wrong ,Try again..", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                prgDialog.dismiss();
                e.printStackTrace();
            }
        }
    }

    public void addCommentToList(JSONObject comment) {
        comment_card.setId(Integer.valueOf(comment.get("commnet_id").toString()));
        TextView user_name = (TextView) comment_card.findViewById(R.id.user_name);
        TextView content = (TextView) comment_card.findViewById(R.id.commnet_content);
        ImageView userimage = (ImageView) comment_card.findViewById(R.id.user_img);

        user_name.setText(comment.get("username").toString());
        content.setText(comment.get("content").toString());
        System.out.println("con " + content.getText());

        if (!comment.get("userimage").toString().equals("none")) {
            LoadImage load = new LoadImage();
            load.Image(userimage);
            load.tag=1;
            load.context = getApplicationContext();
            load.execute("http://watchandrateimage.comxa.com/User_image/" + comment.get("userimage").toString());
        }
        List_view.addView(comment_card);
        comment_card = layoutInflater.inflate(R.layout.user_comment_card, null);
    }
}

