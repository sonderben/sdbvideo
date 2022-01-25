package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.SplashScreenActivity;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.adapter.AdapterEpo4PlayerView;
import com.sonderben.sdbvideo.adapter.AdapterImagesEditProfileActivity;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.databinding.ActivityEditProfileBinding;
import com.sonderben.sdbvideo.entity.Episode;
import com.sonderben.sdbvideo.repository.ProfileRepository;
import com.sonderben.sdbvideo.ui.login.LoginViewModel;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileName = binding.profileName;
        profilePine = binding.profilePin;
        imgProfile = binding.imgProfile;
        save = binding.save;
        delete = binding.delete;
        profileAge = binding.profileAge;
        ageLayout = binding.layProfileAge;
        iconEdit = binding.iconEdit;
        editProfileViewModel = new ViewModelProvider(this)
                .get(EditProfileViewModel.class);

        //intent.putExtra("MODE","CREATE");

        imgProfile.setOnClickListener(this::onClick);
        iconEdit.setOnClickListener(this::onClick);
        Intent intent = getIntent();

        if (intent.getStringExtra("MODE").equals("EDIT")) {

            idProfile = intent.getLongExtra("id", -1);
            //idProfile=idProfile.equals(-1L)?null:idProfile;
            url = intent.getStringExtra("url");
            String name = intent.getStringExtra("name");
            String pin = intent.getStringExtra("pin");
            boolean isMainProfile = intent.getBooleanExtra("main_profile", false);
            int age = intent.getIntExtra("age", -1);
            String lang = intent.getStringExtra("lang");

            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imgProfile);
            profileName.setText(name);
            profilePine.setText(pin);
            profileAge.setText("+" + age);
            if (!isMainProfile) {
                profileAge.setEnabled(false);
                //profileAge.setError("You don't have permission to edit it");
                ageLayout.setHelperText("You don't have the permission to edit this field");

            }
            if (isMainProfile)
                delete.setVisibility(View.GONE);
            else
                delete.setVisibility(View.VISIBLE);
            save.setText("Update");
        } else if (intent.getStringExtra("MODE").equals("CREATE")) {
            profileName.setText("");
            profileAge.setText("+18");
            profilePine.setText("");
        }

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        float mHeightScreen = mDisplayMetrics.heightPixels;
        float mWidthScreen = mDisplayMetrics.widthPixels;
        Preferences preferences = Preferences.getPreferenceInstance(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile();
                profile.setId(idProfile);
                profile.setName(profileName.getText().toString());
                profile.setAgeCategory(Integer.valueOf(profileAge.getText().toString()));
                profile.setDefaultLanguage("En");
                profile.setPin(profilePine.getText().toString());
                profile.setUrlImg(editProfileViewModel.getUrLiveData().getValue());
                postProfile(preferences.getIdProfile(), profile, preferences.getToken());
                //onBackPressed();
            }
        });
        delete.setOnClickListener(x -> {
            Retrofit retrofit = Utils.getInstanceRetrofit();
            ProfileRepository repository = retrofit.create(ProfileRepository.class);
            Call<Profile> profileCall = repository.deleteProfile(idProfile, "Bearer " + preferences.getToken());
            profileCall.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if (response.isSuccessful()) {
                        EditProfileActivity.this.finish();
                    } else
                        try {
                            new AlertDialog.Builder(EditProfileActivity.this).setMessage(response.errorBody().string()).show();
                        } catch (Exception e) {

                        }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });

            if(preferences.getIdProfile().equals(idProfile)){
                preferences.setIdProfile(-1L);
                //Utils.signOut(EditProfileActivity.this);
                Intent intent1=new Intent(EditProfileActivity.this, SplashScreenActivity.class);
                EditProfileActivity.this.startActivity(intent1);
                EditProfileActivity.this.finish();
            }
        });
    }

    ActivityEditProfileBinding binding;
    TextInputEditText profileName, profilePine, profileAge;
    TextInputLayout ageLayout;
    ImageView imgProfile, iconEdit;
    TextView save, delete;
    String url = null;
    EditProfileViewModel editProfileViewModel;
    Long idProfile;

    @Override
    public void onClick(View view) {
        if (view.equals(imgProfile) || view.equals(iconEdit)) {
            getAllImageProfile(url);
        }

    }

    private void getAllImageProfile(String urlProfilePicture) {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        ProfileRepository repository = retrofit.create(ProfileRepository.class);
        Call<List<Profile.Image>> callImage = repository.getImages();
        callImage.enqueue(new Callback<List<Profile.Image>>() {
            @Override
            public void onResponse(Call<List<Profile.Image>> call, Response<List<Profile.Image>> response) {
                if (response.isSuccessful()) {
                    //new AlertDialog.Builder(EditProfileActivity.this).setMessage("m laaaaa").show();
                    getImages(response.body(), urlProfilePicture).show();
                }
                //else
                //new AlertDialog.Builder(EditProfileActivity.this).setMessage("error m laaaaa").show();
            }

            @Override
            public void onFailure(Call<List<Profile.Image>> call, Throwable t) {

            }
        });
    }

    public android.app.AlertDialog getImages(List<Profile.Image> images, String urlProfilePicture) {

        android.app.AlertDialog.Builder dialodAddIncome = new android.app.AlertDialog.Builder(EditProfileActivity.this);
        AlertDialog alertDialog = dialodAddIncome.create();

        List<String> categoryImage = new ArrayList<>(5);
        for (int i = 0; i < images.size(); i++) {
            if (!categoryImage.contains(images.get(i).getCategory().trim()))
                categoryImage.add(images.get(i).getCategory().trim());
        }
        List<Profile.Image> imageListTemp;


        LinearLayout root = new LinearLayout(EditProfileActivity.this);
        root.setOrientation(LinearLayout.VERTICAL);
        TextView[] category = new TextView[categoryImage.size()];
        RecyclerView[] recyclerView = new RecyclerView[categoryImage.size()];

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int a = 0; a < categoryImage.size(); a++) {
            imageListTemp = new ArrayList<>(10);
            category[a] = new TextView(this);
            category[a].setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            lp.setMargins(20, 0, 0, 0);

            category[a].setLayoutParams(lp);
            category[a].setTypeface(null, Typeface.BOLD);
            category[a].setTextSize(24);
            recyclerView[a] = new RecyclerView(this);
            category[a].setText(categoryImage.get(a));
            root.addView(category[a]);
            for (int j = 0; j < images.size(); j++) {
                if (images.get(j).getCategory().trim().equals(categoryImage.get(a)))
                    imageListTemp.add(images.get(j));
            }
            recyclerView[a].setAdapter(new AdapterImagesEditProfileActivity(EditProfileActivity.this, imageListTemp, alertDialog));
            recyclerView[a].setLayoutManager(new LinearLayoutManager(EditProfileActivity.this, RecyclerView.HORIZONTAL, false));
            root.addView(recyclerView[a]);
        }
        alertDialog.setView(root);

        alertDialog.getWindow().setLayout((int) (700), (int) (900));
        alertDialog.setOnDismissListener(dialogInterface -> {

            editProfileViewModel.getUrLiveData().observe(EditProfileActivity.this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Picasso.get()
                            .load(s)
                            .fit()
                            .centerCrop()
                            .into(imgProfile);
                }
            });
        });
        alertDialog.setView(root);

        return alertDialog;
    }

    public void postProfile(Long idProfile, Profile profile, String token) {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        Call<Profile> profileCall = null;
        ProfileRepository repository = retrofit.create(ProfileRepository.class);
        if (profile.getId() == null)
            profileCall = repository.saveProfile(idProfile, profile, "Bearer " + token);
        else
            profileCall = repository.updateProfile(idProfile, profile, "Bearer " + token);
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    EditProfileActivity.this.finish();
                } else {
                    try {
                        new AlertDialog.Builder(EditProfileActivity.this).setMessage(response.errorBody().string()).show();
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                new AlertDialog.Builder(EditProfileActivity.this).setMessage(t.getMessage()).show();
            }
        });
    }
    /*public  void putProfile(Long idProfile,Profile profile,String token){
        Retrofit retrofit=Utils.getInstanceRetrofit();
        ProfileRepository repository=retrofit.create(ProfileRepository.class);
        Call<Profile>profileCall=repository.saveProfile(idProfile,profile,"Bearer "+token);
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()){
                    EditProfileActivity.this.finish();
                }
                else {
                    try {
                        new AlertDialog.Builder(EditProfileActivity.this).setMessage(response.errorBody().string()).show();
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                new AlertDialog.Builder(EditProfileActivity.this).setMessage(t.getMessage()).show();
            }
        });
    }*/


}