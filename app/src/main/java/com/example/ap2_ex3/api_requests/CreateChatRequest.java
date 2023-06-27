package com.example.ap2_ex3.api_requests;

public class CreateChatRequest {
    private int id;
    private UserRequest user;

    public CreateChatRequest(int id, UserRequest user){
        this.id = id;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public UserRequest getUser() {
        return user;
    }

}
