package com.example.ap2_ex3.api_requests;

public class CreateMsgRequest {
    private int id;
    private String created;
    private String sender;
    private String content;

    public CreateMsgRequest(int id, String created, String sender, String content){
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }
}

