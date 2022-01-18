package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sonderben.sdbvideo.databinding.ActivityEditProfileBinding;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileName=binding.profileName;
        profilePine=binding.profilePin;
        imgProfile=binding.imgProfile;
        save=binding.save;
        profileAge=binding.profileAge;
        ageLayout=binding.layProfileAge;

        //intent.putExtra("MODE","CREATE");
        Intent intent=getIntent();

        if(intent.getStringExtra("MODE").equals("EDIT")){

            Long id= intent.getLongExtra("id",-1);
            String url=intent.getStringExtra("url");
            String name=intent.getStringExtra("name");
            String pin=intent.getStringExtra("pin");
            boolean isMainProfile=intent.getBooleanExtra("main_profile",false);
            int age=intent.getIntExtra("age",-1);
            String lang=intent.getStringExtra("lang");

            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imgProfile);
            profileName.setText(name);
            profilePine.setText(pin);
            profileAge.setText("+"+age);
            if(!isMainProfile) {
                profileAge.setEnabled(false);
                //profileAge.setError("You don't have permission to edit it");
                ageLayout.setHelperText("You don't have the permission to edit this field");
            }
        }else if(intent.getStringExtra("MODE").equals("CREATE")){
            profileName.setText("");
            profileAge.setText("+18");
            profilePine.setText("");
        }




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  new AlertDialog.Builder(getApplicationContext()).setTitle("class")
                        .setMessage("class: "+getIntent().getExtras().getClass()).show()
                ;*/
                onBackPressed();
            }
        });
    }
    ActivityEditProfileBinding binding;
    TextInputEditText profileName,profilePine,profileAge;
    TextInputLayout ageLayout;
    ImageView imgProfile;
    TextView save;
}