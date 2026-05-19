package com.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CourtModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int courtId;
    private String courtName;
    private String courtNumber;
    private int courtCapacity;
    private String surfaceType;
    private BigDecimal pricePerHour;
    private String courtStatus;
    private int venueId;
    private int sportTypeId;
    private int staffId;
    private String venueName;
    private String imagePath;

    private String sportName;

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public int getCourtCapacity() {
        return courtCapacity;
    }

    public void setCourtCapacity(int courtCapacity) {
        this.courtCapacity = courtCapacity;
    }

    public String getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        this.surfaceType = surfaceType;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getCourtStatus() {
        return courtStatus;
    }

    public void setCourtStatus(String courtStatus) {
        this.courtStatus = courtStatus;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(int sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}