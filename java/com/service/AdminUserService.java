package com.service;

// Importing UserDAO to access user-related database methods
import com.dao.UserDAO;

// Importing UserModel to store user records
import com.model.UserModel;

// Importing List to store multiple user records
import java.util.List;

/**
 * AdminUserService contains business logic for admin user management.
 *
 * This service class acts as a bridge between AdminUsersController
 * and UserDAO.
 *
 * It is responsible for:
 * - Getting all users
 * - Updating user status
 */
public class AdminUserService {

    /**
     * Gets all user records for admin user management.
     *
     * @return list of all users
     * @throws Exception if user records cannot be retrieved
     */
    public List<UserModel> getAllUsers() throws Exception {
        // Creating UserDAO object to access user database operations
        UserDAO dao = new UserDAO();

        // Returning all users from DAO
        return dao.getAllUsers();
    }

    /**
     * Updates the status of a selected user.
     *
     * @param userId ID of the user to update
     * @param status new user status
     * @throws Exception if user status update fails
     */
    public void updateUserStatus(int userId, String status) throws Exception {
        // Creating UserDAO object to update user status
        UserDAO dao = new UserDAO();

        // Calling DAO method to update selected user status
        dao.updateUserStatus(userId, status);
    }
}
