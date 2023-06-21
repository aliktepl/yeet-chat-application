package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;

public class User {
    private final String username;
    private final String displayName;
    private final int picture;

    public User(String username, String displayName, int picture) {
        this.username = username;
        this.displayName = displayName;
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPicture() {
        return picture;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", picture=" + picture +
                '}';
    }

}
