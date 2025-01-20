package com.example.myapplication;

import java.io.Serializable;

public class Activity implements Serializable {
    private String time;
    private String description;
    public Activity(){}

    public Activity(String time, String description) {
        this.time = time;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "time='" + time + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
