package com.dh.mediaplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by DH on 4/21/2015.
 */
public class SplashScreenActivity extends Activity {
    static private int SPLASH_TIME = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // put the splash screen into full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //Finish the splash activity so it can't be returned to.
                SplashScreenActivity.this.finish();

                // Create an Intent that will start the main activity.
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
            }
        }, SPLASH_TIME);
    }
}
