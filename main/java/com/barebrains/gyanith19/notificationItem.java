package com.barebrains.gyanith19;

public class notificationItem {
    private String sender, time, text;

    public notificationItem(String sender, String time, String text) {
        this.sender = sender;
        this.time = time;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }
}
