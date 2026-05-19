package com.dao;

import com.model.CourtModel;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourtDAO {

    public int getTotalCourts() throws Exception {
        int count = 0;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM court";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }

    public List<CourtModel> getAllCourts() throws Exception {
        List<CourtModel> courts = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, st.sport_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "ORDER BY c.court_id DESC";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            CourtModel court = new CourtModel();
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
            courts.add(court);
        }

        rs.close();
        pst.close();
        con.close();

        return courts;
    }

    public int insertCourt(CourtModel court) throws Exception {
        int generatedCourtId = 0;

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO court (court_name, court_number, court_capacity, surface_type, price_per_hour, court_status, venue_id, sportType_id, staff_id, image_path) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

        int affectedRows = pst.executeUpdate();

        if (affectedRows > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                generatedCourtId = rs.getInt(1);
            }
            rs.close();
        }

        pst.close();
        con.close();

        return generatedCourtId;
    }

    public void updateCourtStatus(int courtId, String status) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE court SET court_status = ? WHERE court_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, status);
        pst.setInt(2, courtId);

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public int getCourtCountByStatus(String status) throws Exception {
        int count = 0;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM court WHERE court_status = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, status);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        pst.close();
        con.close();

        return count;
    }

    public List<CourtModel> getAllActiveCourtsForPublic() throws Exception {
        List<CourtModel> courts = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, " +
                     "st.sport_name, v.venue_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN venue v ON c.venue_id = v.venue_id " +
                     "WHERE c.court_status IN ('active', 'available') " +
                     "ORDER BY c.court_id DESC";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            CourtModel court = new CourtModel();
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
            courts.add(court);
        }

        rs.close();
        pst.close();
        con.close();

        return courts;
    }

    public CourtModel getCourtById(int courtId) throws Exception {
        CourtModel court = null;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, " +
                     "st.sport_name, v.venue_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN venue v ON c.venue_id = v.venue_id " +
                     "WHERE c.court_id = ?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, courtId);
        ResultSet rs = pst.executeQuery();

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

        rs.close();
        pst.close();
        con.close();

        return court;
    }
    public List<CourtModel> getCourtsBySport(String sportName) throws Exception {
        List<CourtModel> courts = new ArrayList<>();
        Connection con = DBConnection.getConnection();

        String sql = "SELECT c.court_id, c.court_name, c.court_number, c.court_capacity, " +
                     "c.surface_type, c.price_per_hour, c.court_status, c.image_path, " +
                     "c.venue_id, c.sportType_id, c.staff_id, " +
                     "st.sport_name, v.venue_name " +
                     "FROM court c " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN venue v ON c.venue_id = v.venue_id " +
                     "WHERE c.court_status IN ('active', 'available') AND st.sport_name = ? " +
                     "ORDER BY c.court_id DESC";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, sportName);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            CourtModel court = new CourtModel();
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

            courts.add(court);
        }
        rs.close();
        pst.close();
        con.close();
        return courts;
    }
}