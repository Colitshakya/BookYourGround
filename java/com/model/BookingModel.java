package com.model;

// Importing Serializable so BookingModel objects can be stored or transferred safely
import java.io.Serializable;

// Importing SQL Date to store booking date
import java.sql.Date;

// Importing Timestamp to store booking creation date and time
import java.sql.Timestamp;

/**
 * BookingModel is a POJO/model class used to store booking information.
 *
 * This class represents booking data such as:
 * - Booking ID
 * - Booking date
 * - Booking status
 * - Created date and time
 * - User ID
 * - Time slot ID
 *
 * It also stores extra display fields such as court name, sport name,
 * start time, end time, and user full name.
 */
public class BookingModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores booking ID
    private int bookingId;

    // Stores date of booking
    private Date bookingDate;

    // Stores booking status, such as pending, confirmed, or cancelled
    private String bookingStatus;

    // Stores date and time when booking was created
    private Timestamp bookingCreatedAt;

    // Stores ID of the user who made the booking
    private int userId;

    // Stores ID of the selected time slot
    private int timeSlotId;

    // Stores court name for display purpose
    private String courtName;

    // Stores sport name for display purpose
    private String sportName;

    // Stores start time of the booking slot
    private String startTime;

    // Stores end time of the booking slot
    private String endTime;

    // Stores full name of the user for admin display
    private String userFullName;

    /**
     * Gets booking ID.
     *
     * @return booking ID
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets booking ID.
     *
     * @param bookingId booking ID
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets booking date.
     *
     * @return booking date
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets booking date.
     *
     * @param bookingDate booking date
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets booking status.
     *
     * @return booking status
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets booking status.
     *
     * @param bookingStatus booking status
     */
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**
     * Gets booking creation timestamp.
     *
     * @return booking creation date and time
     */
    public Timestamp getBookingCreatedAt() {
        return bookingCreatedAt;
    }

    /**
     * Sets booking creation timestamp.
     *
     * @param bookingCreatedAt booking creation date and time
     */
    public void setBookingCreatedAt(Timestamp bookingCreatedAt) {
        this.bookingCreatedAt = bookingCreatedAt;
    }

    /**
     * Gets user ID.
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user ID.
     *
     * @param userId user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets time slot ID.
     *
     * @return time slot ID
     */
    public int getTimeSlotId() {
        return timeSlotId;
    }

    /**
     * Sets time slot ID.
     *
     * @param timeSlotId time slot ID
     */
    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    /**
     * Gets court name.
     *
     * @return court name
     */
    public String getCourtName() {
        return courtName;
    }

    /**
     * Sets court name.
     *
     * @param courtName court name
     */
    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

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
     * Gets start time.
     *
     * @return start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets user full name.
     *
     * @return user full name
     */
    public String getUserFullName() {
        return userFullName;
    }

    /**
     * Sets user full name.
     *
     * @param userFullName user full name
     */
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
