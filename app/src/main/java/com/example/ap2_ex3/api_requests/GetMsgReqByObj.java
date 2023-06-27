package com.example.ap2_ex3.api_requests;

public class GetMsgReqByObj {
    private int id;
    private String created;
    private UserRequest sender;
    private String content;

    public GetMsgReqByObj(int id, String created, UserRequest sender, String content){
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public UserRequest getSender() {
        return sender;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }
}
