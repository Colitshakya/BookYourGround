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
 * PickleballController handles requests for the Pickleball courts page.
 *
 * URL Mapping:
 * /pickleball
 *
 * This controller:
 * - Loads only Pickleball courts using PublicCourtService
 * - Sends the court list to pickleball.jsp
 * - Displays an error message if pickleball courts cannot be loaded
 */
@WebServlet("/pickleball")
public class PickleballController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public PickleballController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /pickleball URL.
     * It retrieves all courts related to Pickleball from the service layer
     * and forwards the court list to pickleball.jsp for display.
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

            // Retrieves pickleball courts and sends the court list to the JSP page
            request.setAttribute("courtList", service.getCourtsBySport("Pickleball"));
        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends an error message to the JSP page if pickleball courts cannot be loaded
            request.setAttribute("errorMessage", "Unable to load pickleball courts.");
        }

        // Sets the page title that can be displayed in the JSP page
        request.setAttribute("pageTitle", "Pickleball Courts");

        // Forwards request and response to pickleball.jsp for displaying pickleball courts
        request.getRequestDispatcher("/pages/pickleball.jsp").forward(request, response);
    }
}
