package edu.udel.cisc475.team1.greentech_android;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by writingcenter on 11/28/16.
 */

public class Site {
    private String title;
    private double lat;
    private double lon;
    private String facilityType;
    private String facilityStatus;
    private String power;
    private String fuel;
    private String secondaryFuel;

    public Site() {

    }

    public Site(String title, double lat, double lon, String facilityType, String facilityStatus, String power, String fuel, String secondaryFuel) {
        this.title = title;
        this.lat = lat;
        this.lon = lon;
        this.facilityType = facilityType;
        this.facilityStatus = facilityStatus;
        this.power = power;
        this.fuel = fuel;
        this.secondaryFuel = secondaryFuel;
    }

    public String getTitle() {
        return title;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getFacilityType() {

        return facilityType;
    }

    public String getFacilityStatus() {
        return facilityStatus;
    }

    public String getPower() {
        return power;
    }

    public String getFuel() {
        return fuel;
    }

    public String getSecondaryFuel() {
        return secondaryFuel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public void setFacilityStatus(String facilityStatus) {
        this.facilityStatus = facilityStatus;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setSecondaryFuel(String secondaryFuel) {
        this.secondaryFuel = secondaryFuel;
    }
}
