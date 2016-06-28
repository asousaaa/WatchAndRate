package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.test.suitebuilder.TestMethod;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.hen.watchrate.Connection;
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.RoundImage;
import com.gp.hen.watchrate.Upload;
import com.gp.hen.watchrate.model.UserEntity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hosam azzam on 25/04/2016.
 */
public class add_review extends Activity {
    SlidingPaneLayout mSlidingPanel;

    String Url;
    SlidingPanel slide;
    UserEntity user;
    String imgDecodableString = "";
    int RESULT_LOAD_IMG;
    ProgressDialog prgDialog;
    CheckBox enable_comt;
    EditText title;
    EditText content;
    TextView img_path;
    String uptime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        ActionBar ab = getActionBar();
        ab.hide();
        Intent intent = getIntent();
        String s = intent.getStringExtra("movie");
        System.out.println("s ? " + s);
        user = UserEntity.getCurrentUser();
        // Url = "http://10.0.3.2:8080/Watch_and_Rate";
//        Url ="http://192.168.1.6:8080/Watch_and_Rate";
        Url = "http://watchandrate-fcigp.rhcloud.com";

        // Sliding Panel Layout ( START )
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setParallaxDistance(200);

        slide = new SlidingPanel();
        slide.setContext(add_review.this);
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

        title = (EditText) findViewById(R.id.rev_titile);
        content = (EditText) findViewById(R.id.rev_content);
        img_path = (TextView) findViewById(R.id.rev_img_path);

        prgDialog = new ProgressDialog(this);


        ImageButton img_btn = (ImageButton) findViewById(R.id.image_rev_btn);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });


        enable_comt = (CheckBox) findViewById(R.id.enable_comment_ckbox);

        Button add_rev = (Button) findViewById(R.id.add_rev_btn);
        add_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!title.getText().equals("")&& !content.getText().equals("")) {
                    if (!imgDecodableString.equals("")) {
                        Upload image = new Upload();

                        prgDialog.setMessage("Uploading...");
                        prgDialog.setCancelable(false);
                        prgDialog.show();

                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
                        uptime = sdf.format(cal.getTime());

                        image.type = "review";
                        image.imgPath = imgDecodableString;
                        image.review = add_review.this;
                        image.uploadtime = uptime;
                        image.uploadImage();
                    } else {
                        img_path.setText("chosse image");
                        doneUploadImageState(1);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "please fill the fields", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                System.out.println("file " + imgDecodableString);
                String fileNameSegments[] = imgDecodableString.split("/");
                String fileName = fileNameSegments[fileNameSegments.length - 1];
                fileName = fileName.replaceAll(" ", "_");
                img_path.setText(fileName);


            } else {

            }
        } catch (Exception e) {

        }

    }


    public void doneUploadImageState(int flage) {
        if (flage == 1) {
            Intent intent = getIntent();
            String s = intent.getStringExtra("movie");
            JSONParser parser = new JSONParser();
            JSONObject json;
            try {
                json = (JSONObject) parser.parse(s);

                Connect conn = new Connect();
                JSONObject jobj = new JSONObject();

                jobj.put("title", title.getText().toString());
                jobj.put("content", content.getText().toString());
                if (img_path.getText().equals("chosse image")) {
                    img_path.setText("none");
                }
                else{
                    img_path.setText(user.getUser_Id()+uptime+img_path.getText());
                }
                jobj.put("image", img_path.getText().toString());
                jobj.put("rate", user.getUser_Id());
                jobj.put("userid", user.getUser_Id());
                jobj.put("movieid", json.get("id"));
                if (enable_comt.isChecked()) {
                    jobj.put("enable", "1");
                } else {
                    jobj.put("enable", "0");
                }
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = mdformat.format(calendar.getTime());


                jobj.put("userid", user.getUser_Id());
                jobj.put("date", strDate);

                jobj.put("mov_name", json.get("original_title"));
                jobj.put("type", "none");
                jobj.put("year", json.get("release_date"));
                String cleandesc = json.get("overview").toString().replaceAll("'", "\\\\'");

                System.out.println("cleaned " + cleandesc);
                jobj.put("desc", cleandesc);
                jobj.put("mov_img", json.get("poster_path"));
                jobj.put("mov_rate", json.get("vote_average"));
                jobj.put("mov_vote", json.get("vote_count"));
                jobj.put("story", "0");
                jobj.put("direction", "0");
                jobj.put("acting", "0");
                jobj.put("motion", "0");
                jobj.put("music", "0");
                conn.data = "data=" + jobj.toString();
                conn.execute(Url + "/writeReview");
                System.out.println(conn.data);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            prgDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Something wrong ,Try again or try with different photo..", Toast.LENGTH_SHORT).show();
        }
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
                JSONObject data = (JSONObject) obj;
                if (data.get("status").toString().equals("inserted")) {
                    prgDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Your review inserted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), data.get("status").toString(), Toast.LENGTH_SHORT).show();
                    prgDialog.dismiss();
                }
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Error!!, please check network or try again", Toast.LENGTH_SHORT).show();
                prgDialog.dismiss();
                e.printStackTrace();
            }

        }

    }
}



