package com.example.ap2_ex3.api;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.activities.Username;
import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.api_requests.UserRequest;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.User;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    public UserAPI(UserDao userDao) {
        // init dao
        this.userDao = userDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(AppContext.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wsAPI = retrofit.create(WebServiceAPI.class);
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
        Call<ResponseBody> getTokenCall = wsAPI.createToken(loginRequest);
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
    public void getUser(String username, String token, MutableLiveData<User> myUser) {
        Call<UserRequest> getUserCall = wsAPI.getUser(username, "Bearer " + token);
        getUserCall.enqueue(new Callback<UserRequest>() {
            @Override
            public void onResponse(@NonNull Call<UserRequest> call, @NonNull Response<UserRequest> response) {
                if (response.isSuccessful()) {
                    UserRequest userRequest = response.body();
                    assert userRequest != null;
                    User user = new User(userRequest.getUsername(), userRequest.getDisplayName(),
                            userRequest.getProfPic(), token, 1);
                    myUser.setValue(user);
                    new Thread(() -> userDao.insert(user)).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserRequest> call,@NonNull Throwable t) {

            }
        });
    }
}
