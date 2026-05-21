package com.model;

// Importing Serializable so CourtModel objects can be stored or transferred safely
import java.io.Serializable;

// Importing BigDecimal to store court price accurately
import java.math.BigDecimal;

/**
 * CourtModel is a POJO/model class used to store court information.
 *
 * This class represents court details such as:
 * - Court ID
 * - Court name
 * - Court number
 * - Capacity
 * - Surface type
 * - Price per hour
 * - Court status
 * - Venue ID
 * - Sport type ID
 * - Staff ID
 * - Venue name
 * - Image path
 * - Sport name
 */
public class CourtModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores court ID
    private int courtId;

    // Stores court name
    private String courtName;

    // Stores court number
    private String courtNumber;

    // Stores court capacity
    private int courtCapacity;

    // Stores court surface type, such as turf, wooden, or concrete
    private String surfaceType;

    // Stores court price per hour
    private BigDecimal pricePerHour;

    // Stores court status, such as active, available, or inactive
    private String courtStatus;

    // Stores venue ID linked with this court
    private int venueId;

    // Stores sport type ID linked with this court
    private int sportTypeId;

    // Stores staff ID assigned to this court
    private int staffId;

    // Stores venue name for display purpose
    private String venueName;

    // Stores uploaded image path or image file name
    private String imagePath;

    // Stores sport name for display purpose
    private String sportName;

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
     * Gets court number.
     *
     * @return court number
     */
    public String getCourtNumber() {
        return courtNumber;
    }

    /**
     * Sets court number.
     *
     * @param courtNumber court number
     */
    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    /**
     * Gets court capacity.
     *
     * @return court capacity
     */
    public int getCourtCapacity() {
        return courtCapacity;
    }

    /**
     * Sets court capacity.
     *
     * @param courtCapacity court capacity
     */
    public void setCourtCapacity(int courtCapacity) {
        this.courtCapacity = courtCapacity;
    }

    /**
     * Gets surface type.
     *
     * @return surface type
     */
    public String getSurfaceType() {
        return surfaceType;
    }

    /**
     * Sets surface type.
     *
     * @param surfaceType surface type
     */
    public void setSurfaceType(String surfaceType) {
        this.surfaceType = surfaceType;
    }

    /**
     * Gets price per hour.
     *
     * @return price per hour
     */
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    /**
     * Sets price per hour.
     *
     * @param pricePerHour court price per hour
     */
    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    /**
     * Gets court status.
     *
     * @return court status
     */
    public String getCourtStatus() {
        return courtStatus;
    }

    /**
     * Sets court status.
     *
     * @param courtStatus court status
     */
    public void setCourtStatus(String courtStatus) {
        this.courtStatus = courtStatus;
    }

    /**
     * Gets venue ID.
     *
     * @return venue ID
     */
    public int getVenueId() {
        return venueId;
    }

    /**
     * Sets venue ID.
     *
     * @param venueId venue ID
     */
    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    /**
     * Gets sport type ID.
     *
     * @return sport type ID
     */
    public int getSportTypeId() {
        return sportTypeId;
    }

    /**
     * Sets sport type ID.
     *
     * @param sportTypeId sport type ID
     */
    public void setSportTypeId(int sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    /**
     * Gets staff ID.
     *
     * @return staff ID
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * Sets staff ID.
     *
     * @param staffId staff ID
     */
    public void setStaffId(int staffId) {
        this.staffId = staffId;
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
     * Gets venue name.
     *
     * @return venue name
     */
    public String getVenueName() {
        return venueName;
    }

    /**
     * Sets venue name.
     *
     * @param venueName venue name
     */
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    /**
     * Gets image path.
     *
     * @return image path or image file name
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets image path.
     *
     * @param imagePath image path or image file name
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
