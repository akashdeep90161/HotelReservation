package com.innovationm.hotel.model.secondary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RoomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean isAC;
    private boolean wifi;
    private boolean geyser;
    private boolean tv;
    private boolean roomHeater;
    private boolean powerBackup;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAC() {
        return isAC;
    }

    public void setAC(boolean AC) {
        isAC = AC;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isGeyser() {
        return geyser;
    }

    public void setGeyser(boolean geyser) {
        this.geyser = geyser;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isRoomHeater() {
        return roomHeater;
    }

    public void setRoomHeater(boolean roomHeater) {
        this.roomHeater = roomHeater;
    }

    public boolean isPowerBackup() {
        return powerBackup;
    }

    public void setPowerBackup(boolean powerBackup) {
        this.powerBackup = powerBackup;
    }
}
