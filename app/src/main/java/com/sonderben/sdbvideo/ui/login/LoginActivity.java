package com.sonderben.sdbvideo.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sonderben.sdbvideo.MainActivity2;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.data.UserRepository;
import com.sonderben.sdbvideo.data.model.UserLogin;
import com.sonderben.sdbvideo.databinding.ActivityLoginBinding;
import com.sonderben.sdbvideo.ui.choose_profile.ChooseProfileActivity;
import com.sonderben.sdbvideo.ui.choose_profile.EditProfileActivity;
import com.sonderben.sdbvideo.ui.sign_up.SignUpActivity;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private  TextView signup;
    String tempEmail;
    private TextInputLayout layPassword,layEmail;
    ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this)
                .get(LoginViewModel.class);


        //final EditText usernameEditText = binding.username;
        final TextInputEditText usernameEditText = binding.username;
        final TextInputEditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
          loadingProgressBar = binding.loading;
          layPassword=binding.layPassword;
          layEmail=binding.layUsername;
        signup=binding.signup;
        signup.setOnClickListener(x->{
            Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
            //Intent intent=new Intent(LoginActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                //       passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /*loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());*/
                }
                return false;
            }
        });

        loginButton.setEnabled(true);
        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            login(usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                    "device", "location");
        });
        tempEmail=usernameEditText.getText().toString();
    }

    private void updateUiWithUser() {

    }

    private void login(String email, String pwd, String device, String location) {
        Retrofit retrofit = new Retrofit.Builder()//192.168.0.103
                .baseUrl(Utils.baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserRepository userRepository = retrofit.create(UserRepository.class);
        Call<UserLogin.Response> call = userRepository.login(new UserLogin(email, pwd, device, location));
        call.enqueue(new Callback<UserLogin.Response>() {
            @Override
            public void onResponse(Call<UserLogin.Response> call, Response<UserLogin.Response> response) {
                if(response.isSuccessful()){

                    UserLogin.Response g= response.body();

                    Preferences preferences=Preferences.getPreferenceInstance(LoginActivity.this);
                    preferences.setTokenPreferences(g.getToken());
                    preferences.setEmailUserPreferences(tempEmail);
                   // new AlertDialog.Builder(LoginActivity.this).setMessage(preferences.getToken()+" token").show();

                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    Intent intent=new Intent(LoginActivity.this, ChooseProfileActivity.class);

                    intent.putExtra("Activity",this.getClass());
                    startActivity(intent);
                }
                else {
                    try {
                        JSONObject jsonError=new JSONObject(response.errorBody().string());
                        int status=jsonError.getInt("status");
                        if(status==404){
                            loadingProgressBar.setVisibility(View.GONE);
                            layEmail.setError(" ");
                            //layPassword.setHelperText("error on email or password!");
                            layPassword.setError("Error on email or password!");
                        }
                        String message= jsonError.getString("error");
                        Toast.makeText(LoginActivity.this, message+". error: "+status , Toast.LENGTH_LONG).show();
                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<UserLogin.Response> call, Throwable t) {


            }
        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {

    }
}