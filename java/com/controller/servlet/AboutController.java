package com.controller.servlet;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output related errors
import java.io.IOException;

/**
 * AboutController is responsible for handling requests for the About page.
 * 
 * URL Mapping:
 * /about
 * 
 * When the user visits the /about URL, this servlet forwards the request
 * to about.jsp, which displays information about the Book Your Ground system.
 */
@WebServlet("/about")
public class AboutController extends HttpServlet {

    // Used during serialization to verify that sender and receiver classes are compatible
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AboutController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     * 
     * This method is called when the user opens the About page using the /about URL.
     * It forwards the request to the about.jsp page located inside the pages folder.
     * 
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object used to send response back to client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forward the request and response to the About JSP page
        request.getRequestDispatcher("/pages/about.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * 
     * Since the About page does not process form data, the POST request is handled
     * the same way as the GET request by calling doGet().
     * 
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object used to send response back to client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reuse doGet method so both GET and POST requests show the About page
        doGet(request, response);
    }
}
