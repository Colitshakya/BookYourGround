package com.dao;

// Importing BookingModel to store booking details retrieved from or inserted into the database
import com.model.BookingModel;

// Importing SportBookingReportModel to store sport-wise booking report data
import com.model.SportBookingReportModel;

// Importing DBConnection to create database connection
import com.util.DBConnection;

// Importing SQL classes needed for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// Importing ArrayList and List to store multiple records
import java.util.ArrayList;
import java.util.List;

/**
 * BookingDAO handles all database operations related to bookings.
 *
 * This class is responsible for:
 * - Checking whether a time slot is already booked
 * - Inserting new booking records
 * - Counting total bookings
 * - Retrieving recent bookings for admin dashboard
 * - Retrieving all bookings for admin management
 * - Updating booking status
 * - Counting bookings by status
 * - Generating sport-wise booking reports
 */
public class BookingDAO {

    /**
     * Checks whether a selected time slot is already booked.
     *
     * This method checks the booking table using the given timeSlotId.
     * Cancelled bookings are ignored, so a cancelled slot can be booked again.
     *
     * @param timeSlotId the ID of the selected time slot
     * @return true if the time slot is already booked, otherwise false
     * @throws Exception if database operation fails
     */
    public boolean isSlotAlreadyBooked(int timeSlotId) throws Exception {
        // Default value is false, meaning the slot is not booked
        boolean booked = false;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to check if a booking exists for the selected time slot
        // Cancelled bookings are excluded from this check
        String sql = "SELECT booking_id FROM booking WHERE timeSlot_id = ? AND booking_status <> 'cancelled'";

        // Preparing SQL statement to prevent SQL injection
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting timeSlotId value in the query
        pst.setInt(1, timeSlotId);

        // Executing the query
        ResultSet rs = pst.executeQuery();

        // If a record exists, rs.next() returns true, meaning the slot is booked
        booked = rs.next();

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning booking availability result
        return booked;
    }

    /**
     * Inserts a new booking record into the booking table.
     *
     * This method also returns the generated booking ID after insertion.
     *
     * @param booking BookingModel object containing booking details
     * @return generated booking ID after successful insertion
     * @throws Exception if database operation fails
     */
    public int insertBooking(BookingModel booking) throws Exception {
        // Stores the generated booking ID after insertion
        int generatedBookingId = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to insert a new booking record
        String sql = "INSERT INTO booking (booking_date, booking_status, booking_created_at, user_id, timeSlot_id) " +
                     "VALUES (?, ?, ?, ?, ?)";

        // Preparing SQL statement and requesting generated keys after insertion
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Setting booking date from BookingModel
        pst.setDate(1, booking.getBookingDate());

        // Setting booking status from BookingModel
        pst.setString(2, booking.getBookingStatus());

        // Setting booking created timestamp from BookingModel
        pst.setTimestamp(3, booking.getBookingCreatedAt());

        // Setting user ID from BookingModel
        pst.setInt(4, booking.getUserId());

        // Setting time slot ID from BookingModel
        pst.setInt(5, booking.getTimeSlotId());

        // Executing insert query and storing number of affected rows
        int affectedRows = pst.executeUpdate();

        // If at least one row was inserted, get the generated booking ID
        if (affectedRows > 0) {
            ResultSet rs = pst.getGeneratedKeys();

            // Reads generated booking ID
            if (rs.next()) {
                generatedBookingId = rs.getInt(1);
            }

            // Closing generated keys result set
            rs.close();
        }

        // Closing database resources
        pst.close();
        con.close();

        // Returning generated booking ID
        return generatedBookingId;
    }

    /**
     * Counts the total number of bookings in the booking table.
     *
     * @return total booking count
     * @throws Exception if database operation fails
     */
    public int getTotalBookings() throws Exception {
        // Stores total booking count
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count all booking records
        String sql = "SELECT COUNT(*) FROM booking";

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

        // Returning total booking count
        return count;
    }

