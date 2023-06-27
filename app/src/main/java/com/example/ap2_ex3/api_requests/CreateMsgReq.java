package com.example.ap2_ex3.api_requests;

public class CreateMsgReq {
    private String msg;

    public CreateMsgReq(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
