package com.service;

import com.dao.BookingDAO;
import com.dao.CourtDAO;
import com.dao.UserDAO;
import com.model.AdminReportModel;
import com.model.SportBookingReportModel;

import java.util.List;

public class AdminReportService {

    public AdminReportModel getReportData() throws Exception {
        UserDAO userDAO = new UserDAO();
        CourtDAO courtDAO = new CourtDAO();
        BookingDAO bookingDAO = new BookingDAO();

        AdminReportModel report = new AdminReportModel();

        int totalUsers = userDAO.getTotalUsers();
        int totalCourts = courtDAO.getTotalCourts();
        int totalBookings = bookingDAO.getTotalBookings();
        int confirmed = bookingDAO.getBookingCountByStatus("confirmed");
        int pending = bookingDAO.getBookingCountByStatus("pending");
        int cancelled = bookingDAO.getBookingCountByStatus("cancelled");
        int activeCourts = courtDAO.getCourtCountByStatus("active");
        int inactiveCourts = courtDAO.getCourtCountByStatus("inactive");

        report.setTotalUsers(totalUsers);
        report.setTotalCourts(totalCourts);
        report.setTotalBookings(totalBookings);
        report.setConfirmedBookings(confirmed);
        report.setPendingBookings(pending);
        report.setCancelledBookings(cancelled);
        report.setActiveCourts(activeCourts);
        report.setInactiveCourts(inactiveCourts);

        if (totalBookings > 0) {
            report.setConfirmedPercent((confirmed * 100.0) / totalBookings);
            report.setPendingPercent((pending * 100.0) / totalBookings);
            report.setCancelledPercent((cancelled * 100.0) / totalBookings);
        } else {
            report.setConfirmedPercent(0);
            report.setPendingPercent(0);
            report.setCancelledPercent(0);
        }

        List<SportBookingReportModel> sportReportList = bookingDAO.getSportBookingReport();

        int maxCount = 0;
        for (SportBookingReportModel item : sportReportList) {
            if (item.getBookingCount() > maxCount) {
                maxCount = item.getBookingCount();
            }
        }

        for (SportBookingReportModel item : sportReportList) {
            if (maxCount > 0) {
                item.setPercentage((item.getBookingCount() * 100.0) / maxCount);
            } else {
                item.setPercentage(0);
            }
        }

        report.setSportReportList(sportReportList);

        return report;
    }
}