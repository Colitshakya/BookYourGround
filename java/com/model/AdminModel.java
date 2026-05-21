package com.model;

// Importing Serializable so AdminModel objects can be stored in session or transferred safely
import java.io.Serializable;

// Importing List to store multiple recent booking records
import java.util.List;

/**
 * AdminModel is a POJO/model class used to store admin dashboard data.
 *
 * This class holds summary information for the admin dashboard, such as:
 * - Total users
 * - Pending users
 * - Total courts
 * - Total bookings
 * - Recent bookings
 *
 * It implements Serializable so its object can be safely stored or transferred,
 * especially when used in session or request-based operations.
 */
public class AdminModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores total number of users in the system
    private int totalUsers;

    // Stores number of users waiting for approval
    private int pendingUsers;

    // Stores total number of courts in the system
    private int totalCourts;

    // Stores total number of bookings in the system
    private int totalBookings;

    // Stores list of recent bookings shown on the admin dashboard
    private List<BookingModel> recentBookings;

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
     * Gets number of pending users.
     *
     * @return pending users count
     */
    public int getPendingUsers() {
        return pendingUsers;
    }

    /**
     * Sets number of pending users.
     *
     * @param pendingUsers pending users count
     */
    public void setPendingUsers(int pendingUsers) {
        this.pendingUsers = pendingUsers;
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
     * Gets recent booking list for admin dashboard.
     *
     * @return list of recent bookings
     */
    public List<BookingModel> getRecentBookings() {
        return recentBookings;
    }

    /**
     * Sets recent booking list for admin dashboard.
     *
     * @param recentBookings list of recent bookings
     */
    public void setRecentBookings(List<BookingModel> recentBookings) {
        this.recentBookings = recentBookings;
    }
}
