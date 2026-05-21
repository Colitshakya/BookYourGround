package com.controller.servlet;

// Importing the service class that handles admin booking-related business logic
import com.service.AdminBookingService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * AdminBookingsController handles admin-side booking management.
 * 
 * URL Mapping:
 * /adminBookings
 * 
 * This controller allows the admin to:
 * - View all bookings
 * - Confirm bookings
 * - Cancel bookings
 */
@WebServlet("/adminBookings")
public class AdminBookingsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AdminBookingsController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     * 
     * This method loads all booking records from the database through
     * AdminBookingService and forwards them to the adminBookings.jsp page.
     * 
     * @param request  stores request data sent by the client/browser
     * @param response sends response data back to the client/browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Creating service object to access booking-related admin operations
            AdminBookingService service = new AdminBookingService();

            // Sets the active sidebar/page indicator as bookings in the admin dashboard
            request.setAttribute("activePage", "bookings");

            // Gets all bookings from the service layer and sends them to the JSP page
            request.setAttribute("bookingList", service.getAllBookings());

            // Forwards request and response to adminBookings.jsp for displaying booking data
            request.getRequestDispatcher("/pages/adminBookings.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps bookings page active even if loading data fails
            request.setAttribute("activePage", "bookings");

            // Sends an error message to the JSP page
            request.setAttribute("errorMessage", "Unable to load bookings.");

            // Forwards back to adminBookings.jsp with the error message
            request.getRequestDispatcher("/pages/adminBookings.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     * 
     * This method is used when the admin performs an action on a booking,
     * such as confirming or cancelling a booking.
     * 
     * @param request  stores form data submitted from the admin bookings page
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gets the action value from the submitted form, such as confirm or cancel
            String action = request.getParameter("action");

            // Gets the booking ID from the submitted form and converts it into integer
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));

            // Creating service object to update booking status
            AdminBookingService service = new AdminBookingService();

            // If admin clicks confirm, booking status is updated to confirmed
            if ("confirm".equals(action)) {
                service.updateBookingStatus(bookingId, "confirmed");

            // If admin clicks cancel, booking status is updated to cancelled
            } else if ("cancel".equals(action)) {
                service.updateBookingStatus(bookingId, "cancelled");
            }

            // Redirects back to adminBookings page after status update
            response.sendRedirect(request.getContextPath() + "/adminBookings");

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps bookings page active in the admin dashboard
            request.setAttribute("activePage", "bookings");

            // Sends an error message if booking status update fails
            request.setAttribute("errorMessage", "Unable to update booking status.");

            // Reloads booking page by calling doGet method
            doGet(request, response);
        }
    }
}