    /**
     * Retrieves the five most recent bookings for the admin dashboard.
     *
     * This method joins booking, user, timeslot, court, and sport_type tables
     * to display complete booking information.
     *
     * @return list of recent bookings
     * @throws Exception if database operation fails
     */
    public List<BookingModel> getRecentBookingsForAdmin() throws Exception {
        // Creating list to store booking records
        List<BookingModel> bookings = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to get five latest bookings with user, court, sport, and time slot details
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

        // Preparing and executing SQL query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each booking record
        while (rs.next()) {
            // Creating BookingModel object for each row
            BookingModel booking = new BookingModel();

            // Setting booking details from result set
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setBookingDate(rs.getDate("booking_date"));
            booking.setBookingStatus(rs.getString("booking_status"));
            booking.setCourtName(rs.getString("court_name"));
            booking.setSportName(rs.getString("sport_name"));
            booking.setStartTime(String.valueOf(rs.getTime("start_time")));
            booking.setEndTime(String.valueOf(rs.getTime("end_time")));
            booking.setUserFullName(rs.getString("user_full_name"));

            // Adding booking object to the list
            bookings.add(booking);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning recent booking list
        return bookings;
    }

    /**
     * Retrieves all bookings for admin booking management.
     *
     * This method returns complete booking details by joining related tables.
     *
     * @return list of all bookings
     * @throws Exception if database operation fails
     */
    public List<BookingModel> getAllBookingsForAdmin() throws Exception {
        // Creating list to store all booking records
        List<BookingModel> bookings = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to get all bookings with user, court, sport, and time slot details
        String sql = "SELECT b.booking_id, b.booking_date, b.booking_status, b.booking_created_at, " +
                "c.court_name, st.sport_name, t.start_time, t.end_time, " +
                "CONCAT(u.user_first_name, ' ', u.user_last_name) AS user_full_name " +
                "FROM booking b " +
                "JOIN user u ON b.user_id = u.user_id " +
                "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                "JOIN court c ON t.court_id = c.court_id " +
                "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                "ORDER BY b.booking_created_at DESC, b.booking_id DESC";

        // Preparing and executing SQL query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each booking record
        while (rs.next()) {
            // Creating BookingModel object for each booking row
            BookingModel booking = new BookingModel();

            // Setting booking details from result set
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setBookingDate(rs.getDate("booking_date"));
            booking.setBookingStatus(rs.getString("booking_status"));
            booking.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
            booking.setCourtName(rs.getString("court_name"));
            booking.setSportName(rs.getString("sport_name"));
            booking.setStartTime(String.valueOf(rs.getTime("start_time")));
            booking.setEndTime(String.valueOf(rs.getTime("end_time")));
            booking.setUserFullName(rs.getString("user_full_name"));

            // Adding booking object to the list
            bookings.add(booking);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning all booking records
        return bookings;
    }

    /**
     * Updates the status of a selected booking.
     *
     * @param bookingId ID of the booking to update
     * @param status new booking status
     * @throws Exception if database operation fails
     */
    public void updateBookingStatus(int bookingId, String status) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update booking status
        String sql = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting new booking status
        pst.setString(1, status);

        // Setting booking ID whose status should be updated
        pst.setInt(2, bookingId);

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }

    /**
     * Counts bookings by specific booking status.
     *
     * Example statuses can be confirmed, pending, or cancelled.
     *
     * @param status booking status to count
     * @return number of bookings with the given status
     * @throws Exception if database operation fails
     */
    public int getBookingCountByStatus(String status) throws Exception {
        // Stores booking count by status
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count bookings with a specific status
        String sql = "SELECT COUNT(*) FROM booking WHERE booking_status = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting booking status in query
        pst.setString(1, status);

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

        // Returning booking count by status
        return count;
    }

    /**
     * Generates sport-wise booking report.
     *
     * This method counts how many bookings are made for each sport type.
     *
     * @return list of sport booking report records
     * @throws Exception if database operation fails
     */
    public List<SportBookingReportModel> getSportBookingReport() throws Exception {
        // Creating list to store sport booking report data
        List<SportBookingReportModel> reportList = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count bookings grouped by sport name
        String sql = "SELECT st.sport_name, COUNT(b.booking_id) AS booking_count " +
                     "FROM booking b " +
                     "JOIN timeslot t ON b.timeSlot_id = t.timeSlot_id " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "GROUP BY st.sport_name " +
                     "ORDER BY booking_count DESC";

        // Preparing and executing SQL query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each sport report row
        while (rs.next()) {
            // Creating SportBookingReportModel object for each sport
            SportBookingReportModel item = new SportBookingReportModel();

            // Setting sport name from result set
            item.setSportName(rs.getString("sport_name"));

            // Setting booking count for that sport
            item.setBookingCount(rs.getInt("booking_count"));

            // Adding report item to the list
            reportList.add(item);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning sport booking report list
        return reportList;
    }
}
