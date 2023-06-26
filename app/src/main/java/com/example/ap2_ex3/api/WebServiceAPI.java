package com.example.ap2_ex3.api;

import com.example.ap2_ex3.activities.Username;
import com.example.ap2_ex3.api_requests.CreateChatRequest;
import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.GetChatsRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.api_requests.MessageRequest;
import com.example.ap2_ex3.api_requests.UserRequest;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.User;

import java.util.List;

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
    Call<UserRequest> getUser(@Path("username") String id, @Header("Authorization") String token);

    // CHAT services
    @GET("Chats")
    Call<List<GetChatsRequest>> getChats(@Header("Authorization") String token);

    @POST("Chats")
    Call<CreateChatRequest> createChat(@Header("Authorization") String token, @Body Username username);

    // MESSAGE services
    @GET("Chats/{id}/Messages")
    Call<List<MessageRequest>> getMessages(@Header("Authorization") String token, @Path("id") Integer chatId);

    @POST("Chats/{id}/Messages")
    Call<MessageRequest> createMessage(@Path("id") Integer msgId
            , @Body MessageRequest messageRequest, @Header("Authorization") String token);
}
