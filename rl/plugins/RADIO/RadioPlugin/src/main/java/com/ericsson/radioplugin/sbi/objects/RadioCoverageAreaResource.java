/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.radioplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class RadioCoverageAreaResource {
    private String id;
    private String minbw;
    private String maxbw;
    private String delay;
    private String latitude;
    private String longitude;
    private String altitude;
    private String range;

    public RadioCoverageAreaResource(String id, String minbw, String maxbw, String delay, String latitude, String longitude, String altitude, String range) {
        this.id = id;
        this.minbw = minbw;
        this.maxbw = maxbw;
        this.delay = delay;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.range = range;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinbw() {
        return minbw;
    }

    public void setMinbw(String minbw) {
        this.minbw = minbw;
    }

    public String getMaxbw() {
        return maxbw;
    }

    public void setMaxbw(String maxbw) {
        this.maxbw = maxbw;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
    

    
    


    
    
    
}  

