package com.dao;

// Importing TimeSlotModel to store time slot data retrieved from the database
import com.model.TimeSlotModel;

// Importing DBConnection to create database connection
import com.util.DBConnection;

// Importing SQL classes needed for database operations
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

// Importing LocalDate to generate dates for monthly time slots
import java.time.LocalDate;

// Importing ArrayList and List to store multiple time slot records
import java.util.ArrayList;
import java.util.List;

/**
 * TimeSlotDAO handles all database operations related to court time slots.
 *
 * This class is responsible for:
 * - Retrieving all time slots
 * - Retrieving time slots by court and date
 * - Retrieving a time slot by ID
 * - Updating time slot status
 * - Generating default monthly time slots for a court
 */
public class TimeSlotDAO {

    /**
     * Retrieves all time slots from the database.
     *
     * This method joins timeslot, court, and sport_type tables so that
     * court name and sport name can be displayed together with time slot data.
     *
     * @return list of all time slots
     * @throws Exception if database operation fails
     */
    public List<TimeSlotModel> getAllTimeSlots() throws Exception {
        // Creating list to store time slot records
        List<TimeSlotModel> timeSlots = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve all time slots with court name and sport name
        String sql = "SELECT t.timeSlot_id, t.slot_date, t.start_time, t.end_time, t.slot_status, t.court_id, " +
                     "c.court_name, st.sport_name " +
                     "FROM timeslot t " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "ORDER BY t.slot_date DESC, t.start_time ASC";

        // Preparing and executing SQL query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each time slot record
        while (rs.next()) {
            // Creating TimeSlotModel object for each row
            TimeSlotModel slot = new TimeSlotModel();

            // Setting time slot details from database result
            slot.setTimeSlotId(rs.getInt("timeSlot_id"));
            slot.setSlotDate(rs.getDate("slot_date"));
            slot.setStartTime(rs.getTime("start_time"));
            slot.setEndTime(rs.getTime("end_time"));
            slot.setSlotStatus(rs.getString("slot_status"));
            slot.setCourtId(rs.getInt("court_id"));
            slot.setCourtName(rs.getString("court_name"));
            slot.setSportName(rs.getString("sport_name"));

            // By default, this method does not check booking table, so booked is set to false
            slot.setBooked(false);

            // Adding time slot object to the list
            timeSlots.add(slot);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning list of all time slots
        return timeSlots;
    }

    /**
     * Retrieves time slots for a selected court and selected date.
     *
     * This method also checks whether each time slot is already booked
     * by using a LEFT JOIN with the booking table.
     *
     * @param courtId ID of the selected court
     * @param slotDate selected booking date
     * @return list of time slots for the selected court and date
     * @throws Exception if database operation fails
     */
    public List<TimeSlotModel> getSlotsByCourtAndDate(int courtId, Date slotDate) throws Exception {
        // Creating list to store time slots for selected court and date
        List<TimeSlotModel> timeSlots = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve time slots and check if they are already booked
        String sql = "SELECT t.timeSlot_id, t.slot_date, t.start_time, t.end_time, t.slot_status, t.court_id, " +
                     "c.court_name, st.sport_name, " +
                     "CASE WHEN b.booking_id IS NOT NULL THEN 1 ELSE 0 END AS booked " +
                     "FROM timeslot t " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN booking b ON t.timeSlot_id = b.timeSlot_id AND b.booking_status <> 'cancelled' " +
                     "WHERE t.court_id = ? AND t.slot_date = ? " +
                     "ORDER BY t.start_time ASC";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting selected court ID in the query
        pst.setInt(1, courtId);

        // Setting selected slot date in the query
        pst.setDate(2, slotDate);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // Looping through each time slot record
        while (rs.next()) {
            // Creating TimeSlotModel object for each row
            TimeSlotModel slot = new TimeSlotModel();

            // Setting time slot details from database result
            slot.setTimeSlotId(rs.getInt("timeSlot_id"));
            slot.setSlotDate(rs.getDate("slot_date"));
            slot.setStartTime(rs.getTime("start_time"));
            slot.setEndTime(rs.getTime("end_time"));
            slot.setSlotStatus(rs.getString("slot_status"));
            slot.setCourtId(rs.getInt("court_id"));
            slot.setCourtName(rs.getString("court_name"));
            slot.setSportName(rs.getString("sport_name"));

            // Sets booked as true if booking record exists for this time slot
            slot.setBooked(rs.getInt("booked") == 1);

            // If the slot is already booked, display its status as booked
            if (slot.isBooked()) {
                slot.setSlotStatus("booked");
            }

            // Adding time slot object to the list
            timeSlots.add(slot);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning time slots for selected court and date
        return timeSlots;
    }

    /**
     * Retrieves a single time slot using its time slot ID.
     *
     * This method also joins court and sport_type tables to include
     * court name and sport name.
     *
     * @param timeSlotId ID of the selected time slot
     * @return TimeSlotModel object if found, otherwise null
     * @throws Exception if database operation fails
     */
    public TimeSlotModel getSlotById(int timeSlotId) throws Exception {
        // TimeSlotModel object is initially null and will be filled if record is found
        TimeSlotModel slot = null;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve one time slot by ID
        String sql = "SELECT t.timeSlot_id, t.slot_date, t.start_time, t.end_time, t.slot_status, t.court_id, " +
                     "c.court_name, st.sport_name " +
                     "FROM timeslot t " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "WHERE t.timeSlot_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting selected time slot ID in the query
        pst.setInt(1, timeSlotId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // If time slot record exists, create and fill TimeSlotModel object
        if (rs.next()) {
            slot = new TimeSlotModel();
            slot.setTimeSlotId(rs.getInt("timeSlot_id"));
            slot.setSlotDate(rs.getDate("slot_date"));
            slot.setStartTime(rs.getTime("start_time"));
            slot.setEndTime(rs.getTime("end_time"));
            slot.setSlotStatus(rs.getString("slot_status"));
            slot.setCourtId(rs.getInt("court_id"));
            slot.setCourtName(rs.getString("court_name"));
            slot.setSportName(rs.getString("sport_name"));

            // This method does not check booking table, so booked is set to false
            slot.setBooked(false);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning selected time slot detail
        return slot;
    }

    /**
     * Updates the status of a selected time slot.
     *
     * Example statuses can be available, booked, or inactive.
     *
     * @param timeSlotId ID of the time slot to update
     * @param status new time slot status
     * @throws Exception if database operation fails
     */
    public void updateTimeSlotStatus(int timeSlotId, String status) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update time slot status
        String sql = "UPDATE timeslot SET slot_status = ? WHERE timeSlot_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting new status value
        pst.setString(1, status);

        // Setting time slot ID whose status will be updated
        pst.setInt(2, timeSlotId);

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }

    /**
     * Generates default monthly time slots for a selected court.
     *
     * This method first checks whether time slots already exist for the selected
     * court and month. If they already exist, it stops to avoid duplicate slots.
     * If not, it generates hourly slots from 6:00 AM to 9:00 PM for each day.
     *
     * @param courtId ID of the court for which slots are generated
     * @param year selected year
     * @param month selected month
     * @throws Exception if database operation fails
     */
    public void generateDefaultTimeSlotsForMonth(int courtId, int year, int month) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to check if time slots already exist for the selected court and month
        String checkSql = "SELECT COUNT(*) FROM timeslot WHERE court_id = ? AND slot_date BETWEEN ? AND ?";

        // Preparing check query
        PreparedStatement checkPst = con.prepareStatement(checkSql);

        // Creating first date of the selected month
        LocalDate startDate = LocalDate.of(year, month, 1);

        // Creating last date of the selected month
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // Setting court ID in check query
        checkPst.setInt(1, courtId);

        // Setting start date of month
        checkPst.setDate(2, Date.valueOf(startDate));

        // Setting end date of month
        checkPst.setDate(3, Date.valueOf(endDate));

        // Executing check query
        ResultSet checkRs = checkPst.executeQuery();

        // If time slots already exist, stop the method to avoid duplicate generation
        if (checkRs.next() && checkRs.getInt(1) > 0) {
            checkRs.close();
            checkPst.close();
            con.close();
            return;
        }

        // Closing check query resources
        checkRs.close();
        checkPst.close();

        // SQL query to insert generated time slots into timeslot table
        String insertSql = "INSERT INTO timeslot (slot_date, start_time, end_time, slot_status, court_id) VALUES (?, ?, ?, ?, ?)";

        // Preparing insert query
        PreparedStatement pst = con.prepareStatement(insertSql);

        // Loops through each date from startDate to endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

            // Creates hourly slots from 6 AM to 9 PM
            for (int hour = 6; hour < 21; hour++) {
                // Sets slot date
                pst.setDate(1, Date.valueOf(date));

                // Sets slot start time
                pst.setTime(2, Time.valueOf(String.format("%02d:00:00", hour)));

                // Sets slot end time
                pst.setTime(3, Time.valueOf(String.format("%02d:00:00", hour + 1)));

                // Sets default slot status as available
                pst.setString(4, "available");

                // Sets court ID for the generated slot
                pst.setInt(5, courtId);

                // Adds this insert operation to the batch
                pst.addBatch();
            }
        }

        // Executes all insert operations together
        pst.executeBatch();

        // Closing database resources
        pst.close();
        con.close();
    }
}
