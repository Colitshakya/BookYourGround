package com.service;

// Importing DAO classes needed to get dashboard data
import com.dao.BookingDAO;
import com.dao.CourtDAO;
import com.dao.UserDAO;

// Importing AdminModel to store dashboard summary data
import com.model.AdminModel;

/**
 * AdminDashboardService contains business logic for the admin dashboard.
 *
 * This service class collects data from different DAO classes and stores
 * it inside AdminModel so it can be displayed on the admin dashboard page.
 *
 * It is responsible for:
 * - Getting total users
 * - Getting pending users
 * - Getting total courts
 * - Getting total bookings
 * - Getting recent bookings
 */
public class AdminDashboardService {

    /**
     * Gets all summary data required for the admin dashboard.
     *
     * @return AdminModel object containing dashboard data
     * @throws Exception if dashboard data cannot be retrieved
     */
    public AdminModel getDashboardData() throws Exception {
        // Creating UserDAO object to retrieve user-related counts
        UserDAO userDAO = new UserDAO();

        // Creating CourtDAO object to retrieve court-related counts
        CourtDAO courtDAO = new CourtDAO();

        // Creating BookingDAO object to retrieve booking-related data
        BookingDAO bookingDAO = new BookingDAO();

        // Creating AdminModel object to store dashboard data
        AdminModel dashboard = new AdminModel();

        // Setting total number of users
        dashboard.setTotalUsers(userDAO.getTotalUsers());

        // Setting total number of pending users
        dashboard.setPendingUsers(userDAO.getPendingUsers());

        // Setting total number of courts
        dashboard.setTotalCourts(courtDAO.getTotalCourts());

        // Setting total number of bookings
        dashboard.setTotalBookings(bookingDAO.getTotalBookings());

        // Setting recent booking list
        dashboard.setRecentBookings(bookingDAO.getRecentBookingsForAdmin());

        // Returning complete dashboard data
        return dashboard;
    }
}
