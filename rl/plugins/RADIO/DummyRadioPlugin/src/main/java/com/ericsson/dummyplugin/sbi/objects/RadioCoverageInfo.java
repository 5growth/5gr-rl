/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi.objects;

import java.math.BigDecimal;

/**
 *
 * @author osboxes
 */
public class RadioCoverageInfo {
    private String areaId;
    private String geogrphicalInfo;
    private BigDecimal minBw;
    private BigDecimal maxBw;
    private BigDecimal delay;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;
    private BigDecimal range;

    public RadioCoverageInfo(String areaId, String geogrphicalInfo, BigDecimal minBw, BigDecimal maxBw, BigDecimal delay, BigDecimal latitude, BigDecimal longitude, BigDecimal altitude, BigDecimal range) {
        this.areaId = areaId;
        this.geogrphicalInfo = geogrphicalInfo;
        this.minBw = minBw;
        this.maxBw = maxBw;
        this.delay = delay;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.range = range;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getGeogrphicalInfo() {
        return geogrphicalInfo;
    }

    public void setGeogrphicalInfo(String geogrphicalInfo) {
        this.geogrphicalInfo = geogrphicalInfo;
    }

    public BigDecimal getMinBw() {
        return minBw;
    }

    public void setMinBw(BigDecimal minBw) {
        this.minBw = minBw;
    }

    public BigDecimal getMaxBw() {
        return maxBw;
    }

    public void setMaxBw(BigDecimal maxBw) {
        this.maxBw = maxBw;
    }

    public BigDecimal getDelay() {
        return delay;
    }

    public void setDelay(BigDecimal delay) {
        this.delay = delay;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getAltitude() {
        return altitude;
    }

    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
    }

    public BigDecimal getRange() {
        return range;
    }

    public void setRange(BigDecimal range) {
        this.range = range;
    }
    
    
    
}
