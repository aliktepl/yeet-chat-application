package com.example.ap2_ex3.api_requests;

public class CreateUserRequest {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public CreateUserRequest(String username, String password, String displayName, String profPic){
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profPic;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getProfPic() {
        return profilePic;
    }
}
