package com.example.ap2_ex3.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.activities.Username;
import com.example.ap2_ex3.api_requests.CreateChatRequest;
import com.example.ap2_ex3.api_requests.GetChatsRequest;
import com.example.ap2_ex3.api_requests.UserRequest;
import com.example.ap2_ex3.database.ChatDao;
import com.example.ap2_ex3.entities.Chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {

    // service api and retrofit fields
    private Retrofit retrofit;
    private WebServiceAPI wsAPI;

    // dao fields
    private ChatDao chatDao;

    public ChatAPI(ChatDao chatDao) {
        // init database
        this.chatDao = chatDao;
        // init service and retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(AppContext.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wsAPI = retrofit.create(WebServiceAPI.class);
    }

    // Update your API method
    public void getChats(String token) {
//        String token = myUser.getToken();
        Call<List<GetChatsRequest>> call = wsAPI.getChats("Bearer " + token);
        call.enqueue(new Callback<List<GetChatsRequest>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetChatsRequest>> call, @NonNull Response<List<GetChatsRequest>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    // iterate all chats for logged in user
                    for (GetChatsRequest chat : response.body()) {
                        Chat newChat;
                        if (chat.getLastMessage() != null) {
                            newChat = new Chat(chat.getId(), chat.getUser().getUsername()
                                    , chat.getUser().getProfPic(), chat.getLastMessage().getContent(),
                                    formatDate(chat.getLastMessage().getCreated()));
                        } else {
                            newChat = new Chat(chat.getId(), chat.getUser().getUsername()
                                    , chat.getUser().getProfPic(), null, null);
                        }
                        new Thread(() -> {chatDao.Insert(newChat);}).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetChatsRequest>> call, Throwable t) {
                Log.d("getChats", "failed");
            }
        });
    }



    public void createChat(String token, MutableLiveData<Integer> status,
                            String username){
        Username usernameObj = new Username(username);
        Call<CreateChatRequest> call = wsAPI.createChat("Bearer " + token, usernameObj);
        call.enqueue(new Callback<CreateChatRequest>() {
            @Override
            public void onResponse(@NonNull Call<CreateChatRequest> call,@NonNull Response<CreateChatRequest> response) {
                if(response.isSuccessful()){
                    CreateChatRequest c = response.body();
                    assert c != null;
                    UserRequest u = c.getUser();
                    Chat new_chat = new Chat(c.getId(), u.getUsername(), u.getProfPic(), null, null);
                    new Thread(() -> {chatDao.Insert(new_chat);}).start();
                } else if(response.code() == 400) {
                    status.setValue(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateChatRequest> call,@NonNull Throwable t) {
                status.setValue(2);
            }
        });
    }

    private String formatDate(String dateString) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());

        try {
            Date date = inputDateFormat.parse(dateString);
            assert date != null;
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return an empty string in case of parsing error
    }
}
