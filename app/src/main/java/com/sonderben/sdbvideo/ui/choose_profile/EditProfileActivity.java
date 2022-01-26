package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sonderben.sdbvideo.MainActivity2;
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

import java.io.Serializable;
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


        imgProfile.setOnClickListener(this::onClick);
        iconEdit.setOnClickListener(this::onClick);
        Intent intent = getIntent();
        preferences = Preferences.getPreferenceInstance(this);

        if (intent.getStringExtra("MODE").equals("EDIT")) {
            save.setText("Update");
            idProfile = intent.getLongExtra("id", -1);
            tryUrl = intent.getStringExtra("url");
             name = intent.getStringExtra("name");
             pin = intent.getStringExtra("pin");
            boolean isMainProfile = intent.getBooleanExtra("main_profile", false);
             age = intent.getIntExtra("age", -1);
            String lang = intent.getStringExtra("lang");

            Toast.makeText(this,"ismain: "+isMainProfile,Toast.LENGTH_LONG).show();
            setViewsForEdit();

            if(preferences.getIsMainProfilesPreferences()){
                if(isMainProfile)
                    delete.setVisibility(View.GONE);
                if(!preferences.getIdProfile().equals(idProfile)){
                    imgProfile.setEnabled(false);
                    iconEdit.setEnabled(false);
                    profileName.setEnabled(false);
                    profileName.setFocusable(false);
                    profilePine.setEnabled(false);
                    profilePine.setFocusable(false);
                }
            }


            /*if (!isMainProfile) {
                profileAge.setEnabled(false);
                ageLayout.setHelperText("You don't have the permission to edit this field");

            }
            if (isMainProfile) {
                delete.setVisibility(View.GONE);

            }
            else
                delete.setVisibility(View.VISIBLE);
            save.setText("Update");*/
        } else if (intent.getStringExtra("MODE").equals("CREATE")) {
            profileName.setText("");
            profileAge.setText("+18");
            profilePine.setText("");
            delete.setEnabled(false);
        }

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Profile profile = new Profile();
                profile.setId(idProfile);
                profile.setName(profileName.getText().toString());
                profile.setAgeCategory(Integer.valueOf(profileAge.getText().toString()));
                profile.setDefaultLanguage("En");
                profile.setPin(profilePine.getText().toString());


                profile.setUrlImg(tryUrl);

                postProfile(preferences.getIdProfile(), profile, preferences.getToken());


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

                        if (preferences.getIdProfile().equals(idProfile)) {
                            preferences.setIdProfile(-1L);

                            Intent intent1 = new Intent(EditProfileActivity.this, SplashScreenActivity.class);
                            EditProfileActivity.this.startActivity(intent1);
                            EditProfileActivity.this.finishAffinity();
                        } else {
                            setResult(RESULT_OK);
                            EditProfileActivity.this.finish();
                        }

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

        });
    }

    private void setViewsForEdit() {
        Picasso.get()
                .load(tryUrl)
                .fit()
                .centerCrop()
                .into(imgProfile);
        profileName.setText(name);
        profilePine.setText(pin);
        profileAge.setText("+" + age);
    }

    ActivityEditProfileBinding binding;
    TextInputEditText profileName, profilePine, profileAge;
    TextInputLayout ageLayout;
    ImageView imgProfile, iconEdit;
    TextView save, delete;
    Preferences preferences;


    Long idProfile;
    AdapterImagesEditProfileActivity[] adapter;
    String tryUrl;
    String name;
    String pin;
    int age;

    @Override
    public void onClick(View view) {

        if (view.equals(imgProfile) || view.equals(iconEdit)) {
            getAllImageProfile(tryUrl);
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
                    AlertDialog alertDialog = getImages(response.body(), urlProfilePicture);
                    alertDialog.show();

                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            for (int i = 0; i < adapter.length; i++) {
                                if (adapter[i].getUrlImg() != null) {
                                    tryUrl = adapter[i].getUrlImg();

                                    Picasso.get()
                                            .load(tryUrl)
                                            .fit()
                                            .centerCrop()
                                            .into(imgProfile);

                                    break;
                                }
                            }
                        }
                    });
                }

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
        adapter = new AdapterImagesEditProfileActivity[categoryImage.size()];

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
            adapter[a] = new AdapterImagesEditProfileActivity(EditProfileActivity.this, imageListTemp, alertDialog);
            recyclerView[a].setAdapter(adapter[a]);
            recyclerView[a].setLayoutManager(new LinearLayoutManager(EditProfileActivity.this, RecyclerView.HORIZONTAL, false));
            root.addView(recyclerView[a]);
        }
        alertDialog.setView(root);

        alertDialog.getWindow().setLayout((int) (700), (int) (900));
        alertDialog.setOnDismissListener(dialogInterface -> {

            Picasso.get()
                    .load(tryUrl)
                    .fit()
                    .centerCrop()
                    .into(imgProfile);
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
                    int act = getIntent().getIntExtra("ACTIVITY", -1);

                    if (act == 11) {

                        startActivity(new Intent(EditProfileActivity.this, MainActivity2.class));
                        EditProfileActivity.this.finish();
                        return;
                    }
                    setResult(RESULT_OK);
                    //Toast.makeText(EditProfileActivity.this,"act: "+act,Toast.LENGTH_LONG).show();
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


}