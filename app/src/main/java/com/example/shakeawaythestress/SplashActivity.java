package com.example.shakeawaythestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
/*
Splash Screen that appears upon app startup
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ArrayList<String> quotes = new ArrayList<>();
        final TextView textView = findViewById(R.id.splashText);
        final ImageView imageView = findViewById(R.id.splashImage);
        //Load slide in animation
        Animation slideAnimation = AnimationUtils.loadAnimation(this,R.anim.side_slide);
        textView.startAnimation(slideAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
            }
        }, 2000);

    }
}