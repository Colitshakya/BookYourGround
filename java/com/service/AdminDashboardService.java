package com.service;

import com.dao.BookingDAO;
import com.dao.CourtDAO;
import com.dao.UserDAO;
import com.model.AdminModel;

public class AdminDashboardService {

    public AdminModel getDashboardData() throws Exception {
        UserDAO userDAO = new UserDAO();
        CourtDAO courtDAO = new CourtDAO();
        BookingDAO bookingDAO = new BookingDAO();

        AdminModel dashboard = new AdminModel();
        dashboard.setTotalUsers(userDAO.getTotalUsers());
        dashboard.setPendingUsers(userDAO.getPendingUsers());
        dashboard.setTotalCourts(courtDAO.getTotalCourts());
        dashboard.setTotalBookings(bookingDAO.getTotalBookings());
        dashboard.setRecentBookings(bookingDAO.getRecentBookingsForAdmin());

        return dashboard;
    }
}