<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!-- JSTL core tag library used for loops and conditions -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Bookings - Book Your Ground</title>

<!-- Linking external CSS file for my bookings page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/myBookings.css">
</head>
<body>

<!-- Including common website header -->
<%@ include file="/includes/header.jsp" %>

<!-- Breadcrumb navigation section -->
<div class="breadcrumb-wrap">
    <div class="breadcrumb-container">
        <a href="${pageContext.request.contextPath}/home" class="breadcrumb-link">Home</a>
        <span class="breadcrumb-separator">›</span>
        <a href="${pageContext.request.contextPath}/userProfile" class="breadcrumb-link">Profile</a>
        <span class="breadcrumb-separator">›</span>
        <span class="breadcrumb-current">Bookings</span>
    </div>
</div>

<!-- Main my bookings page content -->
<main class="my-bookings-page">

    <div class="my-bookings-container">

        <!-- Page heading section -->
        <div class="my-bookings-head">
            <p class="my-bookings-tag">USER PANEL</p>
            <h1>My Bookings</h1>
            <p class="my-bookings-subtext">View all your court bookings in one place.</p>
        </div>

        <!-- Error message section displayed only when errorMessage exists -->
        <div class="message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <!-- Empty booking message shown when user has no bookings -->
        <div class="my-bookings-empty"
             style="${empty bookingHistoryList ? 'display:block;' : 'display:none;'}">
            You do not have any bookings yet.
        </div>

        <!-- Booking history grid shown when booking records exist -->
        <div class="my-bookings-grid"
             style="${empty bookingHistoryList ? 'display:none;' : 'grid'}">

            <!-- Looping through bookingHistoryList and displaying each booking card -->
            <c:forEach var="booking" items="${bookingHistoryList}">
                <div class="booking-card">

                    <!-- Booking card heading with sport, court name, and status -->
                    <div class="booking-card-top">
                        <div>
                            <p class="booking-sport">${booking.sportName}</p>
                            <h3>${booking.courtName}</h3>
                        </div>

                        <span class="booking-status ${booking.bookingStatus == 'confirmed' ? 'booking-confirmed' : 'booking-pending'}">
                            ${booking.bookingStatus}
                        </span>
                    </div>

                    <!-- Booking date and time details -->
                    <div class="booking-meta">
                        <div class="booking-meta-item">
                            <span>Date</span>
                            <strong>${booking.bookingDate}</strong>
                        </div>

                        <div class="booking-meta-item">
                            <span>Time</span>
                            <strong>${booking.startTime} - ${booking.endTime}</strong>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>
</main>

<!-- Including common website footer -->
<%@ include file="/includes/footer.jsp" %>

</body>
</html>
