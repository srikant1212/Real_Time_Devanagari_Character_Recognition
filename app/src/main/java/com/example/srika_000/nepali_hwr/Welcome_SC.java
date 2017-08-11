package com.example.srika_000.nepali_hwr;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome_SC extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__sc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//notification bar hatauna


        //start of splash screen animation ------loading ko lagi
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.clock);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.anticlock);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        imageView.startAnimation(animation_1);
          animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.startAnimation(animation_1);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                imageView.startAnimation(animation_1);

            }
        });

        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.startAnimation(animation_2);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                imageView.startAnimation(animation_2);

            }
        });

        //end of splash screen animation


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent HomeIntent = new Intent(Welcome_SC.this,LoginRegisterMain.class);
                startActivity(HomeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}
