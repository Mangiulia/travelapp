package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public class TravelDay implements Serializable {
    private int day;
    private List<Activity> activities;

    public TravelDay() {
    }

    public TravelDay(int day, List<Activity> activities) {
        this.day = day;
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "TravelDay{" +
                "day=" + day +
                ", activities=" + activities +
                '}';
    }

    public int getDay() {
        return day;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
