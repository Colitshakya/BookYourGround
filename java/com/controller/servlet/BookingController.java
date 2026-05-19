package com.controller.servlet;

import com.model.CourtModel;
import com.model.UserModel;
import com.service.CourtBookingService;
import com.service.PublicCourtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/bookCourt")
public class BookingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CourtBookingService bookingService = new CourtBookingService();
    private final PublicCourtService publicCourtService = new PublicCourtService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserModel loggedInUser = null;

        if (session != null) {
            loggedInUser = (UserModel) session.getAttribute("loggedInUser");
        }

        String courtIdParam = request.getParameter("courtId");
        String bookingDate = request.getParameter("bookingDate");
        String timeSlotIdParam = request.getParameter("timeSlotId");

        if (courtIdParam == null || courtIdParam.trim().isEmpty()) {
            courtIdParam = "1";
        }

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (bookingDate == null || bookingDate.trim().isEmpty()
                || timeSlotIdParam == null || timeSlotIdParam.trim().isEmpty()) {

            String errorMessage = URLEncoder.encode(
                    "Please select a date and time slot.",
                    StandardCharsets.UTF_8
            );

            response.sendRedirect(request.getContextPath()
                    + "/courtDetails?courtId=" + courtIdParam
                    + "&bookingDate=" + bookingDate
                    + "&bookingErrorMessage=" + errorMessage);
            return;
        }

        try {
            int timeSlotId = Integer.parseInt(timeSlotIdParam);
            int courtId = Integer.parseInt(courtIdParam);

            int bookingId = bookingService.bookSlot(loggedInUser.getUserId(), timeSlotId);

            CourtModel court = publicCourtService.getCourtById(courtId);

            if (bookingId <= 0) {
                throw new Exception("Booking was created but booking ID was not returned.");
            }

            if (court == null || court.getPricePerHour() == null) {
                throw new Exception("Court price could not be found.");
            }

            String amount = court.getPricePerHour().toPlainString();

            response.sendRedirect(request.getContextPath()
                    + "/payment?bookingId=" + bookingId
                    + "&amount=" + amount
                    + "&courtId=" + courtIdParam
                    + "&bookingDate=" + bookingDate);

        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(
                    e.getMessage(),
                    StandardCharsets.UTF_8
            );

            response.sendRedirect(request.getContextPath()
                    + "/courtDetails?courtId=" + courtIdParam
                    + "&bookingDate=" + bookingDate
                    + "&bookingErrorMessage=" + errorMessage);
        }
    }
}