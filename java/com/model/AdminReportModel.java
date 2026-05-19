package com.model;

import java.io.Serializable;
import java.util.List;

public class AdminReportModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalUsers;
    private int totalCourts;
    private int totalBookings;
    private int confirmedBookings;
    private int pendingBookings;
    private int cancelledBookings;
    private int activeCourts;
    private int inactiveCourts;

    private double confirmedPercent;
    private double pendingPercent;
    private double cancelledPercent;

    private List<SportBookingReportModel> sportReportList;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalCourts() {
        return totalCourts;
    }

    public void setTotalCourts(int totalCourts) {
        this.totalCourts = totalCourts;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public int getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(int confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    public int getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(int pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    public int getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(int cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public int getActiveCourts() {
        return activeCourts;
    }

    public void setActiveCourts(int activeCourts) {
        this.activeCourts = activeCourts;
    }

    public int getInactiveCourts() {
        return inactiveCourts;
    }

    public void setInactiveCourts(int inactiveCourts) {
        this.inactiveCourts = inactiveCourts;
    }

    public double getConfirmedPercent() {
        return confirmedPercent;
    }

    public void setConfirmedPercent(double confirmedPercent) {
        this.confirmedPercent = confirmedPercent;
    }

    public double getPendingPercent() {
        return pendingPercent;
    }

    public void setPendingPercent(double pendingPercent) {
        this.pendingPercent = pendingPercent;
    }

    public double getCancelledPercent() {
        return cancelledPercent;
    }

    public void setCancelledPercent(double cancelledPercent) {
        this.cancelledPercent = cancelledPercent;
    }

    public List<SportBookingReportModel> getSportReportList() {
        return sportReportList;
    }

    public void setSportReportList(List<SportBookingReportModel> sportReportList) {
        this.sportReportList = sportReportList;
    }
}