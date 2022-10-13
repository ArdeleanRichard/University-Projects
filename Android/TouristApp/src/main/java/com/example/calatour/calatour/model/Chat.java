package com.example.calatour.calatour.model;

/**
 * Created by Rici on 05-Dec-18.
 */

public class Chat {
    String username;
    String message;
    String time;
    Integer image;

    public Chat(String username, String message, String time) {
        this.username = username;
        this.message = message;
        this.time = time;
    }

    public Chat(String username, String message, String time, Integer image) {
        this.username = username;
        this.message = message;
        this.time = time;
        this.image = image;
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

    public String getTime() {
        return time;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
