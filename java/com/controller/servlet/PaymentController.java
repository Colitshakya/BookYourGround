package com.controller.servlet;

import com.model.PaymentModel;
import com.service.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PaymentController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingId = request.getParameter("bookingId");
        String amount = request.getParameter("amount");
        String courtId = request.getParameter("courtId");
        String bookingDate = request.getParameter("bookingDate");

        request.setAttribute("bookingId", bookingId);
        request.setAttribute("amount", amount);
        request.setAttribute("courtId", courtId);
        request.setAttribute("bookingDate", bookingDate);

        request.getRequestDispatcher("/pages/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingId = request.getParameter("bookingId");
        String amount = request.getParameter("amount");
        String courtId = request.getParameter("courtId");
        String bookingDate = request.getParameter("bookingDate");

        try {
            PaymentModel payment = new PaymentModel();

            payment.setBookingId(Integer.parseInt(bookingId));
            payment.setPaymentAmount(new BigDecimal(amount));
            payment.setPaymentMethod(request.getParameter("paymentMethod"));
            payment.setPaymentDate(Date.valueOf(LocalDate.now()));
            payment.setPaymentStatus("paid");
            payment.setTransactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            PaymentService service = new PaymentService();
            service.makePayment(payment);

            String successMessage = URLEncoder.encode(
                    "Payment recorded successfully.",
                    StandardCharsets.UTF_8
            );

            response.sendRedirect(request.getContextPath()
                    + "/courtDetails?courtId=" + courtId
                    + "&bookingDate=" + bookingDate
                    + "&bookingSuccessMessage=" + successMessage);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("bookingId", bookingId);
            request.setAttribute("amount", amount);
            request.setAttribute("courtId", courtId);
            request.setAttribute("bookingDate", bookingDate);

            request.getRequestDispatcher("/pages/payment.jsp").forward(request, response);
        }
    }
}