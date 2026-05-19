package com.service;

import com.dao.BookingDAO;
import com.model.BookingModel;

import java.util.List;

public class AdminBookingService {

    public List<BookingModel> getAllBookings() throws Exception {
        BookingDAO dao = new BookingDAO();
        return dao.getAllBookingsForAdmin();
    }

    public void updateBookingStatus(int bookingId, String status) throws Exception {
        BookingDAO dao = new BookingDAO();
        dao.updateBookingStatus(bookingId, status);
    }
}