package com.example.ap2_ex3.api_requests;

import com.example.ap2_ex3.entities.User;

public class GetMessageRequest {
    private int id;
    private String created;
    private User sender;
    private String content;

    public GetMessageRequest(int id, String created, User sender, String content){
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }
}
