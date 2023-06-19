package com.example.ap2_ex3.Users;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate=false)
    @NonNull
    private String username;
    private String password;
    private String displayName;

    public User(String u, String p, String d){
        this.username = u;
        this.password = p;
        this.displayName = d;
    }

    public User(){

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
