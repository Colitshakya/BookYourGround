package com.controller.servlet;

// Importing PaymentModel to store payment details before sending them to the service layer
import com.model.PaymentModel;

// Importing PaymentService to handle payment-related business logic
import com.service.PaymentService;

// Importing required Jakarta Servlet classes
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importing IOException to handle input/output errors
import java.io.IOException;

// Importing BigDecimal to store payment amount accurately
import java.math.BigDecimal;

// Importing URLEncoder to safely send success messages through URL parameters
import java.net.URLEncoder;

// Importing StandardCharsets to encode URL messages using UTF-8
import java.nio.charset.StandardCharsets;

// Importing SQL Date to store payment date in database format
import java.sql.Date;

// Importing LocalDate to get the current date
import java.time.LocalDate;

// Importing UUID to generate a unique transaction reference
import java.util.UUID;

/**
 * PaymentController handles payment-related requests.
 *
 * URL Mapping:
 * /payment
 *
 * This controller:
 * - Displays the payment page using doGet()
 * - Receives payment form data using doPost()
 * - Creates a PaymentModel object
 * - Saves payment details through PaymentService
 * - Redirects the user back to court details page with a success message
 */
@WebServlet("/payment")
public class PaymentController extends HttpServlet {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Calls the parent HttpServlet constructor.
     */
    public PaymentController() {
        super();
    }

    /**
     * Handles HTTP GET requests.
     *
     * This method is called when the user is redirected to the payment page
     * after successfully creating a booking. It receives booking ID, amount,
     * court ID, and booking date from the URL and sends them to payment.jsp.
     *
     * @param request  stores request data sent by the browser
     * @param response sends response data back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Gets booking ID from the URL parameter
        String bookingId = request.getParameter("bookingId");

        // Gets payment amount from the URL parameter
        String amount = request.getParameter("amount");

        // Gets court ID from the URL parameter
        String courtId = request.getParameter("courtId");

        // Gets selected booking date from the URL parameter
        String bookingDate = request.getParameter("bookingDate");

        // Sends booking ID to payment.jsp
        request.setAttribute("bookingId", bookingId);

        // Sends payment amount to payment.jsp
        request.setAttribute("amount", amount);

        // Sends court ID to payment.jsp
        request.setAttribute("courtId", courtId);

        // Sends booking date to payment.jsp
        request.setAttribute("bookingDate", bookingDate);

        // Forwards request and response to payment.jsp to display the payment form
        request.getRequestDispatcher("/pages/payment.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     *
     * This method is called when the user submits the payment form.
     * It creates a payment record, generates a transaction reference,
     * saves payment details using PaymentService, and redirects the user
     * back to the court details page with a success message.
     *
     * @param request  stores payment form data submitted by the user
     * @param response sends redirect or response back to the browser
     * @throws ServletException if servlet forwarding or processing fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Gets booking ID from the submitted form
        String bookingId = request.getParameter("bookingId");

        // Gets payment amount from the submitted form
        String amount = request.getParameter("amount");

        // Gets court ID from the submitted form
        String courtId = request.getParameter("courtId");

        // Gets booking date from the submitted form
        String bookingDate = request.getParameter("bookingDate");

        try {
            // Creating PaymentModel object to store payment details
            PaymentModel payment = new PaymentModel();

            // Sets booking ID after converting it from String to integer
            payment.setBookingId(Integer.parseInt(bookingId));

            // Sets payment amount using BigDecimal for accurate currency handling
            payment.setPaymentAmount(new BigDecimal(amount));

            // Sets selected payment method from the form
            payment.setPaymentMethod(request.getParameter("paymentMethod"));

            // Sets payment date as today's date
            payment.setPaymentDate(Date.valueOf(LocalDate.now()));

            // Sets payment status as paid
            payment.setPaymentStatus("paid");

            // Generates a unique transaction reference starting with TXN-
            payment.setTransactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            // Creating service object to process payment
            PaymentService service = new PaymentService();

            // Saves payment details through the service layer
            service.makePayment(payment);

            // Encodes success message so it can be safely passed in the URL
            String successMessage = URLEncoder.encode(
                    "Payment recorded successfully.",
                    StandardCharsets.UTF_8
            );

            // Redirects back to court details page with booking success message
            response.sendRedirect(request.getContextPath()
                    + "/courtDetails?courtId=" + courtId
                    + "&bookingDate=" + bookingDate
                    + "&bookingSuccessMessage=" + successMessage);

        } catch (Exception e) {
            // Sends error message to payment.jsp if payment process fails
            request.setAttribute("errorMessage", e.getMessage());

            // Sends booking ID back to payment.jsp so the form can keep its data
            request.setAttribute("bookingId", bookingId);

            // Sends amount back to payment.jsp
            request.setAttribute("amount", amount);

            // Sends court ID back to payment.jsp
            request.setAttribute("courtId", courtId);

            // Sends booking date back to payment.jsp
            request.setAttribute("bookingDate", bookingDate);

            // Forwards back to payment.jsp with error message and previous form data
            request.getRequestDispatcher("/pages/payment.jsp").forward(request, response);
        }
    }
}
