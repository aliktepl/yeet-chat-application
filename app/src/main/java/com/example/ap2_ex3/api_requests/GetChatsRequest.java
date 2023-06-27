package com.example.ap2_ex3.api_requests;

public class GetChatsRequest {
    private int id;
    private UserRequest user;
    private GetMessageRequest lastMessage;

    public GetChatsRequest(int id, UserRequest user, GetMessageRequest lastMessage){
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

    public GetMessageRequest getLastMessage() {
        return lastMessage;
    }
}
