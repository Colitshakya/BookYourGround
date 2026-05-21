package com.service;

// Importing DAO classes needed for court booking
import com.dao.BookingDAO;
import com.dao.TimeSlotDAO;

// Importing model classes for booking and time slot data
import com.model.BookingModel;
import com.model.TimeSlotModel;

// Importing SQL Date and Timestamp
import java.sql.Date;
import java.sql.Timestamp;

// Importing List to store multiple time slot records
import java.util.List;

/**
 * CourtBookingService contains business logic for user court booking.
 *
 * This service class is responsible for:
 * - Getting time slots by court and date
 * - Checking whether selected time slot exists
 * - Preventing duplicate bookings
 * - Creating booking records
 */
public class CourtBookingService {

    // TimeSlotDAO object used to retrieve selected time slot data
    private final TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

    // BookingDAO object used to check and insert booking data
    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * Gets time slots for a selected court and date.
     *
     * @param courtId ID of the selected court
     * @param slotDate selected booking date
     * @return list of time slots
     * @throws Exception if time slots cannot be retrieved
     */
    public List<TimeSlotModel> getSlotsByCourtAndDate(int courtId, Date slotDate) throws Exception {
        // Returning time slots from TimeSlotDAO
        return timeSlotDAO.getSlotsByCourtAndDate(courtId, slotDate);
    }

    /**
     * Books a selected time slot for a user.
     *
     * This method checks whether the time slot exists and whether it is already booked.
     * If valid, it creates a new booking record.
     *
     * @param userId ID of the logged-in user
     * @param timeSlotId ID of the selected time slot
     * @return generated booking ID
     * @throws Exception if slot is invalid, already booked, or booking fails
     */
    public int bookSlot(int userId, int timeSlotId) throws Exception {
        // Getting selected time slot details
        TimeSlotModel slot = timeSlotDAO.getSlotById(timeSlotId);

        // If selected slot does not exist, throw error
        if (slot == null) {
            throw new Exception("Selected time slot was not found.");
        }

        // Checks whether the selected time slot is already booked
        if (bookingDAO.isSlotAlreadyBooked(timeSlotId)) {
            throw new Exception("This time slot has already been booked.");
        }

        // Creating BookingModel object to store booking data
        BookingModel booking = new BookingModel();

        // Setting booking date from selected time slot
        booking.setBookingDate(slot.getSlotDate());

        // Setting booking status as confirmed
        booking.setBookingStatus("confirmed");

        // Setting current date and time as booking creation time
        booking.setBookingCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Setting logged-in user's ID
        booking.setUserId(userId);

        // Setting selected time slot ID
        booking.setTimeSlotId(timeSlotId);

        // Inserting booking and returning generated booking ID
        return bookingDAO.insertBooking(booking);
    }
}
