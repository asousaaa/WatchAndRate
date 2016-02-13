package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gp.hen.watchrate.Connection;
import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.UserEntity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class StartActivity extends Activity {


    Button signup, login, sign_in;
    RelativeLayout list;
    String gender = "male";
    Button sign_up;
    ImageView logo;
    Button male, female, gender_button;
    EditText fname, lname, email, pass, year, address, collage, gender_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ActionBar ab = getActionBar();
           ab.hide();
        logo = (ImageView) findViewById(R.id.logo);
        signup = (Button) findViewById(R.id.sign_up_button);
        sign_in = (Button) findViewById(R.id.sign_in_button);
        list = (RelativeLayout) findViewById(R.id.listView);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.removeAllViews();
                sign_in.setText("Login");
                logo.setBackground(getResources().getDrawable(R.drawable.logo_no_titel));
                signup.setBackground(getResources().getDrawable(R.drawable.tab_box));
                sign_in.setBackground(getResources().getDrawable(R.drawable.button_box));

                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View tab_signup = layoutInflater.inflate(R.layout.tab_signup, null);
                list.addView(tab_signup);

                sign_up = (Button) tab_signup.findViewById(R.id.signup_button);

                fname = (EditText) tab_signup.findViewById(R.id.User_name_box);

                email = (EditText) tab_signup.findViewById(R.id.Email_box);
                pass = (EditText) tab_signup.findViewById(R.id.password_box);

                sign_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // System.out.println("jjj");
                        if (fname.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Please fill all boxes", Toast.LENGTH_SHORT).show();
                        } else {
                            Connect conn = new Connect();
                            JSONObject jobj = new JSONObject();

                            jobj.put("username", fname.getText().toString());
                            jobj.put("email", email.getText().toString());
                            jobj.put("pass", pass.getText().toString());

                            conn.data = "data=" + jobj.toString();
                            conn.execute("http://10.0.3.2:8080/Watch_and_Rate/Sign_up");
                        }
                    }
                });


            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.removeAllViews();
                sign_in.setText("Sign in");
                logo.setBackground(getResources().getDrawable(R.drawable.logo));
                signup.setBackground(getResources().getDrawable(R.drawable.button_box));
                sign_in.setBackground(getResources().getDrawable(R.drawable.tab_box));

                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View tab_login = layoutInflater.inflate(R.layout.tab_login, null);
                list.addView(tab_login);

                Button forget = (Button) findViewById(R.id.forget);
                forget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                final EditText email = (EditText) tab_login.findViewById(R.id.email_login);
                final EditText pass = (EditText) tab_login.findViewById(R.id.password_login);

                login = (Button) findViewById(R.id.login_button);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (email.getText().toString().equals("") || pass.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Please fill all boxes", Toast.LENGTH_SHORT).show();
                        } else {
                            Connect conn = new Connect();
                            JSONObject jobj = new JSONObject();

                            jobj.put("email", email.getText().toString());
                            jobj.put("pass", pass.getText().toString());

                            conn.data = "data=" + jobj.toString();
                            conn.execute("http://10.0.3.2:8080/Watch_and_Rate/Sign_in");
                        }

                    }
                });


            }
        });
    }

    public class Connect extends AsyncTask<String, Void, Void> {
        String result, data = "";

        protected Void doInBackground(String... strings) {
            //   System.out.println("dfklsdk;sd");
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
                if (object.get("status").toString().equals("done")) {
                    Intent intent = new Intent(StartActivity.this, home.class);
                    finish();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "your account added ,Now you can use our app", Toast.LENGTH_SHORT).show();
                }
                else if (object.get("status").toString().equals("login")) {
                    UserEntity activeuser= UserEntity.getCurrentUser();
                    activeuser.setCurrentUser(object);
                    Intent intent = new Intent(StartActivity.this, home.class);
                    finish();
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), object.get("status").toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
    }


}
