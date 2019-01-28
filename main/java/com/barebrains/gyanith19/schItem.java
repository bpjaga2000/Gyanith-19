package com.barebrains.gyanith19;

public class schItem {
    private String title, time, venue;

    public schItem(String title, String time, String venue) {
        this.title = title;
        this.time = time;
        this.venue = venue;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }
}