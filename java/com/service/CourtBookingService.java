package com.service;

import com.dao.BookingDAO;
import com.dao.TimeSlotDAO;
import com.model.BookingModel;
import com.model.TimeSlotModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class CourtBookingService {

    private final TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    public List<TimeSlotModel> getSlotsByCourtAndDate(int courtId, Date slotDate) throws Exception {
        return timeSlotDAO.getSlotsByCourtAndDate(courtId, slotDate);
    }

    public int bookSlot(int userId, int timeSlotId) throws Exception {
        TimeSlotModel slot = timeSlotDAO.getSlotById(timeSlotId);

        if (slot == null) {
            throw new Exception("Selected time slot was not found.");
        }

        if (bookingDAO.isSlotAlreadyBooked(timeSlotId)) {
            throw new Exception("This time slot has already been booked.");
        }

        BookingModel booking = new BookingModel();
        booking.setBookingDate(slot.getSlotDate());
        booking.setBookingStatus("confirmed");
        booking.setBookingCreatedAt(new Timestamp(System.currentTimeMillis()));
        booking.setUserId(userId);
        booking.setTimeSlotId(timeSlotId);

        return bookingDAO.insertBooking(booking);
    }
}