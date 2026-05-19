package com.dao;

import com.model.PaymentModel;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentDAO {

    public void insertPayment(PaymentModel payment) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO payment "
                   + "(payment_amount, payment_method, payment_date, payment_status, transaction_ref, booking_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setBigDecimal(1, payment.getPaymentAmount());
        pst.setString(2, payment.getPaymentMethod());
        pst.setDate(3, payment.getPaymentDate());
        pst.setString(4, payment.getPaymentStatus());
        pst.setString(5, payment.getTransactionRef());
        pst.setInt(6, payment.getBookingId());

        pst.executeUpdate();

        pst.close();
        con.close();
    }
}