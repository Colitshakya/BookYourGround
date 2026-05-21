package com.controller.servlet;

// Importing CookieUtil to delete stored cookie information during logout
import com.util.CookieUtil;

// Importing SessionUtil to destroy the current user session during logout
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
 * LogoutController handles user and admin logout requests.
 *
 * URL Mapping:
 * /logout
 *
 * This controller:
 * - Invalidates the current session
 * - Deletes the last_login cookie
 * - Redirects the user back to the login page
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public LogoutController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user clicks a logout link or visits /logout.
     * It destroys the current session, removes the last_login cookie,
     * and redirects the user to the login page.
     *
     * @param request  stores request data sent by the browser
     * @param response sends redirect response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidates/destroys the current user session
        SessionUtil.invalidateSession(request);

        // Deletes the last_login cookie from the browser
        CookieUtil.deleteCookie(response, "last_login");

        // Redirects the user to the login page after logout
        response.sendRedirect(request.getContextPath() + "/login");
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method performs the same logout process as doGet().
     * It is useful when logout is submitted through a form.
     *
     * @param request  stores request data sent by the browser
     * @param response sends redirect response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidates/destroys the current user session
        SessionUtil.invalidateSession(request);

        // Deletes the last_login cookie from the browser
        CookieUtil.deleteCookie(response, "last_login");

        // Redirects the user to the login page after logout
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
