package com.controller.servlet;

// Importing AdminModel to store and transfer dashboard summary data
import com.model.AdminModel;

// Importing service class that contains admin dashboard business logic
import com.service.AdminDashboardService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * AdminDashboardController handles requests for the admin dashboard page.
 *
 * URL Mapping:
 * /adminDashboard
 *
 * This controller loads dashboard summary data such as total users,
 * total courts, total bookings, pending users, and recent bookings,
 * then forwards the data to adminDashboard.jsp.
 */
@WebServlet("/adminDashboard")
public class AdminDashboardController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AdminDashboardController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the admin opens the dashboard page.
     * It gets dashboard data from AdminDashboardService and sends it
     * to adminDashboard.jsp using request attributes.
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
            // Creating service object to access dashboard-related operations
            AdminDashboardService service = new AdminDashboardService();

            // Getting dashboard summary data from the service layer
            AdminModel dashboard = service.getDashboardData();

            // Sets the active sidebar/page indicator as dashboard
            request.setAttribute("activePage", "dashboard");

            // Sends dashboard data to the JSP page
            request.setAttribute("dashboard", dashboard);

            // Forwards request and response to adminDashboard.jsp for display
            request.getRequestDispatcher("/pages/adminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps dashboard page active even if data loading fails
            request.setAttribute("activePage", "dashboard");

            // Sends an error message to the JSP page
            request.setAttribute("errorMessage", "Unable to load admin dashboard.");

            // Forwards back to adminDashboard.jsp with the error message
            request.getRequestDispatcher("/pages/adminDashboard.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * Since this dashboard page mainly displays data, POST requests are handled
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

        // Reuse doGet method so POST request also reloads the dashboard page
        doGet(request, response);
    }
}
