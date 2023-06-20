package com.example.ap2_ex3.api;

public class CreateUserRequest {
    private String username;
    private String password;
    private String displayName;
    private String profPic;

    public CreateUserRequest(String username, String password, String displayName, String profPic){
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profPic = profPic;
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
    public String getProfPic() {
        return profPic;
    }
}
