package com.model;

import java.io.Serializable;
import java.util.List;

public class AdminModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalUsers;
    private int pendingUsers;
    private int totalCourts;
    private int totalBookings;
    private List<BookingModel> recentBookings;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getPendingUsers() {
        return pendingUsers;
    }

    public void setPendingUsers(int pendingUsers) {
        this.pendingUsers = pendingUsers;
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

    public List<BookingModel> getRecentBookings() {
        return recentBookings;
    }

    public void setRecentBookings(List<BookingModel> recentBookings) {
        this.recentBookings = recentBookings;
    }
}