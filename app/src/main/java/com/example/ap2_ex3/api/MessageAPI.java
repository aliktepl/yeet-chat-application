package com.example.ap2_ex3.api;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api_requests.MessageRequest;
import com.example.ap2_ex3.database.MessageDao;
import com.example.ap2_ex3.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private Retrofit retrofit;
    private WebServiceAPI wsAPI;
    private MessageDao messageDao;

    public MessageAPI(MessageDao messageDao){
        this.messageDao = messageDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(AppContext.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wsAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getMessages(Integer chatId, MutableLiveData<Integer> status, String token){
        Call<List<MessageRequest>> getMsgCall = wsAPI.getMessages("Bearer " + token, chatId);
        getMsgCall.enqueue(new Callback<List<MessageRequest>>() {
            @Override
            public void onResponse(@NonNull Call<List<MessageRequest>> call, @NonNull Response<List<MessageRequest>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    for(MessageRequest msg : response.body()){
                        Message new_msg = new Message(msg.getId(), chatId, msg.getCreated(),
                                msg.getSender().getUsername(), msg.getContent());
                        new Thread(() -> messageDao.insert(new_msg)).start();
                    }
                } else if(response.code() == 401){
                    status.setValue(4);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MessageRequest>> call,@NonNull Throwable t) {
                status.setValue(500);
            }
        });
    }

    public void createMessage(Integer chatId, String msgContent, String token, MutableLiveData<Integer> status){
        Call<MessageRequest> createMsgCall = wsAPI.createMessage(chatId, msgContent, token);
        createMsgCall.enqueue(new Callback<MessageRequest>() {
            @Override
            public void onResponse(@NonNull Call<MessageRequest> call,@NonNull Response<MessageRequest> response) {
                if(response.isSuccessful()){
                    MessageRequest msgReq = response.body();
                    Message new_msg = new Message(msgReq.getId(), chatId, msgReq.getCreated(),
                            msgReq.getSender().getUsername(), msgReq.getContent());
                    new Thread(() -> { messageDao.insert(new_msg);}).start();
                } else if(response.code() == 401){
                    // chat does not exist
                    status.setValue(3);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageRequest> call,@NonNull Throwable t) {
                status.setValue(500);
            }
        });
    }

}
