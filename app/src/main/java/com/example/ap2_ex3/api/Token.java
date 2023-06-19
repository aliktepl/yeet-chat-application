package com.example.ap2_ex3.api;

public class Token {
    private String token;

    public Token(String tokenStr){
        token = "Bearer " + tokenStr;
    }

    public String getToken() {
        return token;
    }
}
