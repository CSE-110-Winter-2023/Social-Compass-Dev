package com.example.socialcompass;

public class Location {
    private String label;
    private float latitude;
    private float longitude;

    public Location(String label, float latitude, float longitude) {
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //Getters
    public String getLabel() {
        return label;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    //Setters
    public void setLabel(String label) {
        this.label = label;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}