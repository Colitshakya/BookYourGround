package com.model;

// Importing Serializable so PaymentModel objects can be stored or transferred safely
import java.io.Serializable;

// Importing BigDecimal to store payment amount accurately
import java.math.BigDecimal;

// Importing SQL Date to store payment date
import java.sql.Date;

/**
 * PaymentModel is a POJO/model class used to store payment information.
 *
 * This class represents payment details such as:
 * - Payment ID
 * - Payment amount
 * - Payment method
 * - Payment date
 * - Payment status
 * - Transaction reference
 * - Booking ID
 */
public class PaymentModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores payment ID
    private int paymentId;

    // Stores payment amount
    private BigDecimal paymentAmount;

    // Stores payment method, such as cash, card, or online
    private String paymentMethod;

    // Stores payment date
    private Date paymentDate;

    // Stores payment status, such as paid or pending
    private String paymentStatus;

    // Stores unique transaction reference
    private String transactionRef;

    // Stores booking ID linked with this payment
    private int bookingId;

    /**
     * Gets payment ID.
     *
     * @return payment ID
     */
    public int getPaymentId() {
        return paymentId;
    }

    /**
     * Sets payment ID.
     *
     * @param paymentId payment ID
     */
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Gets payment amount.
     *
     * @return payment amount
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets payment amount.
     *
     * @param paymentAmount payment amount
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Gets payment method.
     *
     * @return payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets payment method.
     *
     * @param paymentMethod payment method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets payment date.
     *
     * @return payment date
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets payment date.
     *
     * @param paymentDate payment date
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Gets payment status.
     *
     * @return payment status
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets payment status.
     *
     * @param paymentStatus payment status
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Gets transaction reference.
     *
     * @return transaction reference
     */
    public String getTransactionRef() {
        return transactionRef;
    }

    /**
     * Sets transaction reference.
     *
     * @param transactionRef transaction reference
     */
    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    /**
     * Gets booking ID.
     *
     * @return booking ID
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets booking ID.
     *
     * @param bookingId booking ID
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
