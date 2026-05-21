package com.controller.servlet;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

// Importing service class used to retrieve public court data
import com.service.PublicCourtService;

/**
 * Servlet implementation class CourtsController
 *
 * CourtsController handles requests for the public courts page.
 *
 * URL Mapping:
 * /courts
 *
 * This controller:
 * - Loads all active courts using PublicCourtService
 * - Sends the court list to courts.jsp
 * - Displays an error message if courts cannot be loaded
 */
@WebServlet("/courts")
public class CourtsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
	private static final long serialVersionUID = 1L;
       
    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     *
     * @see HttpServlet#HttpServlet()
     */
    public CourtsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Handles HTTP GET requests.
	 *
	 * This method is called when the user opens the /courts URL.
	 * It retrieves all active courts from the service layer and forwards
	 * the data to courts.jsp for display.
	 *
	 * @param request  stores request data sent by the browser
	 * @param response sends response data back to the browser
	 * @throws ServletException if servlet forwarding or processing fails
	 * @throws IOException if an input/output error occurs
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Creating service object to access public court-related operations
            PublicCourtService service = new PublicCourtService();

            // Retrieves all active courts and sends the court list to the JSP page
            request.setAttribute("courtList", service.getAllActiveCourts());
        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends an error message to the JSP page if courts cannot be loaded
            request.setAttribute("errorMessage", "Unable to load courts.");
        }

        // Forwards request and response to courts.jsp for displaying court data
        request.getRequestDispatcher("/pages/courts.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     *
     * Since the courts page mainly displays court data, POST requests are handled
     * the same way as GET requests by calling doGet().
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reuse doGet method so POST request also reloads the courts page
        doGet(request, response);
    }
}
