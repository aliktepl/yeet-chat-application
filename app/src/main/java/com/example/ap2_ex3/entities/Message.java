package com.example.ap2_ex3.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys={
        @ForeignKey(entity = User.class, parentColumns = "username", childColumns = "sender"),
        @ForeignKey(entity = Chat.class, parentColumns = "id", childColumns = "chatId", onDelete = ForeignKey.CASCADE)},
        tableName = "msg_table")
public class Message {
    @PrimaryKey
    private int id;
    private int chatId;
    private String created;
    private String sender;
    private String content;

    public Message(int id,int chatId, String created, String sender, String content){
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
        this.chatId = chatId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getChatId() {
        return chatId;
    }
    public String getCreated() {
        return created;
    }

    public String getSender() {
        return sender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
