package com.barebrains.gyanith19;

public class schitem {

    String venue,time,title;
    boolean live;

    public schitem() {
    }

    public schitem(String venue, String time, String title, boolean live) {
        this.venue = venue;
        this.time = time;
        this.title = title;
        this.live = live;
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

    public boolean isLive() {
        return live;
    }
}
