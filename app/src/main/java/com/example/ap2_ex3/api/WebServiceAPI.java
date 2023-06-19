package com.example.ap2_ex3.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // TOKEN services
    @POST("Tokens")
    Call<Token> createToken(@Body User user);

    // USER services
    @POST("Users")
    Call<Void> createUser(@Body User user);

    @GET("Users/{id}")
    Call<User> getUser(@Path("id") String id);

    // CHAT services

}
