package com.dao;

// Importing CourtModel to store court data retrieved from or inserted into the database
import com.model.CourtModel;

// Importing DBConnection to create database connection
import com.util.DBConnection;

// Importing SQL classes needed for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// Importing ArrayList and List to store multiple court records
import java.util.ArrayList;
import java.util.List;

/**
 * CourtDAO handles all database operations related to courts.
 *
 * This class is responsible for:
 * - Counting total courts
 * - Retrieving all courts for admin
 * - Adding new court records
 * - Updating court status
 * - Counting courts by status
 * - Retrieving active courts for public users
 * - Retrieving court details by court ID
 * - Retrieving courts by sport type
 */
public class CourtDAO {

    /**
     * Counts the total number of courts in the court table.
     *
     * @return total number of courts
     * @throws Exception if database operation fails
     */
    public int getTotalCourts() throws Exception {
        // Stores total court count
        int count = 0;

        // Creating database connection using DBConnection utility class
        Connection con = DBConnection.getConnection();

        // SQL query to count all records from court table
        String sql = "SELECT COUNT(*) FROM court";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Executing select query
        ResultSet rs = pst.executeQuery();

        // If result exists, get the count value
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning total court count
        return count;
    }

    /**
     * Retrieves all court records for admin court management.
     *
     * This method joins court and sport_type tables so that sport name
     * can also be displayed with court details.
     *
     * @return list of all courts
     * @throws Exception if database operation fails
     */
    public List<CourtModel> getAllCourts() throws Exception {
        // Creating list to store court records
        List<CourtModel> courts = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve all courts with their sport name
        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, st.sport_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "ORDER BY c.court_id DESC";

        // Preparing and executing SQL query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each court record from the result set
        while (rs.next()) {
            // Creating CourtModel object for each court row
            CourtModel court = new CourtModel();

            // Setting court details from database result
            court.setCourtId(rs.getInt("court_id"));
            court.setCourtName(rs.getString("court_name"));
            court.setCourtNumber(rs.getString("court_number"));
            court.setCourtCapacity(rs.getInt("court_capacity"));
            court.setSurfaceType(rs.getString("surface_type"));
            court.setPricePerHour(rs.getBigDecimal("price_per_hour"));
            court.setCourtStatus(rs.getString("court_status"));
            court.setImagePath(rs.getString("image_path"));
            court.setVenueId(rs.getInt("venue_id"));
            court.setSportTypeId(rs.getInt("sportType_id"));
            court.setStaffId(rs.getInt("staff_id"));
            court.setSportName(rs.getString("sport_name"));

            // Adding court object to the court list
            courts.add(court);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning list of all courts
        return courts;
    }

    /**
     * Inserts a new court record into the court table.
     *
     * This method also returns the generated court ID after successful insertion.
     *
     * @param court CourtModel object containing new court details
     * @return generated court ID
     * @throws Exception if database operation fails
     */
    public int insertCourt(CourtModel court) throws Exception {
        // Stores generated court ID after insertion
        int generatedCourtId = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to insert new court details into court table
        String sql = "INSERT INTO court (court_name, court_number, court_capacity, surface_type, price_per_hour, court_status, venue_id, sportType_id, staff_id, image_path) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Preparing SQL statement and requesting generated keys
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Setting court values from CourtModel object
        pst.setString(1, court.getCourtName());
        pst.setString(2, court.getCourtNumber());
        pst.setInt(3, court.getCourtCapacity());
        pst.setString(4, court.getSurfaceType());
        pst.setBigDecimal(5, court.getPricePerHour());
        pst.setString(6, court.getCourtStatus());
        pst.setInt(7, court.getVenueId());
        pst.setInt(8, court.getSportTypeId());
        pst.setInt(9, court.getStaffId());
        pst.setString(10, court.getImagePath());

        // Executing insert query and storing affected row count
        int affectedRows = pst.executeUpdate();

        // If insert was successful, retrieve generated court ID
        if (affectedRows > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                generatedCourtId = rs.getInt(1);
            }
            rs.close();
        }

        // Closing database resources
        pst.close();
        con.close();

        // Returning generated court ID
        return generatedCourtId;
    }

    /**
     * Updates the status of a selected court.
     *
     * @param courtId ID of the court to update
     * @param status new court status
     * @throws Exception if database operation fails
     */
    public void updateCourtStatus(int courtId, String status) throws Exception {
        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to update court status using court ID
        String sql = "UPDATE court SET court_status = ? WHERE court_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting new court status
        pst.setString(1, status);

        // Setting court ID for the court that needs to be updated
        pst.setInt(2, courtId);

        // Executing update query
        pst.executeUpdate();

        // Closing database resources
        pst.close();
        con.close();
    }

