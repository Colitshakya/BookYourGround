package com.dao;

import com.model.BookingModel;
import com.model.SportBookingReportModel;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean isSlotAlreadyBooked(int timeSlotId) throws Exception {
        boolean booked = false;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT booking_id FROM booking WHERE timeSlot_id = ? AND booking_status <> 'cancelled'";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, timeSlotId);

        ResultSet rs = pst.executeQuery();
        booked = rs.next();

        rs.close();
        pst.close();
        con.close();

        return booked;
    }

    public int insertBooking(BookingModel booking) throws Exception {
        int generatedBookingId = 0;

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO booking (booking_date, booking_status, booking_created_at, user_id, timeSlot_id) " +
                     "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setDate(1, booking.getBookingDate());
        pst.setString(2, booking.getBookingStatus());
        pst.setTimestamp(3, booking.getBookingCreatedAt());
        pst.setInt(4, booking.getUserId());
        pst.setInt(5, booking.getTimeSlotId());

        int affectedRows = pst.executeUpdate();

        if (affectedRows > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                generatedBookingId = rs.getInt(1);
            }
            rs.close();
        }

        pst.close();
        con.close();

        return generatedBookingId;
    }

    public int getTotalBookings() throws Exception {
        int count = 0;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM booking";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }

    public List<BookingModel> getRecentBookingsForAdmin() throws Exception {
        List<BookingModel> bookings = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT b.booking_id, b.booking_date, b.booking_status, " +
                     "c.court_name, st.sport_name, t.start_time, t.end_time, " +
                     "CONCAT(u.user_first_name, ' ', u.user_last_name) AS user_full_name " +
                     "FROM booking b " +
                     "JOIN user u ON b.user_id = u.user_id " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "ORDER BY b.booking_created_at DESC " +
                     "LIMIT 5";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            BookingModel booking = new BookingModel();
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setBookingDate(rs.getDate("booking_date"));
            booking.setBookingStatus(rs.getString("booking_status"));
            booking.setCourtName(rs.getString("court_name"));
            booking.setSportName(rs.getString("sport_name"));
            booking.setStartTime(String.valueOf(rs.getTime("start_time")));
            booking.setEndTime(String.valueOf(rs.getTime("end_time")));
            booking.setUserFullName(rs.getString("user_full_name"));
            bookings.add(booking);
        }

        rs.close();
        pst.close();
        con.close();

        return bookings;
    }

    public List<BookingModel> getAllBookingsForAdmin() throws Exception {
        List<BookingModel> bookings = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT b.booking_id, b.booking_date, b.booking_status, b.booking_created_at, " +
                "c.court_name, st.sport_name, t.start_time, t.end_time, " +
                "CONCAT(u.user_first_name, ' ', u.user_last_name) AS user_full_name " +
                "FROM booking b " +
                "JOIN user u ON b.user_id = u.user_id " +
                "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                "JOIN court c ON t.court_id = c.court_id " +
                "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                "ORDER BY b.booking_created_at DESC, b.booking_id DESC";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            BookingModel booking = new BookingModel();
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setBookingDate(rs.getDate("booking_date"));
            booking.setBookingStatus(rs.getString("booking_status"));
            booking.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
            booking.setCourtName(rs.getString("court_name"));
            booking.setSportName(rs.getString("sport_name"));
            booking.setStartTime(String.valueOf(rs.getTime("start_time")));
            booking.setEndTime(String.valueOf(rs.getTime("end_time")));
            booking.setUserFullName(rs.getString("user_full_name"));
            bookings.add(booking);
        }

        rs.close();
        pst.close();
        con.close();

        return bookings;
    }

    public void updateBookingStatus(int bookingId, String status) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, status);
        pst.setInt(2, bookingId);

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public int getBookingCountByStatus(String status) throws Exception {
        int count = 0;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM booking WHERE booking_status = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, status);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }

    public List<SportBookingReportModel> getSportBookingReport() throws Exception {
        List<SportBookingReportModel> reportList = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT st.sport_name, COUNT(b.booking_id) AS booking_count " +
                     "FROM booking b " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "GROUP BY st.sport_name " +
                     "ORDER BY booking_count DESC";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            SportBookingReportModel item = new SportBookingReportModel();
            item.setSportName(rs.getString("sport_name"));
            item.setBookingCount(rs.getInt("booking_count"));
            reportList.add(item);
        }

        rs.close();
        pst.close();
        con.close();

        return reportList;
    }
}