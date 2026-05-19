package com.dao;

import com.model.TimeSlotModel;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDAO {

    public List<TimeSlotModel> getAllTimeSlots() throws Exception {
        List<TimeSlotModel> timeSlots = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT t.timeSlot_id, t.slot_date, t.start_time, t.end_time, t.slot_status, t.court_id, " +
                     "c.court_name, st.sport_name " +
                     "FROM timeslot t " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "ORDER BY t.slot_date DESC, t.start_time ASC";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            TimeSlotModel slot = new TimeSlotModel();
            slot.setTimeSlotId(rs.getInt("timeSlot_id"));
            slot.setSlotDate(rs.getDate("slot_date"));
            slot.setStartTime(rs.getTime("start_time"));
            slot.setEndTime(rs.getTime("end_time"));
            slot.setSlotStatus(rs.getString("slot_status"));
            slot.setCourtId(rs.getInt("court_id"));
            slot.setCourtName(rs.getString("court_name"));
            slot.setSportName(rs.getString("sport_name"));
            slot.setBooked(false);
            timeSlots.add(slot);
        }

        rs.close();
        pst.close();
        con.close();

        return timeSlots;
    }

    public List<TimeSlotModel> getSlotsByCourtAndDate(int courtId, Date slotDate) throws Exception {
        List<TimeSlotModel> timeSlots = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT t.timeSlot_id, t.slot_date, t.start_time, t.end_time, t.slot_status, t.court_id, " +
                     "c.court_name, st.sport_name, " +
                     "CASE WHEN b.booking_id IS NOT NULL THEN 1 ELSE 0 END AS booked " +
                     "FROM timeslot t " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "LEFT JOIN booking b ON t.timeSlot_id = b.timeSlot_id AND b.booking_status <> 'cancelled' " +
                     "WHERE t.court_id = ? AND t.slot_date = ? " +
                     "ORDER BY t.start_time ASC";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, courtId);
        pst.setDate(2, slotDate);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            TimeSlotModel slot = new TimeSlotModel();
            slot.setTimeSlotId(rs.getInt("timeSlot_id"));
            slot.setSlotDate(rs.getDate("slot_date"));
            slot.setStartTime(rs.getTime("start_time"));
            slot.setEndTime(rs.getTime("end_time"));
            slot.setSlotStatus(rs.getString("slot_status"));
            slot.setCourtId(rs.getInt("court_id"));
            slot.setCourtName(rs.getString("court_name"));
            slot.setSportName(rs.getString("sport_name"));
            slot.setBooked(rs.getInt("booked") == 1);

            if (slot.isBooked()) {
                slot.setSlotStatus("booked");
            }

            timeSlots.add(slot);
        }

        rs.close();
        pst.close();
        con.close();

        return timeSlots;
    }

    public TimeSlotModel getSlotById(int timeSlotId) throws Exception {
        TimeSlotModel slot = null;

        Connection con = DBConnection.getConnection();

        String sql = "SELECT t.timeSlot_id, t.slot_date, t.start_time, t.end_time, t.slot_status, t.court_id, " +
                     "c.court_name, st.sport_name " +
                     "FROM timeslot t " +
                     "JOIN court c ON t.court_id = c.court_id " +
                     "JOIN sport_type st ON c.sportType_id = st.sportType_id " +
                     "WHERE t.timeSlot_id = ?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, timeSlotId);

        ResultSet rs = pst.executeQuery();

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
            slot.setBooked(false);
        }

        rs.close();
        pst.close();
        con.close();

        return slot;
    }

    public void updateTimeSlotStatus(int timeSlotId, String status) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE timeslot SET slot_status = ? WHERE timeSlot_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, status);
        pst.setInt(2, timeSlotId);

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public void generateDefaultTimeSlotsForMonth(int courtId, int year, int month) throws Exception {
        Connection con = DBConnection.getConnection();

        String checkSql = "SELECT COUNT(*) FROM timeslot WHERE court_id = ? AND slot_date BETWEEN ? AND ?";
        PreparedStatement checkPst = con.prepareStatement(checkSql);

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        checkPst.setInt(1, courtId);
        checkPst.setDate(2, Date.valueOf(startDate));
        checkPst.setDate(3, Date.valueOf(endDate));

        ResultSet checkRs = checkPst.executeQuery();
        if (checkRs.next() && checkRs.getInt(1) > 0) {
            checkRs.close();
            checkPst.close();
            con.close();
            return;
        }

        checkRs.close();
        checkPst.close();

        String insertSql = "INSERT INTO timeslot (slot_date, start_time, end_time, slot_status, court_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(insertSql);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (int hour = 6; hour < 21; hour++) {
                pst.setDate(1, Date.valueOf(date));
                pst.setTime(2, Time.valueOf(String.format("%02d:00:00", hour)));
                pst.setTime(3, Time.valueOf(String.format("%02d:00:00", hour + 1)));
                pst.setString(4, "available");
                pst.setInt(5, courtId);
                pst.addBatch();
            }
        }

        pst.executeBatch();
        pst.close();
        con.close();
    }
}