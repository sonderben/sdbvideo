package com.sonderben.sdbvideo.repository;

import com.sonderben.sdbvideo.data.model.Access;
import com.sonderben.sdbvideo.data.model.Country;
import com.sonderben.sdbvideo.data.model.ResponseSignUp;
import com.sonderben.sdbvideo.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserSignUpRepository {
    @GET("access")
    Call<List<Access>> getAccesses();
    @POST("client/sign_up")
    Call<ResponseSignUp> signUp(@Body User user);

    @Headers("X-CSCAPI-KEY: bjM2TFNDN29lMHNxTFA2SjdsN0NnY2JIenFxbEExc2hDM2plMngyTg==")
    @GET("countries")
    Call<List<Country>> getCountries();

    //Get the list of states within country
    @Headers("X-CSCAPI-KEY: bjM2TFNDN29lMHNxTFA2SjdsN0NnY2JIenFxbEExc2hDM2plMngyTg==")
    @GET("countries/{iso}/states")
    Call<List<Country.State>> getStatesWithinCountry(@Path("iso") String iso2Country);

    //Get the list of states within country
    @Headers("X-CSCAPI-KEY: bjM2TFNDN29lMHNxTFA2SjdsN0NnY2JIenFxbEExc2hDM2plMngyTg==")
    @GET("countries/{iso}/states/{state}/cities")
    Call<List<Country.City>> getCityWithinCountryAndState(@Path("iso") String iso2Country,@Path("state") String iso2State);

    //Get the list of states within country
    @Headers("X-CSCAPI-KEY: bjM2TFNDN29lMHNxTFA2SjdsN0NnY2JIenFxbEExc2hDM2plMngyTg==")
    @GET("countries/{iso}")
    Call<Country.Detail> getDetailsCountry(@Path("iso") String iso2Country);
}
