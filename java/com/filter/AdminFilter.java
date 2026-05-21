package com.filter;

// Importing UserModel to access logged-in user details and role
import com.model.UserModel;

// Importing SessionUtil to retrieve logged-in user data from the session
import com.util.SessionUtil;

// Importing required Jakarta Filter and Servlet classes
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * AdminFilter is used to protect admin-only pages.
 *
 * This filter checks:
 * - Whether the user is logged in
 * - Whether the logged-in user has the admin role
 *
 * If the user is not logged in, they are redirected to the login page.
 * If the user is logged in but not an admin, they are redirected to the home page.
 */
@WebFilter(urlPatterns = {
        "/adminDashboard",
        "/adminUsers",
        "/adminCourts",
        "/adminBookings",
        "/adminTimeSlots",
        "/adminReports"
})
public class AdminFilter extends HttpFilter implements Filter {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Filters requests before they reach admin controllers/pages.
     *
     * This method checks the logged-in user from the session.
     * Only users with the role "admin" are allowed to continue to admin pages.
     *
     * @param request  stores request data sent by the browser
     * @param response sends redirect or response back to the browser
     * @param chain    allows the request to continue if validation passes
     * @throws IOException if an input/output error occurs
     * @throws ServletException if filter processing fails
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Gets logged-in user object from the session
        UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

        // If user is not logged in, redirect to login page
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // If logged-in user is not an admin, redirect to normal user home page
        if (!"admin".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // If user is logged in and has admin role, allow request to continue
        chain.doFilter(request, response);
    }
}
