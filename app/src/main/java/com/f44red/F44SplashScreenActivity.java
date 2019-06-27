package com.f44red;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Splash screen for loading application during its running.
 * @author Paweł Turoń
 */

public class F44SplashScreenActivity extends Activity {
    private static final int TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActivityStarter starter = new ActivityStarter();
        starter.start();
    }

    private class ActivityStarter extends Thread {
        @Override
        public void run(){
            try {
                Thread.sleep(TIME);
            } catch (Exception e) {
                Log.e("SplashScreen error: ", e.getMessage());
            }

            Intent intent = new Intent(F44SplashScreenActivity.this, MainActivity.class);
            F44SplashScreenActivity.this.startActivity(intent);
            F44SplashScreenActivity.this.finish();
        }
    }
}
