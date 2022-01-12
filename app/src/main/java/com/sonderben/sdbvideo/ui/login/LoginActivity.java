package com.sonderben.sdbvideo.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.data.UserRepository;
import com.sonderben.sdbvideo.data.model.User;
import com.sonderben.sdbvideo.databinding.ActivityLoginBinding;
import com.sonderben.sdbvideo.utils.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this)
                .get(LoginViewModel.class);


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;


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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*/
                a(usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                        "device", "location");
            }
        });
    }

    private void updateUiWithUser() {

    }

    private void a(String email, String pwd, String device, String location) {
        Retrofit retrofit = new Retrofit.Builder()//192.168.0.103
                .baseUrl("http://192.168.0.103:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserRepository userRepository = retrofit.create(UserRepository.class);
        Call<User.Response> call = userRepository.login(email, pwd, device, location);
        call.enqueue(new Callback<User.Response>() {
            @Override
            public void onResponse(Call<User.Response> call, Response<User.Response> response) {
               User.Response g= response.body();
                Toast.makeText(LoginActivity.this, "Login with success" , Toast.LENGTH_LONG).show();
                 Preferences.getPreferenceInstance(LoginActivity.this).setTokenPreferences(response.body().getToken());

                Intent intent=new Intent(LoginActivity.this, VideoPlayerActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User.Response> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.err.println("u malll");
                Log.i("mitag", "" + t.getMessage());
            }
        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {

    }
}