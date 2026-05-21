<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!-- JSTL core tag library used for loops and conditions -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports - Book Your Ground</title>

<!-- Linking external CSS file for admin reports page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminReports.css">
</head>
<body>

<!-- Main admin layout wrapper -->
<div class="admin-layout">

    <!-- Including common admin sidebar navigation -->
    <%@ include file="/includes/adminSidebar.jsp" %>

    <!-- Main admin reports content -->
    <main class="admin-main">

        <!-- Admin page title section -->
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Reports & Analytics</h1>
        </div>

        <!-- Error message section displayed only when errorMessage exists -->
        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <!-- Top report summary cards -->
        <section class="report-stats-grid">
            <div class="report-stat-card">
                <h3>${report.totalUsers}</h3>
                <p>Total Users</p>
            </div>

            <div class="report-stat-card">
                <h3>${report.totalCourts}</h3>
                <p>Total Courts</p>
            </div>

            <div class="report-stat-card">
                <h3>${report.totalBookings}</h3>
                <p>Total Bookings</p>
            </div>

            <div class="report-stat-card">
                <h3>${report.activeCourts}</h3>
                <p>Active Courts</p>
            </div>
        </section>

        <!-- Reports and analytics grid -->
        <section class="report-grid">

            <!-- Booking status distribution report -->
            <div class="report-card">
                <div class="report-card-head">
                    <h2>Booking Status Distribution</h2>
                    <p>Booking counts by current status.</p>
                </div>

                <div class="progress-group">

                    <!-- Confirmed booking progress bar -->
                    <div class="progress-item">
                        <div class="progress-title-row">
                            <span>Confirmed</span>
                            <strong>${report.confirmedBookings}</strong>
                        </div>
                        <div class="progress-bar-wrap">
                            <div class="progress-bar confirmed-bar" style="width:${report.confirmedPercent}%;"></div>
                        </div>
                    </div>

                    <!-- Pending booking progress bar -->
                    <div class="progress-item">
                        <div class="progress-title-row">
                            <span>Pending</span>
                            <strong>${report.pendingBookings}</strong>
                        </div>
                        <div class="progress-bar-wrap">
                            <div class="progress-bar pending-bar" style="width:${report.pendingPercent}%;"></div>
                        </div>
                    </div>

                    <!-- Cancelled booking progress bar -->
                    <div class="progress-item">
                        <div class="progress-title-row">
                            <span>Cancelled</span>
                            <strong>${report.cancelledBookings}</strong>
                        </div>
                        <div class="progress-bar-wrap">
                            <div class="progress-bar cancelled-bar" style="width:${report.cancelledPercent}%;"></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Court availability report -->
            <div class="report-card">
                <div class="report-card-head">
                    <h2>Court Availability</h2>
                    <p>Current active and inactive courts.</p>
                </div>

                <div class="mini-report-list">
                    <div class="mini-report-box active-box">
                        <h3>${report.activeCourts}</h3>
                        <p>Active Courts</p>
                    </div>

                    <div class="mini-report-box inactive-box">
                        <h3>${report.inactiveCourts}</h3>
                        <p>Inactive Courts</p>
                    </div>
                </div>
            </div>

            <!-- Sport-wise booking analytics report -->
            <div class="report-card report-card-wide">
                <div class="report-card-head">
                    <h2>Most Booked Sports</h2>
                    <p>Sports ranked by booking activity.</p>
                </div>

                <div class="sport-chart-list">

                    <!-- Looping through sport report list and displaying sport booking bars -->
                    <c:forEach var="sport" items="${report.sportReportList}">
                        <div class="sport-chart-item">
                            <div class="sport-chart-head">
                                <span>${sport.sportName}</span>
                                <strong>${sport.bookingCount}</strong>
                            </div>
                            <div class="sport-chart-bar-wrap">
                                <div class="sport-chart-bar" style="width:${sport.percentage}%;"></div>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Empty message shown when sport analytics data is unavailable -->
                    <c:if test="${empty report.sportReportList}">
                        <p class="empty-report-text">No sport analytics available yet.</p>
                    </c:if>
                </div>
            </div>

            <!-- Quick summary report -->
            <div class="report-card">
                <div class="report-card-head">
                    <h2>Quick Summary</h2>
                    <p>Simple performance overview.</p>
                </div>

                <div class="summary-list">
                    <div class="summary-item">
                        <span>Total Users</span>
                        <strong>${report.totalUsers}</strong>
                    </div>
                    <div class="summary-item">
                        <span>Total Courts</span>
                        <strong>${report.totalCourts}</strong>
                    </div>
                    <div class="summary-item">
                        <span>Total Bookings</span>
                        <strong>${report.totalBookings}</strong>
                    </div>
                    <div class="summary-item">
                        <span>Confirmed Bookings</span>
                        <strong>${report.confirmedBookings}</strong>
                    </div>
                </div>
            </div>

        </section>
    </main>

</div>

</body>
</html>
