package com.service;

import com.dao.UserDAO;
import java.util.List;
import com.model.BookingModel;
import com.model.UserModel;
import com.util.PasswordUtil;

public class UserProfileService {

    public UserModel getUserProfile(int userId) throws Exception {
        UserDAO dao = new UserDAO();
        return dao.getUserById(userId);
    }

    public void updateUserProfile(UserModel user) throws Exception {
        UserDAO dao = new UserDAO();

        if (dao.isEmailExistsForOtherUser(user.getEmail(), user.getUserId())) {
            throw new Exception("Email already exists.");
        }

        if (dao.isPhoneExistsForOtherUser(user.getPhone(), user.getUserId())) {
            throw new Exception("Phone number already exists.");
        }

        dao.updateUserProfile(user);
    }

    public void changePassword(int userId, String currentPassword, String newPassword) throws Exception {
        UserDAO dao = new UserDAO();
        UserModel user = dao.getUserById(userId);

        if (user == null) {
            throw new Exception("User not found.");
        }

        if (!PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect.");
        }

        String hashedPassword = PasswordUtil.getHashPassword(newPassword);
        dao.updateUserPassword(userId, hashedPassword);
    }
    
    public int getTotalBookings(int userId) throws Exception {
        UserDAO dao = new UserDAO();
        return dao.getTotalBookingsByUserId(userId);
    }
    
    public List<BookingModel> getUserBookings(int userId) throws Exception {
        UserDAO dao = new UserDAO();
        return dao.getUserBookings(userId);
    }

    public int getUpcomingBookings(int userId) throws Exception {
        UserDAO dao = new UserDAO();
        return dao.getUpcomingBookingsByUserId(userId);
    }

    public int getPendingPayments(int userId) throws Exception {
        UserDAO dao = new UserDAO();
        return dao.getPendingPaymentsByUserId(userId);
    }
    public void softDeleteUser(int userId) throws Exception {
        UserDAO dao = new UserDAO();
        dao.softDeleteUser(userId);
    }
}