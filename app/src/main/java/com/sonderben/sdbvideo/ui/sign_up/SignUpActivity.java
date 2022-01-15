package com.sonderben.sdbvideo.ui.sign_up;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.adapter.AdapterViewPager4SignUp;
import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.Country;
import com.sonderben.sdbvideo.data.model.ResponseSignUp;
import com.sonderben.sdbvideo.data.model.User;
import com.sonderben.sdbvideo.databinding.ActivitySignUpBinding;
import com.sonderben.sdbvideo.repository.UserSignUpRepository;
import com.sonderben.sdbvideo.utils.Utils;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
           viewPager2=signUpBinding.viewPager2;
         final TextView nextPage=signUpBinding.nextPage;
        final TextView previousPage=signUpBinding.previousPage;
        signIn=signUpBinding.signIn;
        final SpringDotsIndicator springDotsIndicator =signUpBinding.springDotsIndicator;
         adapterViewPager4SignUp = new AdapterViewPager4SignUp(this);
         viewPager2.setAdapter(adapterViewPager4SignUp);
        springDotsIndicator.setViewPager2(viewPager2);
         viewPager2.setUserInputEnabled(false);

         signIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 System.out.println(user);
                 System.err.println(user);
                 Log.i("userm",""+user);

                 signUp(user);


             }
         });


        nextPage.setOnClickListener(this::onClick);
        previousPage.setOnClickListener(this::onClick);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position>0){
                    previousPage.setVisibility(View.VISIBLE);
                }
                else {
                    previousPage.setVisibility(View.INVISIBLE);
                }
                if(position<adapterViewPager4SignUp.getItemCount()-1 /*&& Utils.isEmailValid(mEmail)*/){
                    nextPage.setVisibility(View.VISIBLE);
                }
                else {
                    nextPage.setVisibility(View.INVISIBLE);
                }
                if(position==adapterViewPager4SignUp.getItemCount()-1)
                    signIn.setVisibility(View.VISIBLE);
                else
                    signIn.setVisibility(View.GONE);
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("SIGN_UP"));
    }


    ViewPager2 viewPager2;
    AdapterViewPager4SignUp adapterViewPager4SignUp;
    User user=new User();
    MaterialButton signIn;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next_page:
                if(viewPager2.getCurrentItem()<adapterViewPager4SignUp.getItemCount()-1) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
                break;
            case R.id.previous_page:
                if(viewPager2.getCurrentItem()>=1) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                }
                break;
        }
    }
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("SIGN_UP")){

                if(intent.getLongExtra("ID_ACCESS",0)!=0 )
                    user.setAccess(new Access(intent.getLongExtra("ID_ACCESS",0)));
                if(intent.getStringExtra("EMAIL")!=null )
                    user.setEmail(intent.getStringExtra("EMAIL"));
                if(intent.getStringExtra("FIRST_NAME")!=null )
                    user.setFirstName(intent.getStringExtra("FIRST_NAME"));
                if(intent.getStringExtra("LAST_NAME")!=null )
                    user.setLastName(intent.getStringExtra("LAST_NAME"));
                if(intent.getStringExtra("BIRTHDAY")!=null )
                    user.setBirthday(intent.getStringExtra("BIRTHDAY"));
                if(intent.getStringExtra("SEX")!=null )
                    user.setSex(intent.getStringExtra("SEX"));
                if(intent.getStringExtra("TELEPHONE")!=null )
                    user.setTelephone(intent.getStringExtra("TELEPHONE"));
                if(intent.getStringExtra("REGION")!=null )
                    user.setRegion(intent.getStringExtra("REGION"));
                if(intent.getStringExtra("CITY")!=null )
                    user.setCity(intent.getStringExtra("CITY"));
                if(intent.getStringExtra("COUNTRY")!=null )
                    user.setCountry(intent.getStringExtra("COUNTRY"));
                if(intent.getStringExtra("DEPARTMENT")!=null )
                    user.setDepartment(intent.getStringExtra("DEPARTMENT"));
                if(intent.getStringExtra("POSTAL_CODE")!=null )
                    user.setPostalCode(intent.getStringExtra("DEPARTMENT"));
                if(intent.getStringExtra("PASSWORD")!=null )
                    user.setPassword(intent.getStringExtra("PASSWORD"));

                user.setAllProfilesCanCreateNewProfile(intent.getBooleanExtra("CAN_CREATE_PROFILE",false));

            }

        }
    };

    private void signUp(User user) {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        UserSignUpRepository repository = retrofit.create(UserSignUpRepository.class);
        Call<ResponseSignUp> call = repository.signUp(user);

        call.enqueue(new Callback<ResponseSignUp>() {
            @Override
            public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {


                if(response.isSuccessful()){
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("Delete entry")
                            .setMessage(""+response.body())

                            .show();

                }
                else {
                    try {

                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("Delete entry")
                                .setMessage(""+response.errorBody().string())

                                .show();

                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<ResponseSignUp> call, Throwable t) {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("Delete entry")
                        .setMessage(""+t.getMessage()).show();
            }
        });
    }
}