package com.christian_hernandez.medilink.weatherapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by christian_hernandez on 8/3/2018.
 */

public class SplashActivity_Development extends Activity {
    protected int _splashTime = 3000;

    private Thread splashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity_Development.this, MainActivity_Development.class);
                startActivity(i);

                finish();
            }
        }, _splashTime);

        //setContentView(R.layout.activity_main);
        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //finish();
    }
}
