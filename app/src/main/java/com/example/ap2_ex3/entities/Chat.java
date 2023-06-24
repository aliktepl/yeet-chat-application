package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Represents a chat between users.
 */
@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int id; // Unique identifier for the chat
    private int picture; // Picture associated with the chat
    private String username; // Username of the chat participant
    private String displayName; // Display name of the chat participant
    private String lastMsg; // Last message in the chat
    private String lastMsgTime; // Time when the last message was sent

    /**
     * Constructs a Chat object.
     *
     * @param picture      the picture associated with the chat
     * @param username     the username of the chat participant
     * @param displayName  the display name of the chat participant
     * @param lastMsg      the last message in the chat
     * @param lastMsgTime  the time when the last message was sent
     */
    public Chat(int picture, String username, String displayName, String lastMsg, Date lastMsgTime) {
        this.picture = picture;
        this.username = username;
        this.displayName = displayName;
        this.lastMsg = lastMsg;
        this.lastMsgTime = new SimpleDateFormat("kk:mm", new Locale("he", "IL")).format(lastMsgTime);
    }

    public Chat() {

    }

    /**
     * Returns the unique identifier for the chat.
     *
     * @return the chat ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the picture associated with the chat.
     *
     * @return the chat picture
     */
    public int getPicture() {
        return picture;
    }

    /**
     * Returns the username of the chat participant.
     *
     * @return the chat participant's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the display name of the chat participant.
     *
     * @return the chat participant's display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the last message in the chat.
     *
     * @return the last message
     */
    public String getLastMsg() {
        return lastMsg;
    }

    /**
     * Returns the time when the last message was sent.
     *
     * @return the last message time
     */
    public String getLastMsgTime() {
        return lastMsgTime;
    }

    /**
     * Sets the display name of the chat participant.
     *
     * @param displayName the new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the unique identifier for the chat.
     *
     * @param id the new chat ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the last message in the chat.
     *
     * @param lastMsg the new last message
     */
    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    /**
     * Sets the time when the last message was sent.
     *
     * @param lastMsgTime the new last message time
     */
    public void setLastMsgTime(Date lastMsgTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm", new Locale("he", "IL"));
        this.lastMsgTime = dateFormat.format(lastMsgTime);
    }

    /**
     * Sets the picture associated with the chat.
     *
     * @param picture the new chat picture
     */
    public void setPicture(int picture) {
        this.picture = picture;
    }

    /**
     * Sets the username of the chat participant.
     *
     * @param username the new chat participant's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    /**
     * Returns a string representation of the Chat object.
     *
     * @return a string representation of the Chat object
     */
    @NonNull
    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", picture=" + picture +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                ", lastMsgTime=" + lastMsgTime +
                '}';
    }
}
