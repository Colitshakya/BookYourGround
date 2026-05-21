<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment - Book Your Ground</title>

<!-- Linking external CSS file for payment page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment.css">
</head>
<body>

<!-- Including common website header -->
<%@ include file="/includes/header.jsp" %>

<!-- Main payment page content -->
<main class="payment-page">
    <div class="payment-container">

        <!-- Payment card section -->
        <div class="payment-card">
            <h1>Payment</h1>
            <p class="payment-subtext">Complete your booking payment below.</p>

            <!-- Success message section displayed only when successMessage exists -->
            <div class="success-message"
                 style="${empty successMessage ? 'display:none;' : 'display:block;'}">
                ${successMessage}
            </div>

            <!-- Error message section displayed only when errorMessage exists -->
            <div class="error-message"
                 style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
                ${errorMessage}
            </div>

            <!-- Payment form submitted to PaymentController -->
            <form action="${pageContext.request.contextPath}/payment" method="post" class="payment-form">

                <!-- Hidden booking details passed to backend -->
                <input type="hidden" name="bookingId" value="${bookingId}">
                <input type="hidden" name="amount" value="${amount}">
                <input type="hidden" name="courtId" value="${courtId}">
                <input type="hidden" name="bookingDate" value="${bookingDate}">

                <!-- Read-only booking ID display -->
                <div class="form-group">
                    <label>Booking ID</label>
                    <input type="text" value="${bookingId}" readonly>
                </div>

                <!-- Read-only payment amount display -->
                <div class="form-group">
                    <label>Amount</label>
                    <input type="text" value="Rs. ${amount}" readonly>
                </div>

                <!-- Payment method selection -->
                <div class="form-group">
                    <label for="paymentMethod">Payment Method</label>
                    <select name="paymentMethod" id="paymentMethod" required>
                        <option value="">Select Payment Method</option>
                        <option value="Esewa">Esewa</option>
                        <option value="Khalti">Khalti</option>
                    </select>
                </div>

                <!-- Payment submit button -->
                <button type="submit" class="payment-btn">Pay Now</button>
            </form>
        </div>
    </div>
</main>

<!-- Including common website footer -->
<%@ include file="/includes/footer.jsp" %>

</body>
</html>
