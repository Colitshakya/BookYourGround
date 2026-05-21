package com.controller.servlet;

// Importing UserModel to store logged-in user details
import com.model.UserModel;

// Importing LoginService to handle login authentication logic
import com.service.LoginService;

// Importing CookieUtil to create and store login-related cookie data
import com.util.CookieUtil;

// Importing SessionUtil to create and manage user session data
import com.util.SessionUtil;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

// Importing LocalDateTime to get the current login date and time
import java.time.LocalDateTime;

// Importing DateTimeFormatter to format login time before storing it in cookie
import java.time.format.DateTimeFormatter;

/**
 * LoginController handles user and admin login requests.
 *
 * URL Mapping:
 * /login
 *
 * This controller:
 * - Displays the login page using doGet()
 * - Shows registration success message if user was registered
 * - Validates email and password from login form
 * - Authenticates user through LoginService
 * - Stores logged-in user data in session
 * - Stores last login time in cookie
 * - Redirects admin and normal users to different pages
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public LoginController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /login URL.
     * It also checks whether the user was redirected after successful registration
     * and displays a suitable success message if the account is pending approval.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Gets registered parameter from the URL, for example /login?registered=pending
        String registered = request.getParameter("registered");

        // If registration was successful and account is pending, show success message
        if ("pending".equalsIgnoreCase(registered)) {
            request.setAttribute("successMessage",
                    "Registration successful. Please wait for admin approval before logging in.");
        }

        // Forwards request and response to login.jsp to display the login page
        request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method is called when the login form is submitted.
     * It validates email and password, checks user credentials through LoginService,
     * creates session and cookie after successful login, and redirects based on role.
     *
     * @param request  stores login form data submitted by the user
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gets email entered by the user from login form
            String email = request.getParameter("email");

            // Gets password entered by the user from login form
            String password = request.getParameter("password");

            // Sends email back to JSP so it can remain filled if login fails
            request.setAttribute("email", email);

            // Checks whether email field is empty
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Email is required.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            }

            // Checks whether password field is empty
            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Password is required.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            }

            // Creating service object to authenticate the user
            LoginService service = new LoginService();

            // Checks login credentials and returns user details if valid
            UserModel user = service.loginUser(email, password);

            // If user is found and credentials are valid
            if (user != null) {

                // Stores logged-in user object in session for 3600 seconds
                SessionUtil.setAttribute(request, "loggedInUser", user, 3600);

                // Gets current date and time for last login cookie
                LocalDateTime now = LocalDateTime.now();

                // Defines date-time format for cookie value
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

                // Converts current login time into formatted String
                String loginTime = now.format(formatter);

                // Adds last login time as a cookie for 3600 seconds
                CookieUtil.addCookie(response, "last_login", loginTime, 3600);

                // If logged-in user is admin, redirect to admin dashboard
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/adminDashboard");

                // If logged-in user is normal user, redirect to home page
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }

            // If login credentials are invalid, show error message
            } else {
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Prints error details in console for debugging
            e.printStackTrace();

            // Sends exception message to login.jsp
            request.setAttribute("errorMessage", e.getMessage());

            // Forwards back to login.jsp with error message
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
}
