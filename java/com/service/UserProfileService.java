package com.service;

// Importing UserDAO to access user profile and booking data
import com.dao.UserDAO;

// Importing List to store multiple booking records
import java.util.List;

// Importing BookingModel to store user booking history
import com.model.BookingModel;

// Importing UserModel to store user profile details
import com.model.UserModel;

// Importing PasswordUtil to check and hash passwords
import com.util.PasswordUtil;

/**
 * UserProfileService contains business logic for user profile management.
 *
 * This service is responsible for:
 * - Getting user profile data
 * - Updating user profile
 * - Changing user password
 * - Getting booking summary
 * - Getting booking history
 * - Getting pending payments
 * - Soft deleting user account
 */
public class UserProfileService {

    /**
     * Gets user profile data by user ID.
     *
     * @param userId ID of the logged-in user
     * @return UserModel object containing user profile details
     * @throws Exception if user profile cannot be retrieved
     */
    public UserModel getUserProfile(int userId) throws Exception {
        // Creating UserDAO object to retrieve user data
        UserDAO dao = new UserDAO();

        // Returning user profile by ID
        return dao.getUserById(userId);
    }

    /**
     * Updates user profile details.
     *
     * This method checks whether updated email or phone already belongs
     * to another user before updating profile.
     *
     * @param user UserModel object containing updated profile details
     * @throws Exception if duplicate email/phone exists or update fails
     */
    public void updateUserProfile(UserModel user) throws Exception {
        // Creating UserDAO object to access user database methods
        UserDAO dao = new UserDAO();

        // Checks if updated email is already used by another user
        if (dao.isEmailExistsForOtherUser(user.getEmail(), user.getUserId())) {
            throw new Exception("Email already exists.");
        }

        // Checks if updated phone number is already used by another user
        if (dao.isPhoneExistsForOtherUser(user.getPhone(), user.getUserId())) {
            throw new Exception("Phone number already exists.");
        }

        // Updates user profile in database
        dao.updateUserProfile(user);
    }

    /**
     * Changes user password after verifying current password.
     *
     * @param userId ID of the logged-in user
     * @param currentPassword current password entered by user
     * @param newPassword new password entered by user
     * @throws Exception if user is not found, current password is wrong, or update fails
     */
    public void changePassword(int userId, String currentPassword, String newPassword) throws Exception {
        // Creating UserDAO object to retrieve and update user password
        UserDAO dao = new UserDAO();

        // Getting user details by ID
        UserModel user = dao.getUserById(userId);

        // If user does not exist, throw error
        if (user == null) {
            throw new Exception("User not found.");
        }

        // Checks whether current password matches stored hashed password
        if (!PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect.");
        }

        // Hashing new password before saving
        String hashedPassword = PasswordUtil.getHashPassword(newPassword);

        // Updating password in database
        dao.updateUserPassword(userId, hashedPassword);
    }
    
    /**
     * Gets total number of bookings made by a user.
     *
     * @param userId ID of the logged-in user
     * @return total booking count
     * @throws Exception if booking count cannot be retrieved
     */
    public int getTotalBookings(int userId) throws Exception {
        // Creating UserDAO object to get booking count
        UserDAO dao = new UserDAO();

        // Returning total booking count for user
        return dao.getTotalBookingsByUserId(userId);
    }
    
    /**
     * Gets booking history of a user.
     *
     * @param userId ID of the logged-in user
     * @return list of user booking records
     * @throws Exception if booking history cannot be retrieved
     */
    public List<BookingModel> getUserBookings(int userId) throws Exception {
        // Creating UserDAO object to get user bookings
        UserDAO dao = new UserDAO();

        // Returning booking history for user
        return dao.getUserBookings(userId);
    }

    /**
     * Gets upcoming bookings of a user.
     *
     * @param userId ID of the logged-in user
     * @return number of upcoming bookings
     * @throws Exception if upcoming booking count cannot be retrieved
     */
    public int getUpcomingBookings(int userId) throws Exception {
        // Creating UserDAO object to get upcoming bookings
        UserDAO dao = new UserDAO();

        // Returning upcoming booking count for user
        return dao.getUpcomingBookingsByUserId(userId);
    }

    /**
     * Gets pending payment count for a user.
     *
     * @param userId ID of the logged-in user
     * @return number of pending payments
     * @throws Exception if pending payment count cannot be retrieved
     */
    public int getPendingPayments(int userId) throws Exception {
        // Creating UserDAO object to get pending payment count
        UserDAO dao = new UserDAO();

        // Returning pending payment count for user
        return dao.getPendingPaymentsByUserId(userId);
    }

    /**
     * Soft deletes user account.
     *
     * This changes user status instead of permanently deleting the record.
     *
     * @param userId ID of the user to soft delete
     * @throws Exception if soft delete fails
     */
    public void softDeleteUser(int userId) throws Exception {
        // Creating UserDAO object to update user status as deleted
        UserDAO dao = new UserDAO();

        // Calling DAO method to soft delete user
        dao.softDeleteUser(userId);
    }
}
