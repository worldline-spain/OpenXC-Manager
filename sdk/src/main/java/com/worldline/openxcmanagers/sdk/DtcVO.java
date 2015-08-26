package com.worldline.openxcmanagers.sdk;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DtcVO extends RealmObject {

    @PrimaryKey
    private String dtcCode;
    private String description;
    private long timestamp;
    private double distance;
    private int activate;

    public String getDtcCode() {
        return dtcCode;
    }

    public void setDtcCode(String dtcCode) {
        this.dtcCode = dtcCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }
}
