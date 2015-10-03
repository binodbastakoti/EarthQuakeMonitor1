package com.earthquakemanager.Model;

import java.io.Serializable;



public class RowItem implements Serializable {

    private String location;
    private String dateTime;
    private String magnitude;

    public RowItem() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }


}