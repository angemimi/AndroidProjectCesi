package com.tellier.ange_marie.tp1.model;

/**
 * Created by ange-marie on 25/10/17.
 */

public class User {

    Long date;
    String username;

    public User(String username, Long date){
        this.username = username;
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
