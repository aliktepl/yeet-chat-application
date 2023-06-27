package com.example.ap2_ex3.api_requests;

public class UserRequest {
    private String username;
    private String displayName;
    private String profilePic;

    public UserRequest(String username, String displayName, String profPic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profPic;
    }

    public String getProfPic() {
        return profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }
}
