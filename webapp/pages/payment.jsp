<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment.css">
</head>
<body>

<%@ include file="/includes/header.jsp" %>

<main class="payment-page">
    <div class="payment-container">
        <div class="payment-card">
            <h1>Payment</h1>
            <p class="payment-subtext">Complete your booking payment below.</p>

            <div class="success-message"
                 style="${empty successMessage ? 'display:none;' : 'display:block;'}">
                ${successMessage}
            </div>

            <div class="error-message"
                 style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
                ${errorMessage}
            </div>

            <form action="${pageContext.request.contextPath}/payment" method="post" class="payment-form">
                <input type="hidden" name="bookingId" value="${bookingId}">
                <input type="hidden" name="amount" value="${amount}">
                <input type="hidden" name="courtId" value="${courtId}">
                <input type="hidden" name="bookingDate" value="${bookingDate}">

                <div class="form-group">
                    <label>Booking ID</label>
                    <input type="text" value="${bookingId}" readonly>
                </div>

                <div class="form-group">
                    <label>Amount</label>
                    <input type="text" value="Rs. ${amount}" readonly>
                </div>

                <div class="form-group">
                    <label for="paymentMethod">Payment Method</label>
                    <select name="paymentMethod" id="paymentMethod" required>
                        <option value="">Select Payment Method</option>
                        <option value="Esewa">Esewa</option>
                        <option value="Khalti">Khalti</option>
                    </select>
                </div>

                <button type="submit" class="payment-btn">Pay Now</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="/includes/footer.jsp" %>

</body>
</html>