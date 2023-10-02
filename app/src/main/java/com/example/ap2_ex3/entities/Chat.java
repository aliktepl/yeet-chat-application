package com.example.ap2_ex3.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

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
        try {
            LocalTime time = LocalTime.parse(lstMsgTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Format the LocalTime object as "hh:mm" and set it as lstMsgTime
            this.lstMsgTime = time.format(DateTimeFormatter.ofPattern("hh:mm"));
        } catch (DateTimeParseException e) {
            // Handle parsing errors here
            e.printStackTrace();
            // You may want to set a default value or handle the error in a different way
        }
    }


}

