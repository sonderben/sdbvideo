package com.sonderben.sdbvideo.data;



import com.sonderben.sdbvideo.data.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserRepository {

   /* @GET("client/login")
    Call<UserLogin.Response> login(@Query("email") String email, @Query("password") String password,
                                   @Query("device") String device, @Query("location") String location);*/


    @POST("client/login")
    Call<UserLogin.Response> login(@Body UserLogin userLogin);




    @GET("client/profiles?{email}")
    Call<String> getProfiles(@Path("email") String email);
   // private static volatile UserRepository instance;








}