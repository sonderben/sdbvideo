package com.sonderben.sdbvideo.repository;

import com.sonderben.sdbvideo.data.model.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ProfileRepository {
    /*
   @Headers({ "Content-Type: application/json;charset=UTF-8"})
@GET("api/Profiles/GetProfile")
Call<UserProfile> getUser(@Query("id") String id, @Header("Authorization") String auth);
     */

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("client/profiles")
    Call<List<Profile>> getProfiles(@Query("email") String email,@Header("Authorization") String auth );

}
