package com.controller.servlet;

import com.model.CourtModel;
import com.model.TimeSlotModel;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/courtDetails")
public class CourtDetailsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CourtBookingService bookingService = new CourtBookingService();
    private final PublicCourtService publicCourtService = new PublicCourtService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int courtId = 1;
        String courtIdParam = request.getParameter("courtId");

        if (courtIdParam != null && !courtIdParam.trim().isEmpty()) {
            try {
                courtId = Integer.parseInt(courtIdParam);
            } catch (NumberFormatException e) {
                courtId = 1;
            }
        }

        try {
            CourtModel court = publicCourtService.getCourtById(courtId);

            if (court == null) {
                response.sendRedirect(request.getContextPath() + "/courts");
                return;
            }

            String selectedDate = request.getParameter("bookingDate");
            if (selectedDate == null || selectedDate.trim().isEmpty()) {
                selectedDate = LocalDate.now().toString();
            }

            List<TimeSlotModel> slots = bookingService.getSlotsByCourtAndDate(courtId, Date.valueOf(selectedDate));

            HttpSession session = request.getSession(false);
            UserModel loggedInUser = null;
            if (session != null) {
                loggedInUser = (UserModel) session.getAttribute("loggedInUser");
            }

            request.setAttribute("court", court);
            request.setAttribute("slotList", slots);
            request.setAttribute("courtId", courtId);
            request.setAttribute("selectedDate", selectedDate);
            request.setAttribute("isLoggedIn", loggedInUser != null);
            request.setAttribute("bookingSuccessMessage", request.getParameter("bookingSuccessMessage"));
            request.setAttribute("bookingErrorMessage", request.getParameter("bookingErrorMessage"));

            request.getRequestDispatcher("/pages/courtDetails.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("slotErrorMessage", "Unable to load court details.");
            request.getRequestDispatcher("/pages/courts.jsp").forward(request, response);
        }
    }
}