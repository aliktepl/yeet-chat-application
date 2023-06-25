package com.example.ap2_ex3.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


/**
 * Represents a chat between users.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "username", childColumns = "recipient", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Message.class, parentColumns = "id", childColumns = "lst_msg_id", onDelete = ForeignKey.CASCADE)
}, tableName = "chat_table")
public class Chat {
    @PrimaryKey
    private int id; // Unique identifier for the chat
    private String recipient; // Foreign key to the User entity
    private int lst_msg_id;


    public Chat(int id, String recipient, int lst_msg_id) {
        this.id = id;
        this.recipient = recipient;
        this.lst_msg_id = lst_msg_id;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getLst_msg_id() {
        return lst_msg_id;
    }

    public void setLst_msg_id(int lst_msg_id) {
        this.lst_msg_id = lst_msg_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}

