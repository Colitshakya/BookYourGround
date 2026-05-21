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
 * BasketballController handles requests for the Basketball courts page.
 *
 * URL Mapping:
 * /basketball
 *
 * This controller loads only Basketball courts from the database
 * and forwards the data to basketball.jsp for display.
 */
@WebServlet("/basketball")
public class BasketballController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public BasketballController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /basketball URL.
     * It uses PublicCourtService to retrieve all courts related to Basketball
     * and sends the court list to the JSP page using request attributes.
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

            // Retrieves basketball courts and sends the court list to the JSP page
            request.setAttribute("courtList", service.getCourtsBySport("Basketball"));
        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends an error message to the JSP page if basketball courts cannot be loaded
            request.setAttribute("errorMessage", "Unable to load basketball courts.");
        }

        // Sets the page title that can be displayed in the JSP page
        request.setAttribute("pageTitle", "Basketball Courts");

        // Forwards request and response to basketball.jsp for displaying basketball courts
        request.getRequestDispatcher("/pages/basketball.jsp").forward(request, response);
    }
}
