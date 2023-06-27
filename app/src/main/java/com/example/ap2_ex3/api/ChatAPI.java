package com.example.ap2_ex3.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.activities.Username;
import com.example.ap2_ex3.api_requests.CreateChatRequest;
import com.example.ap2_ex3.api_requests.GetChatsRequest;
import com.example.ap2_ex3.api_requests.MessageRequest;
import com.example.ap2_ex3.api_requests.UserRequest;
import com.example.ap2_ex3.database.ChatDao;
import com.example.ap2_ex3.database.MessageDao;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.User;

import java.util.List;

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
    private UserDao userDao;
    private MessageDao messageDao;

    public ChatAPI(ChatDao chatDao, UserDao userDao, MessageDao messageDao) {
        // init database
        this.chatDao = chatDao;
        this.userDao = userDao;
        this.messageDao = messageDao;
        // init service and retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(AppContext.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wsAPI = retrofit.create(WebServiceAPI.class);
    }

    // Define an interface for the callback
    interface InsertUserCallback {
        void onUserInserted();
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
                                    chat.getLastMessage().getCreated());
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
}
