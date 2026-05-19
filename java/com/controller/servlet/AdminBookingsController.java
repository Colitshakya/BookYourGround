package com.controller.servlet;

import com.service.AdminBookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/adminBookings")
public class AdminBookingsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminBookingsController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdminBookingService service = new AdminBookingService();

            request.setAttribute("activePage", "bookings");
            request.setAttribute("bookingList", service.getAllBookings());

            request.getRequestDispatcher("/pages/adminBookings.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "bookings");
            request.setAttribute("errorMessage", "Unable to load bookings.");
            request.getRequestDispatcher("/pages/adminBookings.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));

            AdminBookingService service = new AdminBookingService();

            if ("confirm".equals(action)) {
                service.updateBookingStatus(bookingId, "confirmed");
            } else if ("cancel".equals(action)) {
                service.updateBookingStatus(bookingId, "cancelled");
            }

            response.sendRedirect(request.getContextPath() + "/adminBookings");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "bookings");
            request.setAttribute("errorMessage", "Unable to update booking status.");
            doGet(request, response);
        }
    }
}