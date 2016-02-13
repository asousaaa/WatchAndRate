package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.gp.hen.watchrate.R;
import com.gp.hen.watchrate.model.UserEntity;

/**
 * Created by esraa ahmed on 11/30/2015.
 */
public class home extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar ab = getActionBar();
        ab.hide();
        UserEntity user = UserEntity.getCurrentUser();
        System.out.println(user.getName()+" "+user.getEmail());
    }
}
