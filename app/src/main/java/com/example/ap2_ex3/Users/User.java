package com.example.ap2_ex3.Users;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate=true)
    private String username;
    private String password;
    private String displayName;
    private double profPic;

    public User(String u, String p, String d, double pic){
        this.username = u;
        this.password = p;
        this.displayName = d;
        this.profPic = pic;
    }

    public double getProfPic() {
        return profPic;
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
}
