package com.service;

import com.dao.CourtDAO;
import com.dao.TimeSlotDAO;
import com.model.CourtModel;

import java.time.LocalDate;
import java.util.List;

public class AdminCourtService {

    public List<CourtModel> getAllCourts() throws Exception {
        CourtDAO dao = new CourtDAO();
        return dao.getAllCourts();
    }

    public void updateCourtStatus(int courtId, String status) throws Exception {
        CourtDAO dao = new CourtDAO();
        dao.updateCourtStatus(courtId, status);
    }

    public void addCourt(CourtModel court) throws Exception {
        CourtDAO courtDAO = new CourtDAO();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

        int newCourtId = courtDAO.insertCourt(court);

        if (newCourtId <= 0) {
            throw new Exception("Court was added but no court ID was returned.");
        }

        LocalDate now = LocalDate.now();
        timeSlotDAO.generateDefaultTimeSlotsForMonth(newCourtId, now.getYear(), now.getMonthValue());

        LocalDate nextMonth = now.plusMonths(1);
        timeSlotDAO.generateDefaultTimeSlotsForMonth(newCourtId, nextMonth.getYear(), nextMonth.getMonthValue());
    }
}