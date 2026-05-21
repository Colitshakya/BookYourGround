package com.service;

// Importing PaymentDAO to insert payment records into the database
import com.dao.PaymentDAO;

// Importing PaymentModel to store payment details
import com.model.PaymentModel;

/**
 * PaymentService contains business logic for payment processing.
 *
 * This service validates payment information before saving it through PaymentDAO.
 *
 * It is responsible for:
 * - Checking payment amount
 * - Checking payment method
 * - Checking payment date
 * - Checking payment status
 * - Checking booking ID
 * - Saving valid payment records
 */
public class PaymentService {

    /**
     * Validates and saves payment details.
     *
     * @param payment PaymentModel object containing payment data
     * @throws Exception if payment validation or insertion fails
     */
    public void makePayment(PaymentModel payment) throws Exception {
        // Checks whether payment amount exists
        if (payment.getPaymentAmount() == null) {
            throw new Exception("Payment amount is required.");
        }

        // Checks whether payment method is selected
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            throw new Exception("Payment method is required.");
        }

        // Checks whether payment date exists
        if (payment.getPaymentDate() == null) {
            throw new Exception("Payment date is required.");
        }

        // Checks whether payment status exists
        if (payment.getPaymentStatus() == null || payment.getPaymentStatus().trim().isEmpty()) {
            throw new Exception("Payment status is required.");
        }

        // Checks whether booking ID is valid
        if (payment.getBookingId() <= 0) {
            throw new Exception("Invalid booking.");
        }

        // Creating PaymentDAO object to insert payment record
        PaymentDAO dao = new PaymentDAO();

        // Saving payment details into database
        dao.insertPayment(payment);
    }
}
