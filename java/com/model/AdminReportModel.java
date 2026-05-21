package com.model;

import java.io.Serializable;
import java.util.List;

public class AdminReportModel implements Serializable {
    private static final long serialVersionUID = 1L;
package com.model;

// Importing Serializable so AdminReportModel objects can be stored or transferred safely
import java.io.Serializable;

// Importing List to store multiple sport booking report records
import java.util.List;

/**
 * AdminReportModel is a POJO/model class used to store admin report data.
 *
 * This class holds report information such as:
 * - Total users
 * - Total courts
 * - Total bookings
 * - Booking status counts
 * - Court status counts
 * - Booking status percentages
 * - Sport-wise booking report list
 */
public class AdminReportModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores total number of users
    private int totalUsers;

    // Stores total number of courts
    private int totalCourts;

    // Stores total number of bookings
    private int totalBookings;

    // Stores number of confirmed bookings
    private int confirmedBookings;

    // Stores number of pending bookings
    private int pendingBookings;

    // Stores number of cancelled bookings
    private int cancelledBookings;

    // Stores number of active courts
    private int activeCourts;

    // Stores number of inactive courts
    private int inactiveCourts;

    // Stores percentage of confirmed bookings
    private double confirmedPercent;

    // Stores percentage of pending bookings
    private double pendingPercent;

    // Stores percentage of cancelled bookings
    private double cancelledPercent;

    // Stores sport-wise booking report data
    private List<SportBookingReportModel> sportReportList;

    /**
     * Gets total number of users.
     *
     * @return total users count
     */
    public int getTotalUsers() {
        return totalUsers;
    }

    /**
     * Sets total number of users.
     *
     * @param totalUsers total users count
     */
    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    /**
     * Gets total number of courts.
     *
     * @return total courts count
     */
    public int getTotalCourts() {
        return totalCourts;
    }

    /**
     * Sets total number of courts.
     *
     * @param totalCourts total courts count
     */
    public void setTotalCourts(int totalCourts) {
        this.totalCourts = totalCourts;
    }

    /**
     * Gets total number of bookings.
     *
     * @return total bookings count
     */
    public int getTotalBookings() {
        return totalBookings;
    }

    /**
     * Sets total number of bookings.
     *
     * @param totalBookings total bookings count
     */
    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    /**
     * Gets confirmed booking count.
     *
     * @return confirmed bookings count
     */
    public int getConfirmedBookings() {
        return confirmedBookings;
    }

    /**
     * Sets confirmed booking count.
     *
     * @param confirmedBookings confirmed bookings count
     */
    public void setConfirmedBookings(int confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    /**
     * Gets pending booking count.
     *
     * @return pending bookings count
     */
    public int getPendingBookings() {
        return pendingBookings;
    }

    /**
     * Sets pending booking count.
     *
     * @param pendingBookings pending bookings count
     */
    public void setPendingBookings(int pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    /**
     * Gets cancelled booking count.
     *
     * @return cancelled bookings count
     */
    public int getCancelledBookings() {
        return cancelledBookings;
    }

    /**
     * Sets cancelled booking count.
     *
     * @param cancelledBookings cancelled bookings count
     */
    public void setCancelledBookings(int cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    /**
     * Gets active court count.
     *
     * @return active courts count
     */
    public int getActiveCourts() {
        return activeCourts;
    }

    /**
     * Sets active court count.
     *
     * @param activeCourts active courts count
     */
    public void setActiveCourts(int activeCourts) {
        this.activeCourts = activeCourts;
    }

    /**
     * Gets inactive court count.
     *
     * @return inactive courts count
     */
    public int getInactiveCourts() {
        return inactiveCourts;
    }

    /**
     * Sets inactive court count.
     *
     * @param inactiveCourts inactive courts count
     */
    public void setInactiveCourts(int inactiveCourts) {
        this.inactiveCourts = inactiveCourts;
    }

    /**
     * Gets confirmed booking percentage.
     *
     * @return confirmed booking percentage
     */
    public double getConfirmedPercent() {
        return confirmedPercent;
    }

    /**
     * Sets confirmed booking percentage.
     *
     * @param confirmedPercent confirmed booking percentage
     */
    public void setConfirmedPercent(double confirmedPercent) {
        this.confirmedPercent = confirmedPercent;
    }

    /**
     * Gets pending booking percentage.
     *
     * @return pending booking percentage
     */
    public double getPendingPercent() {
        return pendingPercent;
    }

    /**
     * Sets pending booking percentage.
     *
     * @param pendingPercent pending booking percentage
     */
    public void setPendingPercent(double pendingPercent) {
        this.pendingPercent = pendingPercent;
    }

    /**
     * Gets cancelled booking percentage.
     *
     * @return cancelled booking percentage
     */
    public double getCancelledPercent() {
        return cancelledPercent;
    }

    /**
     * Sets cancelled booking percentage.
     *
     * @param cancelledPercent cancelled booking percentage
     */
    public void setCancelledPercent(double cancelledPercent) {
        this.cancelledPercent = cancelledPercent;
    }

    /**
     * Gets sport-wise booking report list.
     *
     * @return list of sport booking report records
     */
    public List<SportBookingReportModel> getSportReportList() {
        return sportReportList;
    }

    /**
     * Sets sport-wise booking report list.
     *
     * @param sportReportList list of sport booking report records
     */
    public void setSportReportList(List<SportBookingReportModel> sportReportList) {
        this.sportReportList = sportReportList;
    }
}
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
