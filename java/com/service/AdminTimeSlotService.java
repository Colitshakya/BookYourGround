package com.service;

import com.dao.TimeSlotDAO;
import com.model.TimeSlotModel;

import java.util.List;

public class AdminTimeSlotService {

    public List<TimeSlotModel> getAllTimeSlots() throws Exception {
        TimeSlotDAO dao = new TimeSlotDAO();
        return dao.getAllTimeSlots();
    }

    public void updateTimeSlotStatus(int timeSlotId, String status) throws Exception {
        TimeSlotDAO dao = new TimeSlotDAO();
        dao.updateTimeSlotStatus(timeSlotId, status);
    }
}