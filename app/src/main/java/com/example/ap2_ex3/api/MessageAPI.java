package com.example.ap2_ex3.api;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api_requests.CreateMsgReq;
import com.example.ap2_ex3.api_requests.CreateMsgRequest;
import com.example.ap2_ex3.api_requests.GetMessageRequest;
import com.example.ap2_ex3.database.ChatDao;
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
        Call<List<GetMessageRequest>> getMsgCall = wsAPI.getMessages("Bearer " + token, chatId);
        getMsgCall.enqueue(new Callback<List<GetMessageRequest>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetMessageRequest>> call, @NonNull Response<List<GetMessageRequest>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    for(GetMessageRequest msg : response.body()){
                        Message new_msg = new Message(msg.getId(), chatId, msg.getCreated(),
                                msg.getSender().getUsername(), msg.getContent());
                        new Thread(() -> messageDao.insert(new_msg)).start();
                    }
                } else if(response.code() == 401){
                    status.setValue(4);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetMessageRequest>> call, @NonNull Throwable t) {
                status.setValue(500);
            }
        });
    }

    public void createMessage(Integer chatId, String msgContent, String token, MutableLiveData<Integer> status){
        CreateMsgReq msgReq = new CreateMsgReq(msgContent);
        Call<CreateMsgRequest> createMsgCall = wsAPI.createMessage("Bearer " + token, chatId, msgReq);
        createMsgCall.enqueue(new Callback<CreateMsgRequest>() {
            @Override
            public void onResponse(@NonNull Call<CreateMsgRequest> call, @NonNull Response<CreateMsgRequest> response) {
                if(response.isSuccessful()){
                    CreateMsgRequest msgReq = response.body();
                    assert msgReq != null;
                    Message new_msg = new Message(msgReq.getId(), chatId, msgReq.getCreated(),
                            msgReq.getSender(), msgReq.getContent());
                    new Thread(() -> { messageDao.insert(new_msg);}).start();
                } else if(response.code() == 401){
                    // chat does not exist
                    status.setValue(3);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateMsgRequest> call, @NonNull Throwable t) {
                status.setValue(500);
            }
        });
    }

}
