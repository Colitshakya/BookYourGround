package com.model;

// Importing Serializable so TimeSlotModel objects can be stored or transferred safely
import java.io.Serializable;

// Importing SQL Date to store the slot date
import java.sql.Date;

// Importing SQL Time to store start and end time of the slot
import java.sql.Time;

/**
 * TimeSlotModel is a POJO/model class used to store court time slot information.
 *
 * This class represents time slot data such as:
 * - Time slot ID
 * - Slot date
 * - Start time
 * - End time
 * - Slot status
 * - Court ID
 *
 * It also stores extra display fields such as court name, sport name,
 * and whether the slot is already booked.
 */
public class TimeSlotModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores time slot ID
    private int timeSlotId;

    // Stores date of the time slot
    private Date slotDate;

    // Stores start time of the time slot
    private Time startTime;

    // Stores end time of the time slot
    private Time endTime;

    // Stores slot status, such as available, booked, or inactive
    private String slotStatus;

    // Stores court ID linked with this time slot
    private int courtId;

    // Stores court name for display purpose
    private String courtName;

    // Stores sport name for display purpose
    private String sportName;

    // Stores whether the slot is already booked or not
    private boolean booked;

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
     * Gets slot date.
     *
     * @return slot date
     */
    public Date getSlotDate() {
        return slotDate;
    }

    /**
     * Sets slot date.
     *
     * @param slotDate slot date
     */
    public void setSlotDate(Date slotDate) {
        this.slotDate = slotDate;
    }

    /**
     * Gets start time.
     *
     * @return start time
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime start time
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return end time
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime end time
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets slot status.
     *
     * @return slot status
     */
    public String getSlotStatus() {
        return slotStatus;
    }

    /**
     * Sets slot status.
     *
     * @param slotStatus slot status
     */
    public void setSlotStatus(String slotStatus) {
        this.slotStatus = slotStatus;
    }

    /**
     * Gets court ID.
     *
     * @return court ID
     */
    public int getCourtId() {
        return courtId;
    }

    /**
     * Sets court ID.
     *
     * @param courtId court ID
     */
    public void setCourtId(int courtId) {
        this.courtId = courtId;
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
     * Checks whether the time slot is booked.
     *
     * @return true if slot is booked, otherwise false
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Sets booked status of the time slot.
     *
     * @param booked true if slot is booked, otherwise false
     */
    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
