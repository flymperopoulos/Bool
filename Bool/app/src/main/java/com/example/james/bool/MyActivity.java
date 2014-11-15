package com.example.james.bool;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;


public class MyActivity extends Activity {

    ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new GroupFragment();
    Fragment fragmentTab2 = new MainPageFragment();
    Fragment fragmentTab3 = new ResultFragment();
    ActionBar actionBar;

    ArrayList<String> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        questions = new ArrayList<String>();

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        tab1 = actionBar.newTab().setIcon(R.drawable.ic_social_people);
//        tab2 = actionBar.newTab().setIcon(R.drawable.ic_maps_map);
//        tab3 = actionBar.newTab().setIcon(R.drawable.ic_image_timer_auto);

        tab1 = actionBar.newTab().setText("Group");
        tab2 = actionBar.newTab().setText("Questions");
        tab3 = actionBar.newTab().setText("Result");

        tab1.setTabListener(new MyTabListener(fragmentTab1));
        tab2.setTabListener(new MyTabListener(fragmentTab2));
        tab3.setTabListener(new MyTabListener(fragmentTab3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.selectTab(tab2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_questions:

                return true;
            case R.id.action_settings:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
