package com.model;

import java.io.Serializable;

public class SportBookingReportModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sportName;
    private int bookingCount;
    private double percentage;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}