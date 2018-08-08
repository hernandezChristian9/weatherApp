package com.christian_hernandez.medilink.weatherapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * Created by christian_hernandez on 8/3/2018.
 */

public class SplashActivity extends Activity {
    protected int _splashTime = 3000;

    private Thread splashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, _splashTime);

        //setContentView(R.layout.activity_main);
        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //finish();
    }
}
