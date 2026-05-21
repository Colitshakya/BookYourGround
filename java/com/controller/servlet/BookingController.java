package com.controller.servlet;

// Importing CourtModel to access court details such as price per hour
import com.model.CourtModel;

// Importing UserModel to get the logged-in user's information from session
import com.model.UserModel;

// Importing service class that handles booking-related business logic
import com.service.CourtBookingService;

// Importing service class that retrieves public court details
import com.service.PublicCourtService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Importing IOException for input/output errors
import java.io.IOException;

// Importing URLEncoder to safely send error messages through URL parameters
import java.net.URLEncoder;

// Importing StandardCharsets to encode URL messages using UTF-8
import java.nio.charset.StandardCharsets;

/**
 * BookingController handles court booking requests from users.
 *
 * URL Mapping:
 * /bookCourt
 *
 * This controller:
 * - Checks whether the user is logged in
 * - Reads selected court, booking date, and time slot from the request
 * - Creates a booking using CourtBookingService
 * - Retrieves court price using PublicCourtService
 * - Redirects the user to the payment page after successful booking
 */
@WebServlet("/bookCourt")
public class BookingController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Service object used to handle booking creation logic
    private final CourtBookingService bookingService = new CourtBookingService();

    // Service object used to retrieve public court details
    private final PublicCourtService publicCourtService = new PublicCourtService();

    /**
     * Handles HTTP POST requests.
     *
     * This method is called when a logged-in user submits the booking form.
     * It validates login/session information, checks selected date and time slot,
     * creates the booking, and redirects the user to the payment page.
     *
     * @param request  stores form data submitted by the user
     * @param response sends redirect response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Gets the current session without creating a new one
        HttpSession session = request.getSession(false);

        // Variable to store the logged-in user object
        UserModel loggedInUser = null;

        // Checks whether a session exists before trying to get user data
        if (session != null) {
            // Gets the logged-in user from the session
            loggedInUser = (UserModel) session.getAttribute("loggedInUser");
        }

        // Gets selected court ID from the submitted booking form
        String courtIdParam = request.getParameter("courtId");

        // Gets selected booking date from the submitted booking form
        String bookingDate = request.getParameter("bookingDate");

        // Gets selected time slot ID from the submitted booking form
        String timeSlotIdParam = request.getParameter("timeSlotId");

        // If court ID is missing, default court ID is set to 1
        if (courtIdParam == null || courtIdParam.trim().isEmpty()) {
            courtIdParam = "1";
        }

        // If user is not logged in, redirect user to login page
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Checks whether booking date or time slot is missing
        if (bookingDate == null || bookingDate.trim().isEmpty()
                || timeSlotIdParam == null || timeSlotIdParam.trim().isEmpty()) {

            // Encodes the error message so it can be safely passed in the URL
            String errorMessage = URLEncoder.encode(
                    "Please select a date and time slot.",
                    StandardCharsets.UTF_8
            );

            // Redirects back to court details page with error message
            response.sendRedirect(request.getContextPath()
                    + "/courtDetails?courtId=" + courtIdParam
                    + "&bookingDate=" + bookingDate
                    + "&bookingErrorMessage=" + errorMessage);
            return;
        }

        try {
            // Converts time slot ID from String to integer
            int timeSlotId = Integer.parseInt(timeSlotIdParam);

            // Converts court ID from String to integer
            int courtId = Integer.parseInt(courtIdParam);

            // Creates booking for the logged-in user and selected time slot
            int bookingId = bookingService.bookSlot(loggedInUser.getUserId(), timeSlotId);

            // Retrieves selected court details to get court price
            CourtModel court = publicCourtService.getCourtById(courtId);

            // Checks whether booking ID was returned correctly
            if (bookingId <= 0) {
                throw new Exception("Booking was created but booking ID was not returned.");
            }

            // Checks whether court information and court price are available
            if (court == null || court.getPricePerHour() == null) {
                throw new Exception("Court price could not be found.");
            }

            // Converts court price to String so it can be passed to payment page
            String amount = court.getPricePerHour().toPlainString();

            // Redirects user to payment page with booking and payment details
            response.sendRedirect(request.getContextPath()
                    + "/payment?bookingId=" + bookingId
                    + "&amount=" + amount
                    + "&courtId=" + courtIdParam
                    + "&bookingDate=" + bookingDate);

        } catch (Exception e) {
            // Encodes exception message so it can be safely passed in the URL
            String errorMessage = URLEncoder.encode(
                    e.getMessage(),
                    StandardCharsets.UTF_8
            );

            // Redirects back to court details page with booking error message
            response.sendRedirect(request.getContextPath()
                    + "/courtDetails?courtId=" + courtIdParam
                    + "&bookingDate=" + bookingDate
                    + "&bookingErrorMessage=" + errorMessage);
        }
    }
}
