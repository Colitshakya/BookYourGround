package com.service;

// Importing CourtDAO to access court-related database methods
import com.dao.CourtDAO;

// Importing TimeSlotDAO to generate default time slots after adding a court
import com.dao.TimeSlotDAO;

// Importing CourtModel to store court details
import com.model.CourtModel;

// Importing LocalDate to get current month and next month
import java.time.LocalDate;

// Importing List to store multiple court records
import java.util.List;

/**
 * AdminCourtService contains business logic for admin court management.
 *
 * This service class acts as a bridge between AdminCourtsController
 * and DAO classes.
 *
 * It is responsible for:
 * - Getting all courts
 * - Updating court status
 * - Adding a new court
 * - Generating default time slots for newly added courts
 */
public class AdminCourtService {

    /**
     * Gets all court records for admin court management.
     *
     * @return list of all courts
     * @throws Exception if court records cannot be retrieved
     */
    public List<CourtModel> getAllCourts() throws Exception {
        // Creating CourtDAO object to access court database operations
        CourtDAO dao = new CourtDAO();

        // Returning all court records from CourtDAO
        return dao.getAllCourts();
    }

    /**
     * Updates the status of a selected court.
     *
     * @param courtId ID of the court to update
     * @param status new court status
     * @throws Exception if court status update fails
     */
    public void updateCourtStatus(int courtId, String status) throws Exception {
        // Creating CourtDAO object to update court status
        CourtDAO dao = new CourtDAO();

        // Calling DAO method to update selected court status
        dao.updateCourtStatus(courtId, status);
    }

    /**
     * Adds a new court and generates default time slots for it.
     *
     * After inserting the court, this method automatically generates
     * time slots for the current month and the next month.
     *
     * @param court CourtModel object containing new court details
     * @throws Exception if court insertion or time slot generation fails
     */
    public void addCourt(CourtModel court) throws Exception {
        // Creating CourtDAO object to insert new court
        CourtDAO courtDAO = new CourtDAO();

        // Creating TimeSlotDAO object to generate time slots
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

        // Inserting court into database and receiving generated court ID
        int newCourtId = courtDAO.insertCourt(court);

        // If generated court ID is invalid, throw error
        if (newCourtId <= 0) {
            throw new Exception("Court was added but no court ID was returned.");
        }

        // Getting current date
        LocalDate now = LocalDate.now();

        // Generating default time slots for current month
        timeSlotDAO.generateDefaultTimeSlotsForMonth(newCourtId, now.getYear(), now.getMonthValue());

        // Getting next month date
        LocalDate nextMonth = now.plusMonths(1);

        // Generating default time slots for next month
        timeSlotDAO.generateDefaultTimeSlotsForMonth(newCourtId, nextMonth.getYear(), nextMonth.getMonthValue());
    }
}
