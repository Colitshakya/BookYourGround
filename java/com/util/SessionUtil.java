package com.util;

// Importing HttpServletRequest to access or create session from request
import jakarta.servlet.http.HttpServletRequest;

// Importing HttpSession to manage session data
import jakarta.servlet.http.HttpSession;

/**
 * SessionUtil is a utility class used to manage session data.
 *
 * This class provides reusable methods to:
 * - Set session attributes
 * - Get session attributes
 * - Remove session attributes
 * - Invalidate/destroy session
 */
public class SessionUtil {

    /**
     * Sets an attribute in the session.
     *
     * @param request HttpServletRequest object used to access session
     * @param name name of the session attribute
     * @param value value to store in the session
     * @param seconds session timeout duration in seconds
     */
    public static void setAttribute(HttpServletRequest request, String name, Object value, int seconds) {
        // Gets existing session or creates a new one if it does not exist
        HttpSession session = request.getSession(true);

        // Stores value in session using attribute name
        session.setAttribute(name, value);

        // Sets session timeout duration
        session.setMaxInactiveInterval(seconds);
    }

    /**
     * Gets an attribute value from the session.
     *
     * @param request HttpServletRequest object used to access session
     * @param name name of the session attribute
     * @return session attribute value if found, otherwise null
     */
    public static Object getAttribute(HttpServletRequest request, String name) {
        // Gets existing session without creating a new one
        HttpSession session = request.getSession(false);

        // Returns session attribute if session exists, otherwise returns null
        return (session != null) ? session.getAttribute(name) : null;
    }

    /**
     * Removes an attribute from the session.
     *
     * @param request HttpServletRequest object used to access session
     * @param name name of the session attribute to remove
     */
    public static void removeAttribute(HttpServletRequest request, String name) {
        // Gets existing session without creating a new one
        HttpSession session = request.getSession(false);

        // Removes selected attribute if session exists
        if (session != null) {
            session.removeAttribute(name);
        }
    }

    /**
     * Invalidates or destroys the current session.
     *
     * @param request HttpServletRequest object used to access session
     */
    public static void invalidateSession(HttpServletRequest request) {
        // Gets existing session without creating a new one
        HttpSession session = request.getSession(false);

        // Invalidates session if it exists
        if (session != null) {
            session.invalidate();
        }
    }
}
