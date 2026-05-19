package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.BookingModel;
import com.model.UserModel;
import com.util.DBConnection;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void insertUser(UserModel user) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO user (user_first_name, user_last_name, user_email, user_phone, user_password, user_status, user_role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, user.getFirstName());
        pst.setString(2, user.getLastName());
        pst.setString(3, user.getEmail());
        pst.setString(4, user.getPhone());
        pst.setString(5, user.getPassword());
        pst.setString(6, user.getStatus());
        pst.setString(7, user.getRole());

        pst.executeUpdate();
        pst.close();
        con.close();
    }

    public UserModel getUserByEmail(String email) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM user WHERE user_email = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        UserModel user = null;

        if (rs.next()) {
            user = new UserModel();
            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("user_first_name"));
            user.setLastName(rs.getString("user_last_name"));
            user.setEmail(rs.getString("user_email"));
            user.setPhone(rs.getString("user_phone"));
            user.setPassword(rs.getString("user_password"));
            user.setStatus(rs.getString("user_status"));
            user.setRole(rs.getString("user_role"));
        }

        rs.close();
        pst.close();
        con.close();

        return user;
    }

    public boolean isEmailExists(String email) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT user_id FROM user WHERE user_email = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();
        boolean exists = rs.next();

        rs.close();
        pst.close();
        con.close();

        return exists;
    }

    public boolean isPhoneExists(String phone) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT user_id FROM user WHERE user_phone = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, phone);

        ResultSet rs = pst.executeQuery();
        boolean exists = rs.next();

        rs.close();
        pst.close();
        con.close();

        return exists;
    }
    
    public UserModel getUserById(int userId) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM `user` WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);

        ResultSet rs = pst.executeQuery();
        UserModel user = null;

        if (rs.next()) {
            user = new UserModel();
            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("user_first_name"));
            user.setLastName(rs.getString("user_last_name"));
            user.setEmail(rs.getString("user_email"));
            user.setPhone(rs.getString("user_phone"));
            user.setPassword(rs.getString("user_password"));
            user.setStatus(rs.getString("user_status"));
            user.setRole(rs.getString("user_role"));
        }

        rs.close();
        pst.close();
        con.close();

        return user;
    }
    
    public boolean isEmailExistsForOtherUser(String email, int userId) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT user_id FROM `user` WHERE user_email = ? AND user_id != ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);
        pst.setInt(2, userId);

        ResultSet rs = pst.executeQuery();
        boolean exists = rs.next();

        rs.close();
        pst.close();
        con.close();

        return exists;
    }

    public boolean isPhoneExistsForOtherUser(String phone, int userId) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT user_id FROM `user` WHERE user_phone = ? AND user_id != ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, phone);
        pst.setInt(2, userId);

        ResultSet rs = pst.executeQuery();
        boolean exists = rs.next();

        rs.close();
        pst.close();
        con.close();

        return exists;
    }

    public void updateUserProfile(UserModel user) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE `user` SET user_first_name = ?, user_last_name = ?, user_email = ?, user_phone = ? WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, user.getFirstName());
        pst.setString(2, user.getLastName());
        pst.setString(3, user.getEmail());
        pst.setString(4, user.getPhone());
        pst.setInt(5, user.getUserId());

        pst.executeUpdate();
        pst.close();
        con.close();
    }

    public void updateUserPassword(int userId, String hashedPassword) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE `user` SET user_password = ? WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, hashedPassword);
        pst.setInt(2, userId);

        pst.executeUpdate();
        pst.close();
        con.close();
    }
    
    public int getTotalBookingsByUserId(int userId) throws Exception {
        int count = 0;
        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM booking WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }

    public int getUpcomingBookingsByUserId(int userId) throws Exception {
        int count = 0;
        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) " +
                     "FROM booking b " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "WHERE b.user_id = ? " +
                     "AND t.slot_date >= CURDATE() " +
                     "AND b.booking_status IN ('confirmed', 'pending')";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }
    
    public List<BookingModel> getUserBookings(int userId) throws Exception {
        List<BookingModel> bookings = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT b.booking_id, c.court_name, s.sport_name, b.booking_date, " +
                     "t.start_time, t.end_time, b.booking_status " +
                     "FROM booking b " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type s ON c.sportType_id = s.sportType_id " +
                     "WHERE b.user_id = ? " +
                     "ORDER BY b.booking_date DESC, t.start_time ASC";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            BookingModel booking = new BookingModel();
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setCourtName(rs.getString("court_name"));
            booking.setSportName(rs.getString("sport_name"));
            booking.setBookingDate(rs.getDate("booking_date"));
            booking.setStartTime(String.valueOf(rs.getTime("start_time")));
            booking.setEndTime(String.valueOf(rs.getTime("end_time")));
            booking.setBookingStatus(rs.getString("booking_status"));
            bookings.add(booking);
        }

        rs.close();
        pst.close();
        con.close();

        return bookings;
    }

    public int getPendingPaymentsByUserId(int userId) throws Exception {
        int count = 0;
        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) " +
                     "FROM payment p " +
                     "JOIN booking b ON p.booking_id = b.booking_id " +
                     "WHERE b.user_id = ? " +
                     "AND p.payment_status = 'pending'";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }
    public void softDeleteUser(int userId) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE user SET user_status = ? WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, "deleted");
        pst.setInt(2, userId);

        pst.executeUpdate();

        pst.close();
        con.close();
    }
    public int getTotalUsers() throws Exception {
        int count = 0;
        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM user WHERE user_status <> 'deleted'";
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

    public int getPendingUsers() throws Exception {
        int count = 0;
        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM user WHERE user_status = 'pending'";
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
    public List<UserModel> getAllUsers() throws Exception {
        List<UserModel> users = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM user ORDER BY user_id DESC";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            UserModel user = new UserModel();
            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("user_first_name"));
            user.setLastName(rs.getString("user_last_name"));
            user.setEmail(rs.getString("user_email"));
            user.setPhone(rs.getString("user_phone"));
            user.setPassword(rs.getString("user_password"));
            user.setStatus(rs.getString("user_status"));
            users.add(user);
            user.setRole(rs.getString("user_role"));
        }

        rs.close();
        pst.close();
        con.close();

        return users;
    }

    public void updateUserStatus(int userId, String status) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE user SET user_status = ? WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, status);
        pst.setInt(2, userId);

        pst.executeUpdate();

        pst.close();
        con.close();
    }
}