package com.model;

// Importing Serializable so SportBookingReportModel objects can be stored or transferred safely
import java.io.Serializable;

/**
 * SportBookingReportModel is a POJO/model class used to store sport-wise booking report data.
 *
 * This class represents report information such as:
 * - Sport name
 * - Number of bookings for that sport
 * - Booking percentage for that sport
 */
public class SportBookingReportModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores the name of the sport, such as Futsal, Basketball, Tennis, or Pickleball
    private String sportName;

    // Stores the number of bookings made for this sport
    private int bookingCount;

    // Stores the percentage of bookings for this sport compared to total bookings
    private double percentage;

    /**
     * Gets sport name.
     *
     * @return sport name
     */
    public String getSportName() {
        return sportName;
    }

    /**
     * Sets sport name.
     *
     * @param sportName sport name
     */
    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    /**
     * Gets booking count for the sport.
     *
     * @return booking count
     */
    public int getBookingCount() {
        return bookingCount;
    }

    /**
     * Sets booking count for the sport.
     *
     * @param bookingCount number of bookings
     */
    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }

    /**
     * Gets booking percentage for the sport.
     *
     * @return booking percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Sets booking percentage for the sport.
     *
     * @param percentage booking percentage
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
