package com.controller.servlet;

import com.model.UserModel;
import com.service.UserProfileService;
import com.util.FileUploadUtil;
import com.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

/**
 * Servlet implementation class UserProfileController
 */
@WebServlet("/userProfile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + File.separator + "bookyourground_uploads";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * helper method to load profile page data
     */
    private void loadProfileData(HttpServletRequest request, UserProfileService service, int userId) throws Exception {
        UserModel user = service.getUserProfile(userId);

        int totalBookings = service.getTotalBookings(userId);
        int upcomingBookings = service.getUpcomingBookings(userId);
        int pendingPayments = service.getPendingPayments(userId);

        request.setAttribute("profileUser", user);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("upcomingBookings", upcomingBookings);
        request.setAttribute("pendingPayments", pendingPayments);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());

        try {
            UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            UserProfileService service = new UserProfileService();
            loadProfileData(request, service, loggedInUser.getUserId());

            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load profile.");
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        try {
            UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String action = request.getParameter("action");
            UserProfileService service = new UserProfileService();

            if ("updateProfile".equals(action)) {
                String firstName = request.getParameter("first_name");
                String lastName = request.getParameter("last_name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                if (firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty()) {

                    throw new Exception("All profile fields are required.");
                }

                UserModel user = new UserModel();
                user.setUserId(loggedInUser.getUserId());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhone(phone);

                service.updateUserProfile(user);

                UserModel updatedUser = service.getUserProfile(loggedInUser.getUserId());
                SessionUtil.setAttribute(request, "loggedInUser", updatedUser, 3600);

                loadProfileData(request, service, loggedInUser.getUserId());
                request.setAttribute("successMessage", "Changes saved successfully.");
                request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
                return;
            }

            if ("changePassword".equals(action) || "updatePassword".equals(action)) {
                String currentPassword = request.getParameter("current_password");
                String newPassword = request.getParameter("new_password");
                String confirmPassword = request.getParameter("confirm_password");

                if (currentPassword == null || currentPassword.trim().isEmpty() ||
                    newPassword == null || newPassword.trim().isEmpty() ||
                    confirmPassword == null || confirmPassword.trim().isEmpty()) {

                    throw new Exception("All password fields are required.");
                }

                if (!newPassword.equals(confirmPassword)) {
                    throw new Exception("New passwords do not match.");
                }

                service.changePassword(loggedInUser.getUserId(), currentPassword, newPassword);

                UserModel updatedUser = service.getUserProfile(loggedInUser.getUserId());
                SessionUtil.setAttribute(request, "loggedInUser", updatedUser, 3600);

                loadProfileData(request, service, loggedInUser.getUserId());
                request.setAttribute("successMessage", "Password updated successfully.");
                request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
                return;
            }

            if ("uploadPhoto".equals(action)) {
                Part filePart = request.getPart("profileImage");

                if (filePart == null || filePart.getSize() == 0) {
                    throw new Exception("Please select an image to upload.");
                }

                if (!FileUploadUtil.isImage(filePart)) {
                    throw new Exception("Only image files are allowed.");
                }

                String originalName = filePart.getSubmittedFileName();
                String extension = FileUploadUtil.getFileExtension(originalName);
                String fileName = String.valueOf(loggedInUser.getUserId()) + extension;

                FileUploadUtil.saveFile(filePart, UPLOAD_DIR, fileName);

                response.sendRedirect(request.getContextPath() + "/userProfile");
                return;
            }

            if ("deleteAccount".equals(action)) {
                service.softDeleteUser(loggedInUser.getUserId());
                SessionUtil.invalidateSession(request);
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            loadProfileData(request, service, loggedInUser.getUserId());
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            try {
                UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");
                if (loggedInUser != null) {
                    UserProfileService service = new UserProfileService();
                    loadProfileData(request, service, loggedInUser.getUserId());
                }
            } catch (Exception ignored) {
            }

            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/pages/userProfile.jsp").forward(request, response);
        }
    }
}