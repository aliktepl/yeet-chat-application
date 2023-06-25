package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private String profPic;
    private String token;
    private int myUser;

    public User(@NonNull String username, String displayName, String profPic, String token, int myUser){
        this.username = username;
        this.displayName = displayName;
        this.profPic = profPic;
        this.token = token;
        this.myUser = myUser;
    }

    public String getDisplayName() {
        return displayName;
    }
    @NonNull
    public String getUsername() {
        return username;
    }
    @Nullable
    public String getProfPic() {
        return profPic;
    }
    public String getToken() {
        return token;
    }
    public int getMyUser() {
        return myUser;
    }
}
