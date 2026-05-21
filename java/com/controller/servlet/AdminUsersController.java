package com.controller.servlet;

// Importing service class that handles admin user-related business logic
import com.service.AdminUserService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * AdminUsersController handles admin-side user management.
 *
 * URL Mapping:
 * /adminUsers
 *
 * This controller allows the admin to:
 * - View all registered users
 * - Approve pending users
 * - Deactivate users
 * - Reactivate users
 */
@WebServlet("/adminUsers")
public class AdminUsersController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AdminUsersController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method loads all user records from the database through
     * AdminUserService and forwards the data to adminUsers.jsp.
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
            // Creating service object to access user-related admin operations
            AdminUserService service = new AdminUserService();

            // Sets the active sidebar/page indicator as users in the admin dashboard
            request.setAttribute("activePage", "users");

            // Gets all users from the service layer and sends them to the JSP page
            request.setAttribute("userList", service.getAllUsers());

            // Forwards request and response to adminUsers.jsp for displaying user data
            request.getRequestDispatcher("/pages/adminUsers.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps users page active even if user data fails to load
            request.setAttribute("activePage", "users");

            // Sends an error message to the JSP page
            request.setAttribute("errorMessage", "Unable to load users.");

            // Forwards back to adminUsers.jsp with the error message
            request.getRequestDispatcher("/pages/adminUsers.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method processes user status update actions submitted
     * from the admin users page.
     *
     * @param request  stores form data submitted from the admin users page
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gets the action value from the submitted form, such as approve, deactivate, or reactivate
            String action = request.getParameter("action");

            // Gets the selected user ID from the submitted form and converts it into integer
            int userId = Integer.parseInt(request.getParameter("userId"));

            // Creating service object to update user status
            AdminUserService service = new AdminUserService();

            // If admin clicks approve, the selected user's status is updated to active
            if ("approve".equals(action)) {
                service.updateUserStatus(userId, "active");

            // If admin clicks deactivate, the selected user's status is updated to inactive
            } else if ("deactivate".equals(action)) {
                service.updateUserStatus(userId, "inactive");

            // If admin clicks reactivate, the selected user's status is updated back to active
            } else if ("reactivate".equals(action)) {
                service.updateUserStatus(userId, "active");
            }

            // Redirects back to adminUsers page after status update
            response.sendRedirect(request.getContextPath() + "/adminUsers");

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps users page active in the admin dashboard
            request.setAttribute("activePage", "users");

            // Sends an error message if user status update fails
            request.setAttribute("errorMessage", "Unable to update user status.");

            // Reloads user page by calling doGet method
            doGet(request, response);
        }
    }
}
