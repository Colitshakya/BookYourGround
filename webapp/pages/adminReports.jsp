<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminReports.css">
</head>
<body>

<div class="admin-layout">

    <%@ include file="/includes/adminSidebar.jsp" %>

    <main class="admin-main">
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Reports & Analytics</h1>
        </div>

        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

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

        <section class="report-grid">

            <div class="report-card">
                <div class="report-card-head">
                    <h2>Booking Status Distribution</h2>
                    <p>Booking counts by current status.</p>
                </div>

                <div class="progress-group">
                    <div class="progress-item">
                        <div class="progress-title-row">
                            <span>Confirmed</span>
                            <strong>${report.confirmedBookings}</strong>
                        </div>
                        <div class="progress-bar-wrap">
                            <div class="progress-bar confirmed-bar" style="width:${report.confirmedPercent}%;"></div>
                        </div>
                    </div>

                    <div class="progress-item">
                        <div class="progress-title-row">
                            <span>Pending</span>
                            <strong>${report.pendingBookings}</strong>
                        </div>
                        <div class="progress-bar-wrap">
                            <div class="progress-bar pending-bar" style="width:${report.pendingPercent}%;"></div>
                        </div>
                    </div>

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

            <div class="report-card report-card-wide">
                <div class="report-card-head">
                    <h2>Most Booked Sports</h2>
                    <p>Sports ranked by booking activity.</p>
                </div>

                <div class="sport-chart-list">
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

                    <c:if test="${empty report.sportReportList}">
                        <p class="empty-report-text">No sport analytics available yet.</p>
                    </c:if>
                </div>
            </div>

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