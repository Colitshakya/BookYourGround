package com.dao;

// Importing SQL classes required for database connection, query execution, and result reading
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Importing BookingModel to store booking history records
import com.model.BookingModel;

// Importing UserModel to store user data retrieved from or inserted into the database
import com.model.UserModel;

// Importing DBConnection utility class to connect Java application with MySQL database
import com.util.DBConnection;

// Importing ArrayList and List to store multiple user or booking records
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO handles all database operations related to users.
 *
 * This class is responsible for:
 * - Inserting new users
 * - Retrieving user details by email or ID
 * - Checking duplicate email and phone numbers
 * - Updating user profile and password
 * - Getting user booking and payment summary
 * - Soft deleting users
 * - Getting user counts for admin dashboard
 * - Retrieving all users for admin management
 * - Updating user status
 */
public class UserDAO {

    /**
     * Inserts a new user record into the user table.
     *
     * @param user UserModel object containing user details
     * @throws Exception if database connection or insert operation fails
     */
    public void insertUser(UserModel user) throws Exception {
        // Creating database connection using DBConnection utility class
        Connection con = DBConnection.getConnection();

        // SQL query to insert user details into the user table
        String sql = "INSERT INTO user (user_first_name, user_last_name, user_email, user_phone, user_password, user_status, user_role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Preparing SQL statement to insert dynamic values safely
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting first name from UserModel
        pst.setString(1, user.getFirstName());

        // Setting last name from UserModel
        pst.setString(2, user.getLastName());

        // Setting email from UserModel
        pst.setString(3, user.getEmail());

        // Setting phone number from UserModel
        pst.setString(4, user.getPhone());

        // Setting password from UserModel
        pst.setString(5, user.getPassword());

        // Setting user status from UserModel
        pst.setString(6, user.getStatus());

        // Setting user role from UserModel
        pst.setString(7, user.getRole());

        // Executing insert query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }

    /**
     * Retrieves a user record by email address.
     *
     * This method is mainly used during login to find a user using email.
     *
     * @param email email address entered by the user
     * @return UserModel object if user exists, otherwise null
     * @throws Exception if database operation fails
     */
    public UserModel getUserByEmail(String email) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to find user by email
        String sql = "SELECT * FROM user WHERE user_email = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting email value in the query
        pst.setString(1, email);

        // Executing select query
        ResultSet rs = pst.executeQuery();

        // User object is initially null and will be filled if record is found
        UserModel user = null;

        // If user record exists, create UserModel object and set values
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

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning user object
        return user;
    }

