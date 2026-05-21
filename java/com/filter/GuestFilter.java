package com.filter;

// Importing SessionUtil to check whether a user is already logged in
import com.util.SessionUtil;

// Importing required Jakarta Filter and Servlet classes
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * GuestFilter controls access to guest pages such as login and register.
 *
 * URL Patterns:
 * - /login
 * - /register
 *
 * This filter:
 * - Checks whether a user is already logged in
 * - Redirects logged-in users to home page
 * - Allows non-logged-in users to access login and register pages
 */
@WebFilter(urlPatterns = {"/login", "/register"})
public class GuestFilter extends HttpFilter {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Filters requests before they reach guest-only pages.
     *
     * This method prevents logged-in users from opening login or register pages again.
     * If the user is already logged in, they are redirected to home.
     * If the user is not logged in, the request continues normally.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @param chain    allows the request to continue if validation passes
     * @throws IOException if an input/output error occurs
     * @throws ServletException if filter processing fails
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Converts ServletRequest into HttpServletRequest to access HTTP-specific methods
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Converts ServletResponse into HttpServletResponse to use redirect method
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Checks whether loggedInUser exists in session
        boolean isLoggedIn = SessionUtil.getAttribute(httpRequest, "loggedInUser") != null;

        // If user is already logged in, redirect to home page
        if (isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");

        // If user is not logged in, allow access to login or register page
        } else {
            chain.doFilter(request, response);
        }
    }
}
