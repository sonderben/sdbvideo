package com.sonderben.sdbvideo.repository;

import com.sonderben.sdbvideo.data.model.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("profile/{profile}")
    Call<Profile> deleteProfile(@Path("profile") Long profile, @Header("Authorization") String auth );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("profile/{profile}")
    Call<Profile> saveProfile(@Path("profile") Long profile, @Body Profile prof, @Header("Authorization") String auth );

    @GET("image")
    Call<List<Profile.Image>> getImages();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("profile/{profile}")
    Call<Profile> updateProfile(@Path("profile") Long profile, @Body Profile prof, @Header("Authorization") String auth );

}
