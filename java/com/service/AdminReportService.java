package com.service;

// Importing DAO classes needed for report data
import com.dao.BookingDAO;
import com.dao.CourtDAO;
import com.dao.UserDAO;

// Importing report model classes
import com.model.AdminReportModel;
import com.model.SportBookingReportModel;

// Importing List to store sport-wise booking report records
import java.util.List;

/**
 * AdminReportService contains business logic for generating admin reports.
 *
 * This service collects report data from different DAO classes and calculates
 * percentages required for displaying booking and sport reports.
 *
 * It is responsible for:
 * - Getting total users, courts, and bookings
 * - Getting booking counts by status
 * - Getting court counts by status
 * - Calculating booking percentages
 * - Creating sport-wise booking report data
 */
public class AdminReportService {

    /**
     * Gets complete report data for admin reports page.
     *
     * @return AdminReportModel object containing report data
     * @throws Exception if report data cannot be retrieved
     */
    public AdminReportModel getReportData() throws Exception {
        // Creating DAO objects to retrieve report data
        UserDAO userDAO = new UserDAO();
        CourtDAO courtDAO = new CourtDAO();
        BookingDAO bookingDAO = new BookingDAO();

        // Creating AdminReportModel object to store report data
        AdminReportModel report = new AdminReportModel();

        // Getting summary counts from DAO classes
        int totalUsers = userDAO.getTotalUsers();
        int totalCourts = courtDAO.getTotalCourts();
        int totalBookings = bookingDAO.getTotalBookings();
        int confirmed = bookingDAO.getBookingCountByStatus("confirmed");
        int pending = bookingDAO.getBookingCountByStatus("pending");
        int cancelled = bookingDAO.getBookingCountByStatus("cancelled");
        int activeCourts = courtDAO.getCourtCountByStatus("active");
        int inactiveCourts = courtDAO.getCourtCountByStatus("inactive");

        // Setting summary data into report model
        report.setTotalUsers(totalUsers);
        report.setTotalCourts(totalCourts);
        report.setTotalBookings(totalBookings);
        report.setConfirmedBookings(confirmed);
        report.setPendingBookings(pending);
        report.setCancelledBookings(cancelled);
        report.setActiveCourts(activeCourts);
        report.setInactiveCourts(inactiveCourts);

        // Calculating booking status percentages only if total bookings exist
        if (totalBookings > 0) {
            report.setConfirmedPercent((confirmed * 100.0) / totalBookings);
            report.setPendingPercent((pending * 100.0) / totalBookings);
            report.setCancelledPercent((cancelled * 100.0) / totalBookings);
        } else {
            // Setting percentages to 0 if there are no bookings
            report.setConfirmedPercent(0);
            report.setPendingPercent(0);
            report.setCancelledPercent(0);
        }

        // Getting sport-wise booking report list from BookingDAO
        List<SportBookingReportModel> sportReportList = bookingDAO.getSportBookingReport();

        // Variable to store highest booking count among sports
        int maxCount = 0;

        // Finding highest booking count from sport report list
        for (SportBookingReportModel item : sportReportList) {
            if (item.getBookingCount() > maxCount) {
                maxCount = item.getBookingCount();
            }
        }

        // Calculating percentage for each sport based on maximum booking count
        for (SportBookingReportModel item : sportReportList) {
            if (maxCount > 0) {
                item.setPercentage((item.getBookingCount() * 100.0) / maxCount);
            } else {
                item.setPercentage(0);
            }
        }

        // Setting sport-wise report list into report model
        report.setSportReportList(sportReportList);

        // Returning complete report data
        return report;
    }
}
