package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.sonderben.sdbvideo.MainActivity2;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.databinding.ActivityEnterPinBinding;
import com.sonderben.sdbvideo.ui.sign_up.SignUpPage3Fragment;
import com.sonderben.sdbvideo.utils.Preferences;
import com.squareup.picasso.Picasso;

public class EnterPinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEnterPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imgProfile=binding.imgProfile;
        nameProfile=binding.name;
        pinview=binding.pinView;
        errorMsg=binding.error;
        lock=binding.iconLock;

        preferences=Preferences.getPreferenceInstance(this);
        Intent intent=getIntent();
        Picasso.get()
                .load(intent.getStringExtra("url"))
                .fit()
                .centerCrop()
                .into(imgProfile);
        nameProfile.setText(intent.getStringExtra("name"));



        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                if(pinview.getValue().equals(intent.getStringExtra("pin"))){
                    Intent intent1=new Intent(EnterPinActivity.this, MainActivity2.class);
                    //lock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_unlock, 0, 0);

                    InputMethodManager imgr= (InputMethodManager) EnterPinActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imgr.hideSoftInputFromWindow(pinview.getApplicationWindowToken(),0);



                    preferences.setIdProfile(intent.getLongExtra("id",-1));
                    preferences.setIsMainProfilesPreferences(intent.getBooleanExtra("isMain",false));
                    lock.setImageDrawable(getDrawable(R.drawable.ic_unlock));
                    startActivity(intent1);

                }else{
                    errorMsg.setVisibility(View.VISIBLE);
                    pinview.setValue("");
                    pinview.setPinBackgroundRes(R.drawable.bg_pin_view_error);
                }
            }
        });

    }
    ActivityEnterPinBinding binding;
    ImageView imgProfile,lock;
    TextView nameProfile,errorMsg;
    Preferences preferences;
    Pinview pinview;

}