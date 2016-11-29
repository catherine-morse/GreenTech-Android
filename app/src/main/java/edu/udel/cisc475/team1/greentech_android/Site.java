package edu.udel.cisc475.team1.greentech_android;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by writingcenter on 11/28/16.
 */

public class Site {
    private String title;
    private double lat;
    private double lon;

    public Site() {

    }

    public Site(String title, double lat, double lon) {
        this.title = title;
        this.lat = lat;
        this.lon = lon;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
