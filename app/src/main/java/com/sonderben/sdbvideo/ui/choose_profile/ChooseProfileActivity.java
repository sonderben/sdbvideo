package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sonderben.sdbvideo.MainActivity;
import com.sonderben.sdbvideo.MainActivity2;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.SplashScreenActivity;
import com.sonderben.sdbvideo.adapter.AdapterProfile4ChooseProfileActivity;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.databinding.ActivityChooseProfileBinding;
import com.sonderben.sdbvideo.repository.ProfileRepository;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChooseProfileActivity extends AppCompatActivity /*implements View.OnClickListener*/ {

    private ActivityChooseProfileBinding chooseProfileBinding;
    RecyclerView recyclerView;
    AdapterProfile4ChooseProfileActivity adapter;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(this);
        chooseProfileBinding=ActivityChooseProfileBinding.inflate(getLayoutInflater());
        setContentView(chooseProfileBinding.getRoot());
        recyclerView=chooseProfileBinding.recyclerview;

      /*  if(preferences.getEmailUser().length()>10)
        getAllProfiles(preferences.getEmailUser());
        else{
            Intent intent=new Intent(ChooseProfileActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }*/
        preferences=Preferences.getPreferenceInstance(this);
         String token=preferences.getToken();



         if(token.length()>2 && preferences.getEmailUser().length()>2)
         getAllProfiles(preferences.getEmailUser(),token);
         else
             new AlertDialog.Builder(ChooseProfileActivity.this)
                     .setMessage("token: "+preferences.getToken()+" emamail: "+preferences.getEmailUser()).show();


    }

   /* @Override
    public void onClick(View view) {

        Intent intent;
        int ACTIVITY=getIntent().getIntExtra("Activity",-1);

*//*
        if(ACTIVITY==1)
         intent=new Intent(ChooseProfileActivity.this, MainActivity2.class);
       else
           intent=new Intent(ChooseProfileActivity.this,LoginActivity.class);*//*
       new AlertDialog.Builder(ChooseProfileActivity.this)
               .setMessage("code: "+getIntent().getExtras().get("ACTIVITY")).show();
       // startActivity(intent);
    }*/
    public void getAllProfiles(String email,String token){
        Retrofit retrofit=Utils.getInstanceRetrofit();
        ProfileRepository repository= retrofit.create(ProfileRepository.class);
        Call<List<Profile>> profiles=repository.getProfiles(email,"Bearer "+token);
        profiles.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if(response.isSuccessful()){
                    List<Profile>tempProfiles=response.body();
                    adapter=new AdapterProfile4ChooseProfileActivity(tempProfiles,ChooseProfileActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(ChooseProfileActivity.this,2));
                }else{
                    try {
                        new AlertDialog.Builder(ChooseProfileActivity.this)
                                .setMessage(response.errorBody().string() +token+"pa la ")
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {

            }
        });

    }
}