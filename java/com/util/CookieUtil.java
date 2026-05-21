package com.util;

// Importing Cookie class to create, read, and delete cookies
import jakarta.servlet.http.Cookie;

// Importing HttpServletRequest to read cookies from the browser request
import jakarta.servlet.http.HttpServletRequest;

// Importing HttpServletResponse to send cookies back to the browser
import jakarta.servlet.http.HttpServletResponse;

/**
 * CookieUtil is a utility class used to manage cookies in the application.
 *
 * This class provides reusable methods to:
 * - Add a cookie
 * - Get a cookie value
 * - Delete a cookie
 */
public class CookieUtil {

    /**
     * Adds a new cookie to the browser.
     *
     * @param response HttpServletResponse object used to send cookie to browser
     * @param name name of the cookie
     * @param value value stored inside the cookie
     * @param seconds cookie expiry time in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int seconds) {
        // Creating a new cookie with name and value
        Cookie cookie = new Cookie(name, value);

        // Setting cookie path so it is available throughout the application
        cookie.setPath("/");

        // Setting cookie expiry time in seconds
        cookie.setMaxAge(seconds);

        // Making cookie HttpOnly to prevent access from client-side JavaScript
        cookie.setHttpOnly(true);

        // Adding cookie to the response so browser can store it
        response.addCookie(cookie);
    }

    /**
     * Gets the value of a cookie by its name.
     *
     * @param request HttpServletRequest object containing cookies from browser
     * @param name name of the cookie to search
     * @return cookie value if found, otherwise null
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        // Gets all cookies from the request
        Cookie[] cookies = request.getCookies();

        // Checks whether cookies exist
        if (cookies != null) {
            // Loops through each cookie
            for (Cookie cookie : cookies) {
                // Checks if current cookie name matches the required name
                if (name.equals(cookie.getName())) {
                    // Returns the value of the matched cookie
                    return cookie.getValue();
                }
            }
        }

        // Returns null if cookie is not found
        return null;
    }

    /**
     * Deletes a cookie from the browser.
     *
     * @param response HttpServletResponse object used to update cookie in browser
     * @param name name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        // Creating a cookie with the same name and empty value
        Cookie cookie = new Cookie(name, "");

        // Setting same path so the correct cookie is targeted
        cookie.setPath("/");

        // Setting max age to 0 deletes the cookie immediately
        cookie.setMaxAge(0);

        // Adding cookie to response so browser removes it
        response.addCookie(cookie);
    }
}
