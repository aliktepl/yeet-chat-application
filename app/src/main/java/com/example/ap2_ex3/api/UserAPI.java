package com.example.ap2_ex3.api;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserAPI {
    private Retrofit retrofit;
    private WebServiceAPI wsAPI;

    public UserAPI(Application application){
        retrofit = new Retrofit.Builder()
                .baseUrl(application.getBaseContext().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wsAPI = retrofit.create(WebServiceAPI.class);
    }

    // create a new user with the api and store live data variable to check request status
    public LiveData<Boolean> createUser(CreateUserRequest createUserRequest) {
        MutableLiveData<Boolean> createUserLiveData = new MutableLiveData<>();

        Call<Void> call = wsAPI.createUser(createUserRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    createUserLiveData.setValue(true); // User created successfully
                } else {
                    createUserLiveData.setValue(false); // User creation failed
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                createUserLiveData.setValue(false); // User creation failed
            }
        });

        return createUserLiveData;
    }

    // Request to get token from the API
    public LiveData<String> getToken(LoginRequest loginRequest) {
        MutableLiveData<String> token = new MutableLiveData<>();
        Call<String> getTokenCall = wsAPI.createToken(loginRequest);
        getTokenCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    token.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                token.setValue(null); // User login failed
            }
        });
        return token;
    }
    // Request to get a user from the API
    public LiveData<User> getUser(String username, String token){
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        Call<User> getUserCall = wsAPI.getUser(username, "Bearer " + token);
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                if(response.isSuccessful()){
                    userLiveData.setValue(response.body());
                } else {
                    userLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {
                userLiveData.setValue(null);
            }
        });
        return userLiveData;
    }


}
