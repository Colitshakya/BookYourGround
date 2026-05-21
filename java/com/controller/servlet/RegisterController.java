package com.controller.servlet;

// Importing UserModel to store registration form data
import com.model.UserModel;

// Importing RegisterService to handle registration-related business logic
import com.service.RegisterService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * RegisterController handles user registration requests.
 *
 * URL Mapping:
 * /register
 *
 * This controller:
 * - Displays the registration page using doGet()
 * - Receives registration form data using doPost()
 * - Performs backend validation for user input
 * - Creates a UserModel object
 * - Sends user data to RegisterService for saving
 * - Displays success or error messages
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public RegisterController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /register URL.
     * It forwards the request to register.jsp so the registration form can be displayed.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forwards request and response to the registration page
        request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method is called when the registration form is submitted.
     * It collects form data, validates the input, creates a UserModel object,
     * and sends the user data to RegisterService for registration.
     *
     * @param request  stores registration form data submitted by the user
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gets first name from the registration form
            String firstName = request.getParameter("first_name");

            // Gets last name from the registration form
            String lastName = request.getParameter("last_name");

            // Gets email address from the registration form
            String email = request.getParameter("email");

            // Gets phone number from the registration form
            String phone = request.getParameter("phone");

            // Gets password from the registration form
            String password = request.getParameter("password");

            // Gets confirm password from the registration form
            String confirmPassword = request.getParameter("confirm_password");

            // Gets terms and conditions checkbox value from the registration form
            String terms = request.getParameter("terms");

            // Sends entered first name back to JSP so the field does not become empty after error
            request.setAttribute("firstName", firstName);

            // Sends entered last name back to JSP so the field does not become empty after error
            request.setAttribute("lastName", lastName);

            // Sends entered email back to JSP so the field does not become empty after error
            request.setAttribute("email", email);

            // Sends entered phone number back to JSP so the field does not become empty after error
            request.setAttribute("phone", phone);

            // Checks whether any required field is empty
            if (isEmpty(firstName) || isEmpty(lastName) || isEmpty(email) || isEmpty(phone)
                    || isEmpty(password) || isEmpty(confirmPassword)) {

                // Sends error message to register.jsp
                request.setAttribute("errorMessage", "All fields are required.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Checks whether first name and last name contain letters only
            if (!firstName.matches("^[A-Za-z ]+$") || !lastName.matches("^[A-Za-z ]+$")) {

                // Sends name validation error message to register.jsp
                request.setAttribute("errorMessage", "Name should contain letters only.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Checks whether email follows a valid email format
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                // Sends email validation error message to register.jsp
                request.setAttribute("errorMessage", "Please enter a valid email address.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Checks whether phone number follows a valid Nepali phone number format
            if (!phone.matches("^(\\+977)?9[78][0-9]{8}$")) {

                // Sends phone validation error message to register.jsp
                request.setAttribute("errorMessage", "Please enter a valid Nepali phone number.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Checks whether password length is at least 8 characters
            if (password.length() < 8) {

                // Sends password length error message to register.jsp
                request.setAttribute("errorMessage", "Password must be at least 8 characters long.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Checks whether password and confirm password match
            if (!password.equals(confirmPassword)) {

                // Sends password mismatch error message to register.jsp
                request.setAttribute("errorMessage", "Passwords do not match.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Checks whether the user agreed to the Terms and Conditions
            if (terms == null) {

                // Sends terms and conditions error message to register.jsp
                request.setAttribute("errorMessage", "You must agree to the Terms and Conditions.");

                // Forwards back to register page with error message
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }

            // Creating UserModel object to store validated user data
            UserModel user = new UserModel();

            // Sets first name after removing extra spaces
            user.setFirstName(firstName.trim());

            // Sets last name after removing extra spaces
            user.setLastName(lastName.trim());

            // Sets email after removing extra spaces
            user.setEmail(email.trim());

            // Sets phone number after removing extra spaces
            user.setPhone(phone.trim());

            // Sets password before sending it to the service layer
            user.setPassword(password);

            // Sets user status as active
            user.setStatus("active");

            // Creating service object to register the user
            RegisterService service = new RegisterService();

            // Sends user data to service layer for saving into the database
            service.addUser(user);

            // Sends success message after successful registration
            request.setAttribute("successMessage", "Registration successful. Please log in.");

            // Forwards user to login page after successful registration
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);

        } catch (Exception e) {
            // Sends exception message to register.jsp if registration fails
            request.setAttribute("errorMessage", e.getMessage());

            // Forwards back to register page with error message
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        }
    }

    /**
     * Checks whether a given string value is null or empty.
     *
     * @param value the string value to check
     * @return true if the value is null or empty, otherwise false
     */
    private boolean isEmpty(String value) {

        // Returns true if value is null or contains only spaces
        return value == null || value.trim().isEmpty();
    }
}
