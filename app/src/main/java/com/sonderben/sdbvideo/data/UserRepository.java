package com.sonderben.sdbvideo.data;


import com.sonderben.sdbvideo.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserRepository {


    //@GET("http://service.com/movies/list")
    //Single<JsonElement> getMovieList(@Query("movie_lang") String userLanguage);


    //@GET("/client/login?email={em}&password={pwd}&device={de}&location={lo}")

    @GET("client/login")
    Call<User.Response> login(@Query("email") String email, @Query("password") String password,
                       @Query("device") String device, @Query("location") String location);




    @GET("/client/profiles?{email}")
    Call<String> getProfiles(@Path("email") String email);
   // private static volatile UserRepository instance;








}