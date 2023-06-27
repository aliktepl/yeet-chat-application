package com.example.ap2_ex3.api;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.api_requests.UserRequest;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.User;
import com.google.firebase.messaging.FirebaseMessaging;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserAPI {
    private Retrofit retrofit;
    private WebServiceAPI wsAPI;
    private UserDao userDao;

    private String fbToken;

    public UserAPI(UserDao userDao, Application application) {
        // init dao
        this.userDao = userDao;
        SharedPreferences sharedSettings = application.getSharedPreferences(application.getString(R.string.settings_file_key) , Context.MODE_PRIVATE);
        String address = sharedSettings.getString("address", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(address)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wsAPI = retrofit.create(WebServiceAPI.class);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            fbToken = task.getResult();
        });
    }

    // create a new user with the api and store live data variable to check request status
    public void createUser(CreateUserRequest createUserRequest, MutableLiveData<Integer> status) {

        Call<Void> call = wsAPI.createUser(createUserRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                status.setValue(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                status.setValue(400); // User creation failed
            }
        });
    }

    // Request to get token from the API
    public void getToken(LoginRequest loginRequest,MutableLiveData<String> token,
                         MutableLiveData<Integer> status) {
        Call<ResponseBody> getTokenCall = wsAPI.createToken(loginRequest, fbToken);
        getTokenCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        token.setValue(response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    status.setValue(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                token.setValue(null);
            }
        });
    }

    // Request to get a user from the API
    public void getUser(String username, String token, MutableLiveData<Integer> status) {
        Call<UserRequest> getUserCall = wsAPI.getUser(username, "Bearer " + token);
        getUserCall.enqueue(new Callback<UserRequest>() {
            @Override
            public void onResponse(@NonNull Call<UserRequest> call, @NonNull Response<UserRequest> response) {
                if (response.isSuccessful()) {
                    UserRequest userRequest = response.body();
                    assert userRequest != null;
                    User user = new User(userRequest.getUsername(), userRequest.getDisplayName(),
                            userRequest.getProfPic(), token);
                    new Thread(() -> userDao.insert(user)).start();
                    status.setValue(1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserRequest> call,@NonNull Throwable t) {

            }
        });
    }
}
