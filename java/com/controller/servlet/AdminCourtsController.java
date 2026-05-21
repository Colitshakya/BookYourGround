package com.controller.servlet;

// Importing CourtModel to store court form data before sending it to the service layer
import com.model.CourtModel;

// Importing service class that contains admin court-related business logic
import com.service.AdminCourtService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// Importing Java utility classes used for file handling, number handling, and unique file naming
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * AdminCourtsController handles all admin-side court management actions.
 *
 * URL Mapping:
 * /adminCourts
 *
 * This controller allows the admin to:
 * - View all courts
 * - Activate a court
 * - Deactivate a court
 * - Add a new court with image upload
 */
@WebServlet("/adminCourts")

/**
 * MultipartConfig is used because this servlet handles image/file upload.
 *
 * fileSizeThreshold:
 * Files larger than this size will be temporarily stored on disk.
 *
 * maxFileSize:
 * Maximum size allowed for a single uploaded file.
 *
 * maxRequestSize:
 * Maximum total size allowed for the full multipart request.
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminCourtsController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public AdminCourtsController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method loads all court records from the database through
     * AdminCourtService and forwards them to the adminCourts.jsp page.
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
            // Creating service object to access court-related admin operations
            AdminCourtService service = new AdminCourtService();

            // Sets the active sidebar/page indicator as courts in the admin dashboard
            request.setAttribute("activePage", "courts");

            // Gets all courts from the service layer and sends them to the JSP page
            request.setAttribute("courtList", service.getAllCourts());

            // Forwards request and response to adminCourts.jsp for displaying court data
            request.getRequestDispatcher("/pages/adminCourts.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps courts page active even if court data fails to load
            request.setAttribute("activePage", "courts");

            // Sends an error message to the JSP page
            request.setAttribute("errorMessage", "Unable to load courts.");

            // Forwards back to adminCourts.jsp with the error message
            request.getRequestDispatcher("/pages/adminCourts.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method processes court-related actions submitted from the admin court page.
     * The action may be activate, deactivate, or addCourt.
     *
     * @param request  stores form data submitted from the admin court page
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gets the action value from the submitted form
            String action = request.getParameter("action");

            // Creating service object to perform court-related operations
            AdminCourtService service = new AdminCourtService();

            // If admin clicks activate, the selected court status is changed to active
            if ("activate".equals(action)) {
                int courtId = Integer.parseInt(request.getParameter("courtId"));
                service.updateCourtStatus(courtId, "active");

            // If admin clicks deactivate, the selected court status is changed to inactive
            } else if ("deactivate".equals(action)) {
                int courtId = Integer.parseInt(request.getParameter("courtId"));
                service.updateCourtStatus(courtId, "inactive");

            // If admin submits the add court form, a new court is created
            } else if ("addCourt".equals(action)) {

                // Creating CourtModel object to store submitted court data
                CourtModel court = new CourtModel();

                // Setting court details from form input fields
                court.setCourtName(request.getParameter("courtName"));
                court.setCourtNumber(request.getParameter("courtNumber"));
                court.setCourtCapacity(Integer.parseInt(request.getParameter("courtCapacity")));
                court.setSurfaceType(request.getParameter("surfaceType"));
                court.setPricePerHour(new BigDecimal(request.getParameter("pricePerHour")));
                court.setCourtStatus(request.getParameter("courtStatus"));
                court.setVenueId(Integer.parseInt(request.getParameter("venueId")));
                court.setSportTypeId(Integer.parseInt(request.getParameter("sportTypeId")));
                court.setStaffId(Integer.parseInt(request.getParameter("staffId")));

                // Gets the uploaded court image from the multipart form
                Part imagePart = request.getPart("courtImage");

                // Variable to store the final image file name
                String imageFileName = null;

                // Checks if image was uploaded and file size is greater than zero
                if (imagePart != null && imagePart.getSize() > 0) {

                    // Gets the original uploaded file name safely
                    String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

                    // Variable to store file extension, such as .jpg or .png
                    String extension = "";

                    // Finds the last dot in the file name to extract the extension
                    int dotIndex = originalFileName.lastIndexOf(".");
                    if (dotIndex != -1) {
                        extension = originalFileName.substring(dotIndex);
                    }

                    // Creates a unique file name using UUID to avoid duplicate image names
                    imageFileName = UUID.randomUUID().toString() + extension;

                    // Gets the real server path of the /photos folder
                    String uploadPath = getServletContext().getRealPath("/photos");

                    // Creates a File object for the upload directory
                    File uploadDir = new File(uploadPath);

                    // Creates the /photos folder if it does not already exist
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    // Saves the uploaded image inside the /photos folder
                    imagePart.write(uploadPath + File.separator + imageFileName);
                }

                // Sets the uploaded image file name in the court model
                court.setImagePath(imageFileName);

                // Sends the court model to service layer to add the new court
                service.addCourt(court);
            }

            // Redirects back to adminCourts page after completing the action
            response.sendRedirect(request.getContextPath() + "/adminCourts");

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Keeps courts page active in the admin dashboard
            request.setAttribute("activePage", "courts");

            // Sends an error message if court action fails
            request.setAttribute("errorMessage", "Unable to process court action.");

            // Reloads court page by calling doGet method
            doGet(request, response);
        }
    }
}
