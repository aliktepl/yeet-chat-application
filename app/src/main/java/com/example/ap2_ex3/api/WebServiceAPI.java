package com.example.ap2_ex3.api;

import com.example.ap2_ex3.entities.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // TOKEN services
    @POST("Tokens")
    Call<ResponseBody> createToken(@Body LoginRequest userLogin);

    // USER services
    @POST("Users")
    Call<Void> createUser(@Body CreateUserRequest createRequest);

    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String id, @Header("Authorization") String token);

    //TODO: CHAT services

}
