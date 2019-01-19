package com.example.jahid.workmanager;

public class DataModel {
    String timestamp,tag;

    public DataModel(String timestamp, String tag) {
        this.timestamp = timestamp;
        this.tag = tag;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
