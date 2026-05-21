package com.service;

// Importing UserDAO to insert and check user records
import com.dao.UserDAO;

// Importing UserModel to store registration data
import com.model.UserModel;

// Importing PasswordUtil to hash user password
import com.util.PasswordUtil;

/**
 * RegisterService contains business logic for user registration.
 *
 * This service validates user details, checks duplicate email and phone,
 * hashes the password, sets default role/status, and sends user data to UserDAO.
 *
 * It is responsible for:
 * - Validating empty fields
 * - Validating phone number
 * - Validating password strength
 * - Checking duplicate email and phone
 * - Hashing password
 * - Setting user status and role
 * - Inserting new user
 */
public class RegisterService {

    /**
     * Validates and registers a new user.
     *
     * @param user UserModel object containing registration data
     * @throws Exception if validation fails or user cannot be inserted
     */
    public void addUser(UserModel user) throws Exception {
        // Creating UserDAO object to access user database methods
        UserDAO dao = new UserDAO();

        // Checks whether any required field is empty
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() ||
            user.getLastName() == null || user.getLastName().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty() ||
            user.getPhone() == null || user.getPhone().trim().isEmpty() ||
            user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new Exception("All fields are required.");
        }

        // Checks whether phone number is valid
        if (!isValidPhone(user.getPhone())) {
            throw new Exception("Phone number must be exactly 10 digits.");
        }

        // Checks whether password meets required strength
        if (!isValidPassword(user.getPassword())) {
            throw new Exception("Password must contain at least one uppercase letter, one number, and one special character.");
        }

        // Checks whether email already exists
        if (dao.isEmailExists(user.getEmail())) {
            throw new Exception("Email already exists.");
        }

        // Checks whether phone number already exists
        if (dao.isPhoneExists(user.getPhone())) {
            throw new Exception("Phone number already exists.");
        }

        // Hashing plain password before storing it in database
        String hashedPassword = PasswordUtil.getHashPassword(user.getPassword());

        // Setting hashed password back into user object
        user.setPassword(hashedPassword);

        // Sets default status as active if status is missing
        if (user.getStatus() == null || user.getStatus().isEmpty()) {
            user.setStatus("active");
        }

        // Setting user status as pending for admin approval
        user.setStatus("pending");

        // Setting default role as user
        user.setRole("user");

        // Inserting new user into database
        dao.insertUser(user);
    }

    /**
     * Validates phone number format.
     *
     * @param phone phone number entered by user
     * @return true if phone has exactly 10 digits, otherwise false
     */
    private boolean isValidPhone(String phone) {
        // Checks if phone number contains exactly 10 digits
        return phone.matches("\\d{10}");
    }

    /**
     * Validates password strength.
     *
     * Password must contain at least:
     * - One uppercase letter
     * - One number
     * - One special character
     *
     * @param password password entered by user
     * @return true if password is valid, otherwise false
     */
    private boolean isValidPassword(String password) {
        // Checks password using regular expression
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$");
    }
}
