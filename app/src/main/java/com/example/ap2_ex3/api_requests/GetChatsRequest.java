package com.example.ap2_ex3.api_requests;

import com.example.ap2_ex3.entities.User;

public class GetChatsRequest {
    private int id;
    private UserRequest user;
    private MessageRequest lastMessage;

    public GetChatsRequest(int id, UserRequest user, MessageRequest lastMessage){
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public int getId() {
        return id;
    }

    public UserRequest getUser() {
        return user;
    }

    public MessageRequest getLastMessage() {
        return lastMessage;
    }
}
