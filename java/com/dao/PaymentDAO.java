package com.dao;

// Importing PaymentModel to get payment details
import com.model.PaymentModel;

// Importing DBConnection to create a connection with the database
import com.util.DBConnection;

// Importing SQL classes needed for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * PaymentDAO handles database operations related to payment records.
 *
 * This class is responsible for:
 * - Inserting payment details into the payment table
 */
public class PaymentDAO {

    /**
     * Inserts a new payment record into the database.
     *
     * This method receives a PaymentModel object containing payment amount,
     * payment method, payment date, payment status, transaction reference,
     * and booking ID. It then stores these details in the payment table.
     *
     * @param payment PaymentModel object containing payment information
     * @throws Exception if database connection or insert operation fails
     */
    public void insertPayment(PaymentModel payment) throws Exception {

        // Creating database connection using DBConnection utility class
        Connection con = DBConnection.getConnection();

        // SQL query to insert payment details into the payment table
        String sql = "INSERT INTO payment "
                   + "(payment_amount, payment_method, payment_date, payment_status, transaction_ref, booking_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        // Preparing SQL statement to safely insert dynamic values
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting payment amount from PaymentModel
        pst.setBigDecimal(1, payment.getPaymentAmount());

        // Setting payment method from PaymentModel
        pst.setString(2, payment.getPaymentMethod());

        // Setting payment date from PaymentModel
        pst.setDate(3, payment.getPaymentDate());

        // Setting payment status from PaymentModel
        pst.setString(4, payment.getPaymentStatus());

        // Setting transaction reference from PaymentModel
        pst.setString(5, payment.getTransactionRef());

        // Setting booking ID to link payment with the correct booking
        pst.setInt(6, payment.getBookingId());

        // Executing insert query to save payment record in the database
        pst.executeUpdate();

        // Closing PreparedStatement resource
        pst.close();

        // Closing database connection
        con.close();
    }
}
