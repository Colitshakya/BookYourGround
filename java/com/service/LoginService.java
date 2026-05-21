package com.service;

// Importing UserDAO to retrieve user details from database
import com.dao.UserDAO;

// Importing UserModel to store user login data
import com.model.UserModel;

// Importing PasswordUtil to check hashed password
import com.util.PasswordUtil;

/**
 * LoginService contains business logic for user login.
 *
 * This service is responsible for:
 * - Finding user by email
 * - Checking password using PasswordUtil
 * - Checking account status
 * - Returning valid logged-in user data
 */
public class LoginService {

    /**
     * Authenticates user login details.
     *
     * @param email email entered by the user
     * @param password plain password entered by the user
     * @return UserModel object if login is successful
     * @throws Exception if login fails or account is not active
     */
    public UserModel loginUser(String email, String password) throws Exception {
        // Creating UserDAO object to retrieve user from database
        UserDAO dao = new UserDAO();

        // Getting user record by email
        UserModel user = dao.getUserByEmail(email);

        // If no user exists with the entered email, throw error
        if (user == null) {
            throw new Exception("Invalid email or password.");
        }

        // Checks entered password with stored hashed password
        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new Exception("Invalid email or password.");
        }

        // Blocks login if account is pending approval
        if ("pending".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("Your account is waiting for admin approval.");
        }

        // Blocks login if account is inactive
        if ("inactive".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("Your account is currently inactive. Please contact admin.");
        }

        // Blocks login if account is deleted
        if ("deleted".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("This account is no longer available.");
        }

        // Blocks login if account status is not active
        if (!"active".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("Your account cannot be used right now.");
        }

        // Returning valid user object after successful login
        return user;
    }
}
