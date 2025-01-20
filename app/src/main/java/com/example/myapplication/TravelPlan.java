package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public class TravelPlan implements Serializable {

    private String _id;
    private List<TravelDay> plan;
    private String key;
    private String destination;

    private String imageUrl;

    public TravelPlan(String _id, List<TravelDay> plan, String key, String imageUrl) {
        this._id = _id;
        this.plan = plan;
        this.key = key;
        this.imageUrl = imageUrl;
    }

    public TravelPlan(String _id, List<TravelDay> plan, String key, String destination, String imageUrl) {
        this._id = _id;
        this.plan = plan;
        this.key = key;
        this.destination = destination;
        this.imageUrl = imageUrl;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setPlan(List<TravelDay> plan) {
        this.plan = plan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public TravelPlan() {
    }

    public List<TravelDay> getPlan() {
        return plan;
    }

    @Override
    public String toString() {
        return "TravelPlan{" +
                "_id='" + _id + '\'' +
                ", plan=" + plan +
                ", key='" + key + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
