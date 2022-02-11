package com.sonderben.sdbvideo.repository;

import com.sonderben.sdbvideo.data.model.Category;
import com.sonderben.sdbvideo.data.model.Movie;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.data.model.SimpleFilm;
import com.sonderben.sdbvideo.data.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeneralRepository {
    /*
     @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("client/profiles")
    Call<List<Profile>> getProfiles(@Query("email") String email,@Header("Authorization") String auth );
     */

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("category")
    Call<List<Category>> getCategories(@Header("Authorization") String auth );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("movie/category")
    Call<List<SimpleFilm>> getMoviesByCategory(@Query("profile_id")Long profileId,
                                               @Query("category_code")int category_code,
                                               @Header("Authorization") String auth );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("videos/category/{code}")
    Call<List<Video>> getVideoByCategory(@Path("code") int code,
                                         @Query("profile")Long profileId,
                                         @Query("page")int page,
                                         @Header("Authorization") String auth );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("videos/description/{desc}")
    Call<List<Video>> getVideoByDescription(@Path("desc") String description,
                                         @Query("profile")Long profileId,
                                         @Query("page")int page,
                                         @Header("Authorization") String auth );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("movie/video/{video}")
    Call<Movie> getMovieByVideoId(@Path("video") Long idVideo,
                                        @Query("profile")Long profileId,
                                        @Header("Authorization") String auth );
}
















