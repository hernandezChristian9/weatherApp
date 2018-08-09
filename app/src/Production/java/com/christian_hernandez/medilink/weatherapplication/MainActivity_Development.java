package com.christian_hernandez.medilink.weatherapplication;

import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.christian_hernandez.medilink.weatherapplication.Api.GlobalVariables;
import com.christian_hernandez.medilink.weatherapplication.Fragment.MainFragment;
import com.christian_hernandez.medilink.weatherapplication.Fragment.WeatherDetailsFragment;


/**
 * Created by christian_hernandez on 8/9/2018.
 */

public class MainActivity_Development extends AppCompatActivity {
    private FragmentManager fragManager;
    private FragmentTransaction fragTransaction;
    private static final int FRAGMENT_COUNT = 2;
    DrawerLayout mDrawer;

    private static final int MAIN = 0;
    private static final int DETAILS = 1;
    private int currentFragmentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragManager = getFragmentManager();

        MainFragment weatherDetailsFragment = new MainFragment();
        FragmentTransaction transaction = fragManager.beginTransaction();
        transaction.replace(R.id.container, weatherDetailsFragment, "A");
        transaction.commit();

    }
}
