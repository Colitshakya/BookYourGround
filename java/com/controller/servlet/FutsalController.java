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
 * FutsalController handles requests for the Futsal courts page.
 *
 * URL Mapping:
 * /futsal
 *
 * This controller:
 * - Loads only Futsal courts using PublicCourtService
 * - Sends the court list to futsal.jsp
 * - Displays an error message if futsal courts cannot be loaded
 */
@WebServlet("/futsal")
public class FutsalController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public FutsalController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /futsal URL.
     * It retrieves all courts related to Futsal from the service layer
     * and forwards the court list to futsal.jsp for display.
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

            // Retrieves futsal courts and sends the court list to the JSP page
            request.setAttribute("courtList", service.getCourtsBySport("Futsal"));
        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends an error message to the JSP page if futsal courts cannot be loaded
            request.setAttribute("errorMessage", "Unable to load futsal courts.");
        }

        // Sets the page title that can be displayed in the JSP page
        request.setAttribute("pageTitle", "Futsal Courts");

        // Forwards request and response to futsal.jsp for displaying futsal courts
        request.getRequestDispatcher("/pages/futsal.jsp").forward(request, response);
    }
}
