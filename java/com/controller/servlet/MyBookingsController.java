package com.controller.servlet;

// Importing UserModel to access logged-in user details from the session
import com.model.UserModel;

// Importing service class that handles user profile and booking history logic
import com.service.UserProfileService;

// Importing SessionUtil to retrieve logged-in user data from the session
import com.util.SessionUtil;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * MyBookingsController handles requests for the user's booking history page.
 *
 * URL Mapping:
 * /myBookings
 *
 * This controller:
 * - Checks whether the user is logged in
 * - Retrieves the logged-in user's booking history
 * - Sends the booking list to myBookings.jsp
 */
@WebServlet("/myBookings")
public class MyBookingsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public MyBookingsController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /myBookings URL.
     * It first checks whether a user is stored in the session. If no user is
     * logged in, the user is redirected to the login page. If logged in,
     * it loads the user's booking history and forwards it to myBookings.jsp.
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
            // Retrieves the logged-in user object from the session
            UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

            // If no user is logged in, redirect to login page
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Creating service object to retrieve user booking history
            UserProfileService service = new UserProfileService();

            // Gets booking history for the logged-in user and sends it to JSP
            request.setAttribute("bookingHistoryList", service.getUserBookings(loggedInUser.getUserId()));

            // Forwards request and response to myBookings.jsp for displaying booking history
            request.getRequestDispatcher("/pages/myBookings.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends error message to JSP if bookings cannot be loaded
            request.setAttribute("errorMessage", "Unable to load bookings.");

            // Forwards back to myBookings.jsp with the error message
            request.getRequestDispatcher("/pages/myBookings.jsp").forward(request, response);
        }
    }
}
