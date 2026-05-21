package com.util;

// Importing BCrypt library for secure password hashing and verification
import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil is a utility class used for password security.
 *
 * This class provides reusable methods to:
 * - Hash plain text password using BCrypt
 * - Check plain password against stored hashed password
 */
public class PasswordUtil {

    // Cost factor defines how strong/slow the BCrypt hashing process is
    private static final int COST = 10;

    /**
     * Hashes a plain text password using BCrypt.
     *
     * @param password plain text password entered by user
     * @return hashed password
     */
    public static String getHashPassword(String password) {
        // Generates salt using cost factor and hashes the password
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
    }

    /**
     * Checks whether input password matches stored hashed password.
     *
     * @param inputPassword plain password entered during login
     * @param storedHashedPassword hashed password stored in database
     * @return true if password matches, otherwise false
     */
    public static boolean checkPassword(String inputPassword, String storedHashedPassword) {
        // Compares plain password with hashed password using BCrypt
        return BCrypt.checkpw(inputPassword, storedHashedPassword);
    }
}
