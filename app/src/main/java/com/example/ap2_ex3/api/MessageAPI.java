package com.example.ap2_ex3.api;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api_requests.CreateMsgReq;
import com.example.ap2_ex3.api_requests.CreateMsgRequest;
import com.example.ap2_ex3.api_requests.GetMessageRequest;
import com.example.ap2_ex3.api_requests.GetMsgReqByObj;
import com.example.ap2_ex3.database.ChatDao;
import com.example.ap2_ex3.database.MessageDao;
import com.example.ap2_ex3.entities.Message;

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
        Call<List<GetMsgReqByObj>> getMsgCall = wsAPI.getMessages("Bearer " + token, chatId);
        getMsgCall.enqueue(new Callback<List<GetMsgReqByObj>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetMsgReqByObj>> call, @NonNull Response<List<GetMsgReqByObj>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    for(GetMsgReqByObj msg : response.body()){
                        Message new_msg = new Message(msg.getId(), chatId, msg.getCreated(),
                                msg.getSender().getUsername(), msg.getContent());
                        new Thread(() -> messageDao.insert(new_msg)).start();
                    }
                } else if(response.code() == 401){
                    status.setValue(4);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetMsgReqByObj>> call, @NonNull Throwable t) {
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
                    Message new_msg = new Message(msgReq.getId(), chatId, formatDate(msgReq.getCreated()),
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

    private String formatDate(String dateString) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
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