    /**
     * Counts courts based on a specific court status.
     *
     * Example statuses can be active, available, or inactive.
     *
     * @param status court status to count
     * @return number of courts with the given status
     * @throws Exception if database operation fails
     */
    public int getCourtCountByStatus(String status) throws Exception {
        // Stores court count by status
        int count = 0;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to count courts with selected status
        String sql = "SELECT COUNT(*) FROM court WHERE court_status = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting status value in query
        pst.setString(1, status);

        // Executing select query
        ResultSet rs = pst.executeQuery();

        // Reading count result
        if (rs.next()) {
            count = rs.getInt(1);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning court count by status
        return count;
    }

    /**
     * Retrieves all active or available courts for public users.
     *
     * This method joins court, sport_type, and venue tables so that users
     * can see court details along with sport name and venue name.
     *
     * @return list of active or available courts
     * @throws Exception if database operation fails
     */
    public List<CourtModel> getAllActiveCourtsForPublic() throws Exception {
        // Creating list to store active court records
        List<CourtModel> courts = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve only active or available courts with sport and venue details
        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, " +
                     "st.sport_name, v.venue_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN venue v ON c.venue_id = v.venue_id " +
                     "WHERE c.court_status IN ('active', 'available') " +
                     "ORDER BY c.court_id DESC";

        // Preparing and executing SQL query
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Looping through each active court record
        while (rs.next()) {
            // Creating CourtModel object for each row
            CourtModel court = new CourtModel();

            // Setting court details from database result
            court.setCourtId(rs.getInt("court_id"));
            court.setCourtName(rs.getString("court_name"));
            court.setCourtNumber(rs.getString("court_number"));
            court.setCourtCapacity(rs.getInt("court_capacity"));
            court.setSurfaceType(rs.getString("surface_type"));
            court.setPricePerHour(rs.getBigDecimal("price_per_hour"));
            court.setCourtStatus(rs.getString("court_status"));
            court.setImagePath(rs.getString("image_path"));
            court.setVenueId(rs.getInt("venue_id"));
            court.setSportTypeId(rs.getInt("sportType_id"));
            court.setStaffId(rs.getInt("staff_id"));
            court.setSportName(rs.getString("sport_name"));
            court.setVenueName(rs.getString("venue_name"));

            // Adding court object to the list
            courts.add(court);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning active court list
        return courts;
    }

    /**
     * Retrieves one court record using court ID.
     *
     * This method is used when showing a selected court's detail page.
     *
     * @param courtId ID of the selected court
     * @return CourtModel object if found, otherwise null
     * @throws Exception if database operation fails
     */
    public CourtModel getCourtById(int courtId) throws Exception {
        // Court object is initially null and will be filled only if a record is found
        CourtModel court = null;

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve one court with sport name and venue name
        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, " +
                     "st.sport_name, v.venue_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN venue v ON c.venue_id = v.venue_id " +
                     "WHERE c.court_id = ?";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting court ID in query
        pst.setInt(1, courtId);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // If court record exists, create and fill CourtModel object
        if (rs.next()) {
            court = new CourtModel();
            court.setCourtId(rs.getInt("court_id"));
            court.setCourtName(rs.getString("court_name"));
            court.setCourtNumber(rs.getString("court_number"));
            court.setCourtCapacity(rs.getInt("court_capacity"));
            court.setSurfaceType(rs.getString("surface_type"));
            court.setPricePerHour(rs.getBigDecimal("price_per_hour"));
            court.setCourtStatus(rs.getString("court_status"));
            court.setImagePath(rs.getString("image_path"));
            court.setVenueId(rs.getInt("venue_id"));
            court.setSportTypeId(rs.getInt("sportType_id"));
            court.setStaffId(rs.getInt("staff_id"));
            court.setSportName(rs.getString("sport_name"));
            court.setVenueName(rs.getString("venue_name"));
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning selected court detail
        return court;
    }

    /**
     * Retrieves active or available courts by sport name.
     *
     * This method is used for sport-specific pages such as Futsal,
     * Basketball, Tennis, and Pickleball.
     *
     * @param sportName name of the sport to filter courts
     * @return list of courts matching the selected sport
     * @throws Exception if database operation fails
     */
    public List<CourtModel> getCourtsBySport(String sportName) throws Exception {
        // Creating list to store courts filtered by sport
        List<CourtModel> courts = new ArrayList<>();

        // Creating database connection
        Connection con = DBConnection.getConnection();

        // SQL query to retrieve active or available courts by sport name
        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, " +
                     "st.sport_name, v.venue_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN venue v ON c.venue_id = v.venue_id " +
                     "WHERE c.court_status IN ('active', 'available') AND st.sport_name = ? " +
                     "ORDER BY c.court_id DESC";

        // Preparing SQL statement
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting selected sport name in the query
        pst.setString(1, sportName);

        // Executing query
        ResultSet rs = pst.executeQuery();

        // Looping through each matching court record
        while (rs.next()) {
            // Creating CourtModel object for each court row
            CourtModel court = new CourtModel();

            // Setting court details from result set
            court.setCourtId(rs.getInt("court_id"));
            court.setCourtName(rs.getString("court_name"));
            court.setCourtNumber(rs.getString("court_number"));
            court.setCourtCapacity(rs.getInt("court_capacity"));
            court.setSurfaceType(rs.getString("surface_type"));
            court.setPricePerHour(rs.getBigDecimal("price_per_hour"));
            court.setCourtStatus(rs.getString("court_status"));
            court.setImagePath(rs.getString("image_path"));
            court.setVenueId(rs.getInt("venue_id"));
            court.setSportTypeId(rs.getInt("sportType_id"));
            court.setStaffId(rs.getInt("staff_id"));
            court.setSportName(rs.getString("sport_name"));
            court.setVenueName(rs.getString("venue_name"));

            // Adding court object to the filtered court list
            courts.add(court);
        }

        // Closing database resources
        rs.close();
        pst.close();
        con.close();

        // Returning sport-wise court list
        return courts;
    }
}
