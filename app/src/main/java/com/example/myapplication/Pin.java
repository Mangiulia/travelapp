package com.example.myapplication;

public class Pin {
    private String destination;
    private double latitude;
    private double longitude;

    public Pin() {
    }

    public Pin(String destination, double latitude, double longitude) {
        this.destination = destination;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
