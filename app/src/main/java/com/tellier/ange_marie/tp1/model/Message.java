package com.tellier.ange_marie.tp1.model;

/**
 * Created by ange-marie on 25/10/17.
 */

public class Message {
    String username;
    String message;
    long date;

    public Message(String username, String message, long date) {
        this.username = username;
        this.message = message;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