    /**
     * Checks whether an email already exists in the user table.
     *
     * @param email email address to check
     * @return true if email exists, otherwise false
     * @throws Exception if database operation fails
     */
    public boolean isEmailExists(String email) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to check if email exists
        String sql = "SELECT user_id FROM user WHERE user_email = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting email value
        pst.setString(1, email);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // If rs.next() is true, email already exists
        boolean exists = rs.next();

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning result
        return exists;
    }

    /**
     * Checks whether a phone number already exists in the user table.
     *
     * @param phone phone number to check
     * @return true if phone number exists, otherwise false
     * @throws Exception if database operation fails
     */
    public boolean isPhoneExists(String phone) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to check if phone number exists
        String sql = "SELECT user_id FROM user WHERE user_phone = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting phone number value
        pst.setString(1, phone);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // If rs.next() is true, phone number already exists
        boolean exists = rs.next();

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning result
        return exists;
    }
    
    /**
     * Retrieves user details using user ID.
     *
     * @param userId ID of the selected user
     * @return UserModel object if user is found, otherwise null
     * @throws Exception if database operation fails
     */
    public UserModel getUserById(int userId) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve user by ID
        String sql = "SELECT * FROM `user` WHERE user_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting user ID value
        pst.setInt(1, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // User object is initially null
        UserModel user = null;

        // If user record exists, fill UserModel object
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

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning selected user
        return user;
    }
    
    /**
     * Checks whether an email already belongs to another user.
     *
     * This is used during profile update to prevent duplicate emails.
     *
     * @param email updated email address
     * @param userId current user's ID
     * @return true if email belongs to another user, otherwise false
     * @throws Exception if database operation fails
     */
    public boolean isEmailExistsForOtherUser(String email, int userId) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query checks same email but excludes current user ID
        String sql = "SELECT user_id FROM `user` WHERE user_email = ? AND user_id != ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting email value
        pst.setString(1, email);

        // Setting current user ID to exclude from duplicate check
        pst.setInt(2, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // If record exists, another user already has this email
        boolean exists = rs.next();

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning result
        return exists;
    }

    /**
     * Checks whether a phone number already belongs to another user.
     *
     * This is used during profile update to prevent duplicate phone numbers.
     *
     * @param phone updated phone number
     * @param userId current user's ID
     * @return true if phone number belongs to another user, otherwise false
     * @throws Exception if database operation fails
     */
    public boolean isPhoneExistsForOtherUser(String phone, int userId) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query checks same phone number but excludes current user ID
        String sql = "SELECT user_id FROM `user` WHERE user_phone = ? AND user_id != ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting phone number value
        pst.setString(1, phone);

        // Setting current user ID to exclude from duplicate check
        pst.setInt(2, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // If record exists, another user already has this phone number
        boolean exists = rs.next();

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning result
        return exists;
    }

    /**
     * Updates user profile details.
     *
     * @param user UserModel object containing updated profile details
     * @throws Exception if database operation fails
     */
    public void updateUserProfile(UserModel user) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update user profile fields
        String sql = "UPDATE `user` SET user_first_name = ?, user_last_name = ?, user_email = ?, user_phone = ? WHERE user_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting updated first name
        pst.setString(1, user.getFirstName());

        // Setting updated last name
        pst.setString(2, user.getLastName());

        // Setting updated email
        pst.setString(3, user.getEmail());

        // Setting updated phone number
        pst.setString(4, user.getPhone());

        // Setting user ID to update the correct user
        pst.setInt(5, user.getUserId());

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }

    /**
     * Updates user's password.
     *
     * The password passed to this method should already be hashed.
     *
     * @param userId ID of the user whose password is updated
     * @param hashedPassword new hashed password
     * @throws Exception if database operation fails
     */
    public void updateUserPassword(int userId, String hashedPassword) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update user password
        String sql = "UPDATE `user` SET user_password = ? WHERE user_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting new hashed password
        pst.setString(1, hashedPassword);

        // Setting user ID
        pst.setInt(2, userId);

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }
    
    /**
     * Counts total bookings made by a specific user.
     *
     * @param userId ID of the user
     * @return total number of bookings made by the user
     * @throws Exception if database operation fails
     */
    public int getTotalBookingsByUserId(int userId) throws Exception {
        // Stores total booking count
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count bookings for selected user
        String sql = "SELECT COUNT(*) FROM booking WHERE user_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting user ID
        pst.setInt(1, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // Reading count result
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning total booking count
        return count;
    }

    /**
     * Counts upcoming bookings of a specific user.
     *
     * Upcoming bookings are bookings with today's or future slot date
     * and status confirmed or pending.
     *
     * @param userId ID of the user
     * @return number of upcoming bookings
     * @throws Exception if database operation fails
     */
    public int getUpcomingBookingsByUserId(int userId) throws Exception {
        // Stores upcoming booking count
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count upcoming bookings using booking and timeslot tables
        String sql = "SELECT COUNT(*) " +
                     "FROM booking b " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "WHERE b.user_id = ? " +
                     "AND t.slot_date >= CURDATE() " +
                     "AND b.booking_status IN ('confirmed', 'pending')";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting user ID
        pst.setInt(1, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // Reading count result
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning upcoming booking count
        return count;
    }
    
    /**
     * Retrieves booking history of a specific user.
     *
     * This method joins booking, timeslot, court, and sport_type tables
     * to display complete booking information.
     *
     * @param userId ID of the user
     * @return list of booking records made by the user
     * @throws Exception if database operation fails
     */
    public List<BookingModel> getUserBookings(int userId) throws Exception {
        // Creating list to store booking history records
        List<BookingModel> bookings = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve user booking history with court and sport details
        String sql = "SELECT b.booking_id, c.court_name, s.sport_name, b.booking_date, " +
                     "t.start_time, t.end_time, b.booking_status " +
                     "FROM booking b " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type s ON c.sportType_id = s.sportType_id " +
                     "WHERE b.user_id = ? " +
                     "ORDER BY b.booking_date DESC, t.start_time ASC";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting user ID
        pst.setInt(1, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // Looping through each booking record
        while (rs.next()) {
            // Creating BookingModel object for each booking row
            BookingModel booking = new BookingModel();

            // Setting booking details from result set
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setCourtName(rs.getString("court_name"));
            booking.setSportName(rs.getString("sport_name"));
            booking.setBookingDate(rs.getDate("booking_date"));
            booking.setStartTime(String.valueOf(rs.getTime("start_time")));
            booking.setEndTime(String.valueOf(rs.getTime("end_time")));
            booking.setBookingStatus(rs.getString("booking_status"));

            // Adding booking object to the list
            bookings.add(booking);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning user's booking history
        return bookings;
    }

    /**
     * Counts pending payments for a specific user.
     *
     * This method joins payment and booking tables because payment is linked
     * to booking, and booking is linked to user.
     *
     * @param userId ID of the user
     * @return number of pending payments
     * @throws Exception if database operation fails
     */
    public int getPendingPaymentsByUserId(int userId) throws Exception {
        // Stores pending payment count
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count pending payments for selected user
        String sql = "SELECT COUNT(*) " +
                     "FROM payment p " +
                     "JOIN booking b ON p.booking_id = b.booking_id " +
                     "WHERE b.user_id = ? " +
                     "AND p.payment_status = 'pending'";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting user ID
        pst.setInt(1, userId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // Reading count result
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning pending payment count
        return count;
    }

    /**
     * Soft deletes a user account by changing user status to deleted.
     *
     * This does not permanently remove the user from the database.
     *
     * @param userId ID of the user to soft delete
     * @throws Exception if database operation fails
     */
    public void softDeleteUser(int userId) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update user status as deleted
        String sql = "UPDATE user SET user_status = ? WHERE user_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting status as deleted
        pst.setString(1, "deleted");

        // Setting user ID
        pst.setInt(2, userId);

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }

    /**
     * Counts total users whose status is not deleted.
     *
     * @return total active/non-deleted users
     * @throws Exception if database operation fails
     */
    public int getTotalUsers() throws Exception {
        // Stores total user count
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count all users except deleted users
        String sql = "SELECT COUNT(*) FROM user WHERE user_status <> 'deleted'";

        // Preparing and executing query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Reading count result
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning total user count
        return count;
    }

    /**
     * Counts users with pending status.
     *
     * @return number of pending users
     * @throws Exception if database operation fails
     */
    public int getPendingUsers() throws Exception {
        // Stores pending user count
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count pending users
        String sql = "SELECT COUNT(*) FROM user WHERE user_status = 'pending'";

        // Preparing and executing query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Reading count result
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning pending user count
        return count;
    }
    
    /**
     * Retrieves all user records for admin user management.
     *
     * @return list of all users
     * @throws Exception if database operation fails
     */
    public List<UserModel> getAllUsers() throws Exception {
        // Creating list to store user records
        List<UserModel> users = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve all users in descending order
        String sql = "SELECT * FROM user ORDER BY user_id DESC";

        // Preparing and executing query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each user record
        while (rs.next()) {
            // Creating UserModel object for each user row
            UserModel user = new UserModel();

            // Setting user details from database result
            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("user_first_name"));
            user.setLastName(rs.getString("user_last_name"));
            user.setEmail(rs.getString("user_email"));
            user.setPhone(rs.getString("user_phone"));
            user.setPassword(rs.getString("user_password"));
            user.setStatus(rs.getString("user_status"));

            // Adding user object to the list
            users.add(user);

            // Setting user role from database result
            user.setRole(rs.getString("user_role"));
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning user list
        return users;
    }

    /**
     * Updates the status of a selected user.
     *
     * Example statuses can be active, inactive, pending, or deleted.
     *
     * @param userId ID of the user to update
     * @param status new user status
     * @throws Exception if database operation fails
     */
    public void updateUserStatus(int userId, String status) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update user status
        String sql = "UPDATE user SET user_status = ? WHERE user_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting new user status
        pst.setString(1, status);

        // Setting user ID
        pst.setInt(2, userId);

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }
}
