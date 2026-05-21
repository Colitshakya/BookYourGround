package com.service;

// Importing BookingDAO to access booking-related database methods
import com.dao.BookingDAO;

// Importing BookingModel to store booking records
import com.model.BookingModel;

// Importing List to store multiple booking records
import java.util.List;

/**
 * AdminBookingService contains business logic for admin booking management.
 *
 * This service class acts as a bridge between AdminBookingsController
 * and BookingDAO.
 *
 * It is responsible for:
 * - Getting all bookings for admin
 * - Updating booking status
 */
public class AdminBookingService {

    /**
     * Gets all booking records for the admin booking management page.
     *
     * @return list of all bookings
     * @throws Exception if booking records cannot be retrieved
     */
    public List<BookingModel> getAllBookings() throws Exception {
        // Creating BookingDAO object to access booking database operations
        BookingDAO dao = new BookingDAO();

        // Returning all bookings retrieved from BookingDAO
        return dao.getAllBookingsForAdmin();
    }

    /**
     * Updates the status of a selected booking.
     *
     * @param bookingId ID of the booking to update
     * @param status new booking status
     * @throws Exception if booking status update fails
     */
    public void updateBookingStatus(int bookingId, String status) throws Exception {
        // Creating BookingDAO object to update booking status in database
        BookingDAO dao = new BookingDAO();

        // Calling DAO method to update booking status
        dao.updateBookingStatus(bookingId, status);
    }
}
