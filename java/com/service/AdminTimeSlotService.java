package com.service;

// Importing TimeSlotDAO to access time slot-related database methods
import com.dao.TimeSlotDAO;

// Importing TimeSlotModel to store time slot records
import com.model.TimeSlotModel;

// Importing List to store multiple time slot records
import java.util.List;

/**
 * AdminTimeSlotService contains business logic for admin time slot management.
 *
 * This service class acts as a bridge between AdminTimeSlotsController
 * and TimeSlotDAO.
 *
 * It is responsible for:
 * - Getting all time slots
 * - Updating time slot status
 */
public class AdminTimeSlotService {

    /**
     * Gets all time slot records for admin time slot management.
     *
     * @return list of all time slots
     * @throws Exception if time slot records cannot be retrieved
     */
    public List<TimeSlotModel> getAllTimeSlots() throws Exception {
        // Creating TimeSlotDAO object to access time slot database operations
        TimeSlotDAO dao = new TimeSlotDAO();

        // Returning all time slots from DAO
        return dao.getAllTimeSlots();
    }

    /**
     * Updates the status of a selected time slot.
     *
     * @param timeSlotId ID of the time slot to update
     * @param status new time slot status
     * @throws Exception if time slot status update fails
     */
    public void updateTimeSlotStatus(int timeSlotId, String status) throws Exception {
        // Creating TimeSlotDAO object to update time slot status
        TimeSlotDAO dao = new TimeSlotDAO();

        // Calling DAO method to update selected time slot status
        dao.updateTimeSlotStatus(timeSlotId, status);
    }
}
