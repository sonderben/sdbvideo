package com.sonderben.sdbvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.sonderben.sdbvideo.ui.choose_profile.ChooseProfileActivity;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(this);
        setContentView(R.layout.activity_splash_screen);

        Preferences preferences= Preferences.getPreferenceInstance(this);



        Intent intent;
        //if(preferences.getToken().isEmpty()){
            intent=new Intent(SplashScreenActivity.this, ChooseProfileActivity.class);
       // }
        /*else{
            intent=new Intent(SplashScreenActivity.this,MainActivity.class);
        }*/



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                //SplashScreenActivity.this.finish();
            }
        }, 2000);


    }






}