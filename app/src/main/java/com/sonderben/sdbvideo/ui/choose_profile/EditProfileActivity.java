package com.sonderben.sdbvideo.ui.choose_profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
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
        String urlImg="https://ejemploht.s3.us-east-2.amazonaws.com/img-profile/ersenb.png";
        Picasso.get()
                .load(urlImg)
                .fit()
                .centerCrop()
                .into(imgProfile);
        profileName.setText("Phanor");
        profilePine.setText("8765");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  new AlertDialog.Builder(getApplicationContext()).setTitle("class")
                        .setMessage("class: "+getIntent().getExtras().getClass()).show()
                ;*/
            }
        });
    }
    ActivityEditProfileBinding binding;
    TextInputEditText profileName,profilePine;
    ImageView imgProfile;
    TextView save;
}