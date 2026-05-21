package com.controller.servlet;

// Importing service class that handles admin time slot-related business logic
import com.service.AdminTimeSlotService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * AdminTimeSlotsController handles admin-side time slot management.
 *
 * URL Mapping:
 * /adminTimeSlots
 *
 * This controller allows the admin to:
 * - View all time slots
 * - Activate time slots by setting status to available
 * - Deactivate time slots by setting status to inactive
 */
@WebServlet("/adminTimeSlots")
public class AdminTimeSlotsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AdminTimeSlotsController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method loads all time slot records from the database through
     * AdminTimeSlotService and forwards the data to adminTimeSlots.jsp.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Creating service object to access time slot-related admin operations
            AdminTimeSlotService service = new AdminTimeSlotService();

            // Sets the active sidebar/page indicator as timeslots in the admin dashboard
            request.setAttribute("activePage", "timeslots");

            // Gets all time slots from the service layer and sends them to the JSP page
            request.setAttribute("timeSlotList", service.getAllTimeSlots());

            // Forwards request and response to adminTimeSlots.jsp for displaying time slot data
            request.getRequestDispatcher("/pages/adminTimeSlots.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps timeslots page active even if time slot data fails to load
            request.setAttribute("activePage", "timeslots");

            // Sends an error message to the JSP page
            request.setAttribute("errorMessage", "Unable to load time slots.");

            // Forwards back to adminTimeSlots.jsp with the error message
            request.getRequestDispatcher("/pages/adminTimeSlots.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method processes time slot status update actions submitted
     * from the admin time slots page.
     *
     * @param request  stores form data submitted from the admin time slots page
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gets the action value from the submitted form, such as activate or deactivate
            String action = request.getParameter("action");

            // Gets the selected time slot ID from the submitted form and converts it into integer
            int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));

            // Creating service object to update time slot status
            AdminTimeSlotService service = new AdminTimeSlotService();

            // If admin clicks activate, the selected time slot status is updated to available
            if ("activate".equals(action)) {
                service.updateTimeSlotStatus(timeSlotId, "available");

            // If admin clicks deactivate, the selected time slot status is updated to inactive
            } else if ("deactivate".equals(action)) {
                service.updateTimeSlotStatus(timeSlotId, "inactive");
            }

            // Redirects back to adminTimeSlots page after status update
            response.sendRedirect(request.getContextPath() + "/adminTimeSlots");

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps timeslots page active in the admin dashboard
            request.setAttribute("activePage", "timeslots");

            // Sends an error message if time slot status update fails
            request.setAttribute("errorMessage", "Unable to update time slot status.");

            // Reloads time slot page by calling doGet method
            doGet(request, response);
        }
    }
}
