package com.earthquakemanager.Model;

import java.io.Serializable;


public class GeometryValue implements Serializable {


    private double longitude;
    private double latitude;

    public GeometryValue(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
