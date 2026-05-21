package com.controller.servlet;

// Importing UserModel to store and transfer user profile data
import com.model.UserModel;

// Importing service class that handles user profile-related business logic
import com.service.UserProfileService;

// Importing FileUploadUtil to validate and save uploaded profile images
import com.util.FileUploadUtil;

// Importing SessionUtil to get, set, and invalidate user session data
import com.util.SessionUtil;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// Importing File class to create upload directory path
import java.io.File;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * Servlet implementation class UserProfileController
 *
 * UserProfileController handles user profile-related requests.
 *
 * URL Mapping:
 * /userProfile
 *
 * This controller:
 * - Displays logged-in user's profile data
 * - Shows user dashboard counts such as total bookings, upcoming bookings, and pending payments
 * - Updates user profile details
 * - Changes user password
 * - Uploads user profile image
 * - Soft deletes user account
 */
@WebServlet("/userProfile")

/**
 * MultipartConfig is used because this servlet handles profile image upload.
 *
 * fileSizeThreshold:
 * Files larger than this size may be stored temporarily on disk.
 *
 * maxFileSize:
 * Maximum size allowed for one uploaded file.
 *
 * maxRequestSize:
 * Maximum total size allowed for the whole multipart form request.
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class UserProfileController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Folder path where uploaded profile images are stored inside the user's home directory
    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + File.separator + "bookyourground_uploads";

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     *
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Helper method to load profile page data.
     *
     * This method gets the user's profile information and dashboard summary data,
     * then stores them in request attributes so userProfile.jsp can display them.
     *
     * @param request stores data that will be sent to the JSP page
     * @param service service object used to get profile-related data
     * @param userId ID of the logged-in user
     * @throws Exception if profile data cannot be loaded
     */
    private void loadProfileData(HttpServletRequest request, UserProfileService service, int userId) throws Exception {

        // Retrieves full user profile information
        UserModel user = service.getUserProfile(userId);

        // Retrieves total number of bookings made by the user
        int totalBookings = service.getTotalBookings(userId);

        // Retrieves number of upcoming bookings for the user
        int upcomingBookings = service.getUpcomingBookings(userId);

        // Retrieves number of pending payments for the user
        int pendingPayments = service.getPendingPayments(userId);

        // Sends user profile data to JSP
        request.setAttribute("profileUser", user);

        // Sends total booking count to JSP
        request.setAttribute("totalBookings", totalBookings);

        // Sends upcoming booking count to JSP
        request.setAttribute("upcomingBookings", upcomingBookings);

        // Sends pending payment count to JSP
        request.setAttribute("pendingPayments", pendingPayments);
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /userProfile URL.
     * It checks whether the user is logged in, loads the profile data,
     * and forwards the request to userProfile.jsp.
     *
     * @param request stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());

        try {
            // Retrieves logged-in user from the session
            UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

            // If no user is logged in, redirect to login page
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Creating service object to retrieve profile-related data
            UserProfileService service = new UserProfileService();

            // Loads user profile information and dashboard summary data
            loadProfileData(request, service, loggedInUser.getUserId());

            // Forwards request and response to userProfile.jsp for display
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            // Sends error message to JSP if profile cannot be loaded
            request.setAttribute("errorMessage", "Unable to load profile.");

            // Forwards back to userProfile.jsp with error message
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method is called when the user submits an action from the profile page.
     * It checks the submitted action and performs profile update, password change,
     * photo upload, account deletion, or reloads the profile page.
     *
     * @param request stores form data submitted by the user
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        try {
            // Retrieves logged-in user from the session
            UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

            // If no user is logged in, redirect to login page
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Gets action value from the submitted form
            String action = request.getParameter("action");

            // Creating service object to perform user profile operations
            UserProfileService service = new UserProfileService();

            // Handles profile information update action
            if ("updateProfile".equals(action)) {

                // Gets updated first name from form
                String firstName = request.getParameter("first_name");

                // Gets updated last name from form
                String lastName = request.getParameter("last_name");

                // Gets updated email from form
                String email = request.getParameter("email");

                // Gets updated phone number from form
                String phone = request.getParameter("phone");

                // Checks whether any profile field is empty
                if (firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty()) {

                    // Stops profile update if required fields are empty
                    throw new Exception("All profile fields are required.");
                }

                // Creating UserModel object to store updated profile details
                UserModel user = new UserModel();

                // Sets logged-in user's ID so the correct record is updated
                user.setUserId(loggedInUser.getUserId());

                // Sets updated first name
                user.setFirstName(firstName);

                // Sets updated last name
                user.setLastName(lastName);

                // Sets updated email
                user.setEmail(email);

                // Sets updated phone number
                user.setPhone(phone);

                // Sends updated user data to service layer
                service.updateUserProfile(user);

                // Retrieves updated user data after saving changes
                UserModel updatedUser = service.getUserProfile(loggedInUser.getUserId());

                // Updates session with latest user information
                SessionUtil.setAttribute(request, "loggedInUser", updatedUser, 3600);

                // Reloads profile data after update
                loadProfileData(request, service, loggedInUser.getUserId());

                // Sends success message to JSP
                request.setAttribute("successMessage", "Changes saved successfully.");

                // Forwards back to profile page
                request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
                return;
            }

            // Handles password change action
            if ("changePassword".equals(action) || "updatePassword".equals(action)) {

                // Gets current password from form
                String currentPassword = request.getParameter("current_password");

                // Gets new password from form
                String newPassword = request.getParameter("new_password");

                // Gets confirm password from form
                String confirmPassword = request.getParameter("confirm_password");

                // Checks whether any password field is empty
                if (currentPassword == null || currentPassword.trim().isEmpty() ||
                    newPassword == null || newPassword.trim().isEmpty() ||
                    confirmPassword == null || confirmPassword.trim().isEmpty()) {

                    // Stops password update if required fields are empty
                    throw new Exception("All password fields are required.");
                }

                // Checks whether new password and confirm password match
                if (!newPassword.equals(confirmPassword)) {
                    throw new Exception("New passwords do not match.");
                }

                // Sends password change request to service layer
                service.changePassword(loggedInUser.getUserId(), currentPassword, newPassword);

                // Retrieves updated user data after password update
                UserModel updatedUser = service.getUserProfile(loggedInUser.getUserId());

                // Updates session with latest user information
                SessionUtil.setAttribute(request, "loggedInUser", updatedUser, 3600);

                // Reloads profile data after password update
                loadProfileData(request, service, loggedInUser.getUserId());

                // Sends success message to JSP
                request.setAttribute("successMessage", "Password updated successfully.");

                // Forwards back to profile page
                request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
                return;
            }

            // Handles profile photo upload action
            if ("uploadPhoto".equals(action)) {

                // Gets uploaded file from form input named profileImage
                Part filePart = request.getPart("profileImage");

                // Checks whether user selected an image file
                if (filePart == null || filePart.getSize() == 0) {
                    throw new Exception("Please select an image to upload.");
                }

                // Checks whether uploaded file is an image
                if (!FileUploadUtil.isImage(filePart)) {
                    throw new Exception("Only image files are allowed.");
                }

                // Gets original uploaded file name
                String originalName = filePart.getSubmittedFileName();

                // Gets file extension from original file name
                String extension = FileUploadUtil.getFileExtension(originalName);

                // Creates image file name using user ID to keep one profile image per user
                String fileName = String.valueOf(loggedInUser.getUserId()) + extension;

                // Saves uploaded file in the upload directory
                FileUploadUtil.saveFile(filePart, UPLOAD_DIR, fileName);

                // Redirects back to user profile page after photo upload
                response.sendRedirect(request.getContextPath() + "/userProfile");
                return;
            }

            // Handles account delete action
            if ("deleteAccount".equals(action)) {

                // Marks user account as deleted using service layer
                service.softDeleteUser(loggedInUser.getUserId());

                // Destroys user session after account deletion
                SessionUtil.invalidateSession(request);

                // Redirects user to home page after deleting account
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            // If no specific action matches, reload profile data
            loadProfileData(request, service, loggedInUser.getUserId());

            // Forwards back to user profile page
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);

        } catch (Exception e) {
            // Prints error details in the console for debugging
            e.printStackTrace();

            try {
                // Tries to retrieve logged-in user again from session
                UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

                // If user is still logged in, reload profile data even after error
                if (loggedInUser != null) {
                    UserProfileService service = new UserProfileService();
                    loadProfileData(request, service, loggedInUser.getUserId());
                }
            } catch (Exception ignored) {
                // Ignores errors that happen while reloading profile data after main error
            }

            // Sends error message to JSP
            request.setAttribute("errorMessage", e.getMessage());

            // Forwards back to userProfile.jsp with error message
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
        }
    }
}
