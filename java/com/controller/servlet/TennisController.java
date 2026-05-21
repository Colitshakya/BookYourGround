package com.controller.servlet;

// Importing service class used to retrieve public court data
import com.service.PublicCourtService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * TennisController handles requests for the Tennis courts page.
 *
 * URL Mapping:
 * /tennis
 *
 * This controller:
 * - Loads only Tennis courts using PublicCourtService
 * - Sends the court list to tennis.jsp
 * - Displays an error message if tennis courts cannot be loaded
 */
@WebServlet("/tennis")
public class TennisController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public TennisController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /tennis URL.
     * It retrieves all courts related to Tennis from the service layer
     * and forwards the court list to tennis.jsp for display.
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
            // Creating service object to access public court-related operations
            PublicCourtService service = new PublicCourtService();

            // Retrieves tennis courts and sends the court list to the JSP page
            request.setAttribute("courtList", service.getCourtsBySport("Tennis"));
        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends an error message to the JSP page if tennis courts cannot be loaded
            request.setAttribute("errorMessage", "Unable to load tennis courts.");
        }

        // Sets the page title that can be displayed in the JSP page
        request.setAttribute("pageTitle", "Tennis Courts");

        // Forwards request and response to tennis.jsp for displaying tennis courts
        request.getRequestDispatcher("/pages/tennis.jsp").forward(request, response);
    }
}
