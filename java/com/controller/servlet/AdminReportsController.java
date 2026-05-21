package com.controller.servlet;

// Importing AdminReportModel to store and transfer report data to the JSP page
import com.model.AdminReportModel;

// Importing service class that contains admin report-related business logic
import com.service.AdminReportService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * AdminReportsController handles requests for the admin reports page.
 *
 * URL Mapping:
 * /adminReports
 *
 * This controller loads report data such as booking reports, court status reports,
 * user counts, booking status counts, and sport booking analysis, then forwards
 * the data to adminReports.jsp.
 */
@WebServlet("/adminReports")
public class AdminReportsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AdminReportsController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the admin opens the reports page.
     * It gets report data from AdminReportService and stores it in the request
     * so that adminReports.jsp can display the report information.
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
            // Creating service object to access admin report-related operations
            AdminReportService service = new AdminReportService();

            // Getting complete report data from the service layer
            AdminReportModel report = service.getReportData();

            // Sets the active sidebar/page indicator as reports in the admin dashboard
            request.setAttribute("activePage", "reports");

            // Sends report data to the JSP page
            request.setAttribute("report", report);

            // Forwards request and response to adminReports.jsp for displaying report data
            request.getRequestDispatcher("/pages/adminReports.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps reports page active even if report data fails to load
            request.setAttribute("activePage", "reports");

            // Sends an error message to the JSP page
            request.setAttribute("errorMessage", "Unable to load reports.");

            // Forwards back to adminReports.jsp with the error message
            request.getRequestDispatcher("/pages/adminReports.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * Since the reports page mainly displays generated report data, POST requests
     * are handled the same way as GET requests by calling doGet().
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reuse doGet method so POST request also reloads the reports page
        doGet(request, response);
    }
}
