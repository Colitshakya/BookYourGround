package com.service;

import com.dao.PaymentDAO;
import com.model.PaymentModel;

public class PaymentService {

    public void makePayment(PaymentModel payment) throws Exception {
        if (payment.getPaymentAmount() == null) {
            throw new Exception("Payment amount is required.");
        }

        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            throw new Exception("Payment method is required.");
        }

        if (payment.getPaymentDate() == null) {
            throw new Exception("Payment date is required.");
        }

        if (payment.getPaymentStatus() == null || payment.getPaymentStatus().trim().isEmpty()) {
            throw new Exception("Payment status is required.");
        }

        if (payment.getBookingId() <= 0) {
            throw new Exception("Invalid booking.");
        }

        PaymentDAO dao = new PaymentDAO();
        dao.insertPayment(payment);
    }
}