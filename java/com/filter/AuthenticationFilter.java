package com.filter;

// Importing SessionUtil to check whether a user is logged in through session data
import com.util.SessionUtil;

// Importing required Jakarta Filter and Servlet classes
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
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
 * AuthenticationFilter protects pages that should only be accessed by logged-in users.
 *
 * URL Patterns:
 * - /userProfile
 * - /bookCourt
 * - /payment
 * - /logout
 * - /admin-dashboard
 *
 * This filter:
 * - Checks whether loggedInUser exists in the session
 * - Allows access if the user is logged in
 * - Redirects to login page if the user is not logged in
 */
@WebFilter(urlPatterns = {
	    "/userProfile",
	    "/bookCourt",
	    "/payment",
	    "/logout",
	    "/admin-dashboard"
	})
public class AuthenticationFilter extends HttpFilter implements Filter {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpFilter constructor.
     */
    public AuthenticationFilter() {
        super();
    }

    /**
     * Initializes the filter.
     *
     * This method runs when the filter is first created by the servlet container.
     *
     * @param filterConfig contains filter configuration information
     * @throws ServletException if filter initialization fails
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Destroys the filter.
     *
     * This method runs when the filter is removed from service.
     */
    @Override
    public void destroy() {
    }

    /**
     * Filters requests before they reach protected pages.
     *
     * This method checks whether the user is logged in.
     * If logged in, the request continues to the requested page.
     * If not logged in, the user is redirected to the login page.
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

        // Converts ServletResponse into HttpServletResponse to use redirect and header methods
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Checks whether loggedInUser exists in session
        boolean isLoggedIn = SessionUtil.getAttribute(httpRequest, "loggedInUser") != null;

        // If user is logged in, allow the request to continue
        if (isLoggedIn) {
            chain.doFilter(request, response);

        // If user is not logged in, redirect to login page
        } else {
            // Prevents browser from caching protected pages after logout
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

            // Redirects unauthenticated user to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}
