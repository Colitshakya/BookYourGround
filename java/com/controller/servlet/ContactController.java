package com.controller.servlet;

// Importing ContactModel to store contact form data
import com.model.ContactModel;

// Importing service class that handles contact message business logic
import com.service.ContactService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

/**
 * ContactController handles requests for the Contact page.
 *
 * URL Mapping:
 * /contact
 *
 * This controller:
 * - Displays the contact page using doGet()
 * - Receives contact form data using doPost()
 * - Saves the contact message using ContactService
 * - Sends success or error messages back to contact.jsp
 */
@WebServlet("/contact")
public class ContactController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public ContactController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user opens the /contact URL.
     * It forwards the user to contact.jsp so the contact form can be displayed.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forwards request and response to the contact page
        request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method is called when the user submits the contact form.
     * It collects form data, stores it inside ContactModel, sends it to
     * ContactService for saving, and then displays success or error messages.
     *
     * @param request  stores form data submitted by the user
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Creating ContactModel object to store submitted contact form data
            ContactModel contact = new ContactModel();

            // Setting contact details from form input fields
            contact.setFullName(request.getParameter("fullName"));
            contact.setEmail(request.getParameter("email"));
            contact.setSubject(request.getParameter("subject"));
            contact.setMessage(request.getParameter("message"));

            // Sending entered values back to JSP so fields can retain data if needed
            request.setAttribute("fullName", contact.getFullName());
            request.setAttribute("email", contact.getEmail());
            request.setAttribute("subject", contact.getSubject());
            request.setAttribute("message", contact.getMessage());

            // Creating service object to save the contact message
            ContactService service = new ContactService();

            // Saves the submitted contact message through the service layer
            service.saveMessage(contact);

            // Sends success message to contact.jsp after message is saved
            request.setAttribute("successMessage", "Your message has been submitted successfully.");

            // Clears form values after successful message submission
            request.setAttribute("fullName", "");
            request.setAttribute("email", "");
            request.setAttribute("subject", "");
            request.setAttribute("message", "");

            // Forwards back to contact.jsp with success message
            request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);

        } catch (Exception e) {
            // Sends error message to contact.jsp if saving contact message fails
            request.setAttribute("errorMessage", e.getMessage());

            // Forwards back to contact.jsp with error message
            request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);
        }
    }
}
