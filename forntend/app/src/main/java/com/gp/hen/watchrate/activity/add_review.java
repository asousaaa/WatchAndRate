package com.gp.hen.watchrate.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gp.hen.watchrate.R;

/**
 * Created by hosam azzam on 25/04/2016.
 */
public class add_review extends Activity {
    SlidingPaneLayout mSlidingPanel;
    ListView mMenuList;
    Button bbt;
    String [] MenuTitles = new String[]{"First Item","Second Item","Third Item","Fourth Item"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        ActionBar ab = getActionBar();
        ab.hide();
        ImageButton menu = (ImageButton) findViewById((R.id.menu_btn));
        bbt = (Button) findViewById(R.id.bbt);
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mMenuList = (ListView) findViewById(R.id.MenuList);

        mMenuList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,MenuTitles));

        mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(200);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hhhh");
                mSlidingPanel.openPane();
            }
        });
    }
    PanelSlideListener panelListener = new PanelSlideListener(){

        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub

        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub

        bbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("lool");
            }
        });
        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }

    };

}
