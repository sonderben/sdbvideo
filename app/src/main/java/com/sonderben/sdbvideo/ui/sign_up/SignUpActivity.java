package com.sonderben.sdbvideo.ui.sign_up;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.adapter.AdapterViewPager4SignUp;
import com.sonderben.sdbvideo.data.UserRepository;
import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.Country;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.data.model.ResponseSignUp;
import com.sonderben.sdbvideo.data.model.User;
import com.sonderben.sdbvideo.data.model.UserLogin;
import com.sonderben.sdbvideo.databinding.ActivitySignUpBinding;
import com.sonderben.sdbvideo.repository.UserSignUpRepository;
import com.sonderben.sdbvideo.ui.choose_profile.ChooseProfileActivity;
import com.sonderben.sdbvideo.ui.choose_profile.EditProfileActivity;
import com.sonderben.sdbvideo.ui.login.LoginActivity;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySignUpBinding signUpBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(this);
         signUpViewModel=new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        viewPager2 = signUpBinding.viewPager2;
        progressBarSignUp=signUpBinding.progressBarSignUp;
        final TextView nextPage = signUpBinding.nextPage;
        final TextView previousPage = signUpBinding.previousPage;
        signIn = signUpBinding.signIn;
        final SpringDotsIndicator springDotsIndicator = signUpBinding.springDotsIndicator;
        adapterViewPager4SignUp = new AdapterViewPager4SignUp(this);
        viewPager2.setAdapter(adapterViewPager4SignUp);
        springDotsIndicator.setViewPager2(viewPager2);
        viewPager2.setUserInputEnabled(false);
        preferences= Preferences.getPreferenceInstance(this);





        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifiedAndSiggnUp();


            }
        });


        nextPage.setOnClickListener(this::onClick);
        previousPage.setOnClickListener(this::onClick);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > 0) {
                    previousPage.setVisibility(View.VISIBLE);
                } else {
                    previousPage.setVisibility(View.INVISIBLE);
                }
                if (position < adapterViewPager4SignUp.getItemCount() - 1 /*&& Utils.isEmailValid(mEmail)*/) {
                    nextPage.setVisibility(View.VISIBLE);
                } else {
                    nextPage.setVisibility(View.INVISIBLE);
                }
                if (position == adapterViewPager4SignUp.getItemCount() - 1)
                    signIn.setVisibility(View.VISIBLE);
                else
                    signIn.setVisibility(View.GONE);
            }
        });

    }


    ViewPager2 viewPager2;
    AdapterViewPager4SignUp adapterViewPager4SignUp;
    MaterialButton signIn;
    Preferences preferences;

    SignUpViewModel signUpViewModel;
    ProgressBar progressBarSignUp;

    Profile mainProfileOfSignUpUser;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_page:
                if (viewPager2.getCurrentItem() < adapterViewPager4SignUp.getItemCount() - 1) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
                break;
            case R.id.previous_page:
                if (viewPager2.getCurrentItem() >= 1) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                }
                break;
        }
    }



    private void signUp(User user) {
        verify(user);


        Retrofit retrofit = Utils.getInstanceRetrofit();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<ResponseSignUp> call = repository.signUp(user);

        call.enqueue(new Callback<ResponseSignUp>() {
            @Override
            public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {


                if (response.isSuccessful()) {
                    ResponseSignUp responseSignUp=response.body();
                    Toast toast= Toast.makeText(SignUpActivity.this,"Sign up with success!\n" +
                            "logging into your account.",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();






                    preferences.setEmailUserPreferences(user.getEmail());
                     mainProfileOfSignUpUser=responseSignUp.getMainProfile();










                    progressBarSignUp.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login(user.getEmail(),user.getPassword(),"android samsung j7","Santo domingo");
                        }
                    },2000);


                } else {
                    try {

                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("Delete entry")
                                .setMessage("" + response.errorBody().string())

                                .show();

                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSignUp> call, Throwable t) {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("" + t.getMessage()).show();
            }
        });
    }
    private void login(String email, String pwd, String device, String location) {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        UserRepository userRepository = retrofit.create(UserRepository.class);
        Call<UserLogin.Response> call = userRepository.login(new UserLogin(email, pwd, device, location));
        call.enqueue(new Callback<UserLogin.Response>() {
            @Override
            public void onResponse(Call<UserLogin.Response> call, Response<UserLogin.Response> response) {
                if(response.isSuccessful()){

                    UserLogin.Response g= response.body();

                    Preferences preferences=Preferences.getPreferenceInstance(SignUpActivity.this);
                    preferences.setTokenPreferences(g.getToken());
                    preferences.setEmailUserPreferences(email);
                    progressBarSignUp.setVisibility(View.GONE);
                    // new AlertDialog.Builder(LoginActivity.this).setMessage(preferences.getToken()+" token").show();

                    /////////////////////////////////////////////////////////////////////////////////


                    Intent intent = new Intent(SignUpActivity.this, EditProfileActivity.class);
                    intent.putExtra("id", mainProfileOfSignUpUser.getId());
                    intent.putExtra("url", mainProfileOfSignUpUser.getUrlImg());
                    intent.putExtra("name", mainProfileOfSignUpUser.getName());
                    intent.putExtra("pin", mainProfileOfSignUpUser.getPin());
                    intent.putExtra("main_profile", mainProfileOfSignUpUser.getMainProfile());
                    intent.putExtra("age", mainProfileOfSignUpUser.getAgeCategory());
                    intent.putExtra("lang", mainProfileOfSignUpUser.getDefaultLanguage());
                    intent.putExtra("MODE","EDIT");
                    intent.putExtra("ACTIVITY",11);
                    Toast toast2=Toast.makeText(SignUpActivity.this,"almost done.\n Creating your profile.",Toast.LENGTH_LONG);
                    toast2.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            preferences.setIdProfile(mainProfileOfSignUpUser.getId());
                            startActivity(intent);
                            finishAffinity();
                        }
                    },2000);


                    /////////////////////////////////////////////////////////////////////////////////


                }
                else {
                    Toast.makeText(SignUpActivity.this,"Unable to log in automatically, please log in manually.",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                    progressBarSignUp.setVisibility(View.GONE);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserLogin.Response> call, Throwable t) {


            }
        });
    }

    public AlertDialog.Builder alert(String msg) {
        return new AlertDialog.Builder(SignUpActivity.this)
                .setTitle("Info")
                .setMessage(msg);
    }

    public void verifiedAndSiggnUp() {
        signUpViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                signUp(user);
            }
        });

    }

    public void verify(User user){
        if (user.getAccess() == null) {
            alert("Choose a plan").show();
            return ;
        }
        if (user.getBirthday() == null) {
           /* alert("Enter your birthday").show();
            return ;*/
        }
        if (user.getCity() == null) {
            alert("Choose a city").show();
            return ;
        }
        if (user.getCountry() == null) {
            alert("Choose a country").show();
            return ;
        }
        if (user.getDepartment() == null) {
            alert("Choose a State").show();
            return ;
        }
        if (user.getEmail() == null) {
            alert("Enter a email").show();
            return ;
        }
        if (user.getPassword() == null) {
            alert("Enter a password").show();
            return ;
        }
        if (user.getPostalCode() == null) {
            alert("Enter a postal code").show();
            return ;
        }
        if (user.getTelephone() == null) {
            alert("Enter a telephone number").show();
            return ;
        }
        if (user.getSex() == null) {
            alert("Enter a Sex").show();
            return ;
        }
    }
}