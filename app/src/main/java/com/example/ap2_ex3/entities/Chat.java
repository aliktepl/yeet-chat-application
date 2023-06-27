package com.example.ap2_ex3.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a chat between users.
 */
@Entity(tableName = "chat_table")
public class Chat {
    @PrimaryKey
    private int id; // Unique identifier for the chat
    private String recipient; // Foreign key to the User entity
    private String recipientProfPic;
    private String lstMsgContent;
    private String lstMsgTime;

    public Chat(int id, String recipient, String recipientProfPic, String lstMsgContent, String lstMsgTime) {
        this.id = id;
        this.recipient = recipient;
        this.recipientProfPic = recipientProfPic;
        this.lstMsgContent = lstMsgContent;
        this.lstMsgTime = lstMsgTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientProfPic() {
        return recipientProfPic;
    }

    public void setRecipientProfPic(String recipientProfPic) {
        this.recipientProfPic = recipientProfPic;
    }

    public String getLstMsgContent() {
        return lstMsgContent;
    }

    public void setLstMsgContent(String lstMsgContent) {
        this.lstMsgContent = lstMsgContent;
    }

    public String getLstMsgTime() {
        return lstMsgTime;
    }

    public void setLstMsgTime(String lstMsgTime) {
        this.lstMsgTime = lstMsgTime;
    }


}

