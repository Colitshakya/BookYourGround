<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!-- JSTL core tag library used for loops and conditions -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard - Book Your Ground</title>

<!-- Linking external CSS file for admin dashboard styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminDashboard.css">
</head>
<body>

<!-- Main admin layout wrapper -->
<div class="admin-layout">

    <!-- Admin sidebar navigation section -->
    <aside class="admin-sidebar">
        <div class="admin-sidebar-top">
            <a href="${pageContext.request.contextPath}/home" class="admin-logo">
                Book<span>YourGround</span>
            </a>
            <p class="admin-panel-text">Admin Panel</p>
        </div>

        <!-- Admin navigation links -->
        <nav class="admin-side-nav">
            <a href="${pageContext.request.contextPath}/adminDashboard" class="admin-nav-link active">
                <span>Dashboard</span>
            </a>
            <a href="${pageContext.request.contextPath}/adminUsers" class="admin-nav-link">
                <span>Manage Users</span>
            </a>
            <a href="${pageContext.request.contextPath}/adminCourts" class="admin-nav-link">
                <span>Court Management</span>
            </a>
            <a href="${pageContext.request.contextPath}/adminBookings" class="admin-nav-link">
                <span>Booking Management</span>
            </a>
            <a href="${pageContext.request.contextPath}/adminTimeSlots" class="admin-nav-link">
                <span>Time Slot Management</span>
            </a>
            <a href="${pageContext.request.contextPath}/adminReports" class="admin-nav-link">
                <span>Reports</span>
            </a>
        </nav>

        <!-- Sidebar bottom buttons -->
        <div class="admin-sidebar-bottom">
            <a href="${pageContext.request.contextPath}/home" class="admin-side-btn admin-side-btn-outline">
                Back to Site
            </a>

            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit" class="admin-side-btn admin-side-btn-danger">
                    Logout
                </button>
            </form>
        </div>
    </aside>

    <!-- Main admin dashboard content -->
    <main class="admin-main">

        <!-- Dashboard heading section -->
        <div class="admin-topbar">
            <div>
                <p class="admin-page-tag">ADMIN DASHBOARD</p>
                <h1>Welcome Back, Admin</h1>
            </div>
        </div>

        <!-- Error message section displayed only when errorMessage exists -->
        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <!-- Dashboard statistics cards -->
        <section class="admin-stats-grid">
            <div class="admin-stat-card">
                <h3>${dashboard.totalUsers}</h3>
                <p>Total Users</p>
            </div>

            <div class="admin-stat-card">
                <h3>${dashboard.totalCourts}</h3>
                <p>Total Courts</p>
            </div>

            <div class="admin-stat-card">
                <h3>${dashboard.totalBookings}</h3>
                <p>Total Bookings</p>
            </div>

            <div class="admin-stat-card">
                <h3>${dashboard.pendingUsers}</h3>
                <p>Pending Approvals</p>
            </div>
        </section>

        <!-- Dashboard lower content section -->
        <section class="admin-content-grid">

            <!-- Recent booking activity card -->
            <div class="admin-card admin-card-large">
                <div class="admin-card-head">
                    <h2>Recent Bookings</h2>
                    <p>Latest booking activity in the system.</p>
                </div>

                <div class="admin-booking-list">

                    <!-- Empty message shown when there are no recent bookings -->
                    <div class="admin-empty-message"
                         style="${empty dashboard.recentBookings ? 'display:block;' : 'display:none;'}">
                        No recent bookings found.
                    </div>

                    <!-- Recent bookings list shown when booking data exists -->
                    <div style="${empty dashboard.recentBookings ? 'display:none;' : 'display:block;'}">
                        <c:forEach var="booking" items="${dashboard.recentBookings}">
                            <div class="admin-booking-row">
                                <div class="admin-booking-col admin-booking-court">
                                    <strong>${booking.courtName}</strong>
                                </div>

                                <div class="admin-booking-col">
                                    <span class="admin-booking-label">User</span>
                                    <span>${booking.userFullName}</span>
                                </div>

                                <div class="admin-booking-col">
                                    <span class="admin-booking-label">Sport</span>
                                    <span>${booking.sportName}</span>
                                </div>

                                <div class="admin-booking-col">
                                    <span class="admin-booking-label">Date</span>
                                    <span>${booking.bookingDate}</span>
                                </div>

                                <div class="admin-booking-col">
                                    <span class="admin-booking-label">Time</span>
                                    <span>${booking.startTime} - ${booking.endTime}</span>
                                </div>

                                <div class="admin-booking-col admin-booking-status-col">
                                    <span class="admin-booking-status ${booking.bookingStatus}">
                                        ${booking.bookingStatus}
                                    </span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                </div>
            </div>

            <!-- System summary card -->
            <div class="admin-card">
                <div class="admin-card-head">
                    <h2>System Summary</h2>
                    <p>Overview of admin activities.</p>
                </div>

                <div class="admin-summary-list">
                    <div class="admin-summary-item">
                        <span>Platform Status</span>
                        <strong>Active</strong>
                    </div>
                    <div class="admin-summary-item">
                        <span>User Requests</span>
                        <strong>${dashboard.pendingUsers} Pending</strong>
                    </div>
                    <div class="admin-summary-item">
                        <span>Total Members</span>
                        <strong>${dashboard.totalUsers}</strong>
                    </div>
                    <div class="admin-summary-item">
                        <span>Available Courts</span>
                        <strong>${dashboard.totalCourts}</strong>
                    </div>
                </div>
            </div>

        </section>
    </main>

</div>

</body>
</html>
