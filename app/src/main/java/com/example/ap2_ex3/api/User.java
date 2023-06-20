package com.example.ap2_ex3.api;

public class User {

    private String username;
    private String displayName;

    private String profPic;

    public User(String username, String displayName, String profPic){
        this.username = username;
        this.displayName = displayName;
        this.profPic = profPic;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getUsername() {
        return username;
    }
    public String getProfPic() {
        return profPic;
    }
}
