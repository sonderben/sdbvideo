package com.sonderben.sdbvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sonderben.sdbvideo.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(x->{
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

}