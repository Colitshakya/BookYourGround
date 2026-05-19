<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Booking Management - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminBookings.css">
</head>
<body>

<div class="admin-layout">

    <%@ include file="/includes/adminSidebar.jsp" %>

    <main class="admin-main">
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Booking Management</h1>
        </div>

        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <div class="admin-card">
            <div class="admin-card-head">
                <h2>Manage Bookings</h2>
                <p>View and control all booking records in the system.</p>
            </div>

            <div class="admin-table-wrap">
                <table class="admin-booking-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>User</th>
                            <th>Court</th>
                            <th>Sport</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${bookingList}">
                            <tr>
                                <td>${booking.bookingId}</td>
                                <td>${booking.userFullName}</td>
                                <td>${booking.courtName}</td>
                                <td>${booking.sportName}</td>
                                <td>${booking.bookingDate}</td>
                                <td>${booking.startTime} - ${booking.endTime}</td>
                                <td>
                                    <span class="booking-status-badge ${booking.bookingStatus}">
                                        ${booking.bookingStatus}
                                    </span>
                                </td>
                                <td>
                                    <div class="table-action-group">

                                        <c:if test="${booking.bookingStatus == 'pending'}">
                                            <form action="${pageContext.request.contextPath}/adminBookings" method="post">
                                                <input type="hidden" name="action" value="confirm">
                                                <input type="hidden" name="bookingId" value="${booking.bookingId}">
                                                <button type="submit" class="table-btn confirm-btn">Confirm</button>
                                            </form>

                                            <form action="${pageContext.request.contextPath}/adminBookings" method="post">
                                                <input type="hidden" name="action" value="cancel">
                                                <input type="hidden" name="bookingId" value="${booking.bookingId}">
                                                <button type="submit" class="table-btn cancel-btn">Cancel</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${booking.bookingStatus == 'confirmed'}">
                                            <form action="${pageContext.request.contextPath}/adminBookings" method="post">
                                                <input type="hidden" name="action" value="cancel">
                                                <input type="hidden" name="bookingId" value="${booking.bookingId}">
                                                <button type="submit" class="table-btn cancel-btn">Cancel</button>
                                            </form>
                                        </c:if>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty bookingList}">
                            <tr>
                                <td colspan="8" class="empty-row">No bookings found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

</div>

</body>
</html>