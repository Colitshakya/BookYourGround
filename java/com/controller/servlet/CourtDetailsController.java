package com.controller.servlet;

// Importing CourtModel to store and display selected court details
import com.model.CourtModel;

// Importing TimeSlotModel to store and display time slot information
import com.model.TimeSlotModel;

// Importing UserModel to check logged-in user information from session
import com.model.UserModel;

// Importing service class that handles booking and time slot-related logic
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

// Importing IOException to handle input/output errors
import java.io.IOException;

// Importing SQL Date to pass selected booking date to the service layer
import java.sql.Date;

// Importing LocalDate to set today's date as default booking date
import java.time.LocalDate;

// Importing List to store multiple time slot records
import java.util.List;

/**
 * CourtDetailsController handles requests for the court details page.
 *
 * URL Mapping:
 * /courtDetails
 *
 * This controller:
 * - Gets selected court ID from the request
 * - Loads court details using PublicCourtService
 * - Loads time slots for the selected court and date
 * - Checks whether the user is logged in
 * - Sends court, slot, login, success, and error data to courtDetails.jsp
 */
@WebServlet("/courtDetails")
public class CourtDetailsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Service object used to retrieve booking-related time slot data
    private final CourtBookingService bookingService = new CourtBookingService();

    // Service object used to retrieve public court information
    private final PublicCourtService publicCourtService = new PublicCourtService();

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when a user opens the court details page.
     * It loads the selected court, selected date, available/booked time slots,
     * and login status, then forwards everything to courtDetails.jsp.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Default court ID is set to 1 if no valid court ID is provided
        int courtId = 1;

        // Gets courtId value from the URL query parameter
        String courtIdParam = request.getParameter("courtId");

        // Checks whether courtId parameter exists and is not empty
        if (courtIdParam != null && !courtIdParam.trim().isEmpty()) {
            try {
                // Converts courtId from String to integer
                courtId = Integer.parseInt(courtIdParam);
            } catch (NumberFormatException e) {
                // If courtId is invalid, default court ID remains 1
                courtId = 1;
            }
        }

        try {
            // Retrieves court details by court ID
            CourtModel court = publicCourtService.getCourtById(courtId);

            // If court does not exist, redirect user back to courts page
            if (court == null) {
                response.sendRedirect(request.getContextPath() + "/courts");
                return;
            }

            // Gets selected booking date from the request
            String selectedDate = request.getParameter("bookingDate");

            // If no date is selected, today's date is used as default
            if (selectedDate == null || selectedDate.trim().isEmpty()) {
                selectedDate = LocalDate.now().toString();
            }

            // Retrieves time slots for the selected court and selected date
            List<TimeSlotModel> slots = bookingService.getSlotsByCourtAndDate(courtId, Date.valueOf(selectedDate));

            // Gets current session without creating a new one
            HttpSession session = request.getSession(false);

            // Variable to store logged-in user data
            UserModel loggedInUser = null;

            // If session exists, get logged-in user from session
            if (session != null) {
                loggedInUser = (UserModel) session.getAttribute("loggedInUser");
            }

            // Sends selected court details to JSP
            request.setAttribute("court", court);

            // Sends time slot list to JSP
            request.setAttribute("slotList", slots);

            // Sends selected court ID to JSP
            request.setAttribute("courtId", courtId);

            // Sends selected booking date to JSP
            request.setAttribute("selectedDate", selectedDate);

            // Sends login status to JSP, true if user is logged in
            request.setAttribute("isLoggedIn", loggedInUser != null);

            // Sends booking success message from URL parameter to JSP
            request.setAttribute("bookingSuccessMessage", request.getParameter("bookingSuccessMessage"));

            // Sends booking error message from URL parameter to JSP
            request.setAttribute("bookingErrorMessage", request.getParameter("bookingErrorMessage"));

            // Forwards request and response to courtDetails.jsp for display
            request.getRequestDispatcher("/pages/courtDetails.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends error message if court details or time slots cannot be loaded
            request.setAttribute("slotErrorMessage", "Unable to load court details.");

            // Forwards user back to courts.jsp if an error occurs
            request.getRequestDispatcher("/pages/courts.jsp").forward(request, response);
        }
    }
}
