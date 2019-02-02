package com.barebrains.gyanith19;

public class schItem {

    String venue,time,title;

    public schItem() {
    }

    public schItem(String title, String time, String venue) {
        this.venue = venue;
        this.time = time;
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
}
