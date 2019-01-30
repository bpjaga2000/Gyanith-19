package com.barebrains.gyanith19;

public class eventitem {

    String name,time,tag;

    public eventitem() {
    }

    public eventitem(String name, String time , String tag) {
        this.name = name;
        this.time = time;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getTag() {
        return tag;
    }
}
