<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!-- JSTL core tag library used for loops and conditions -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time Slot Management - Book Your Ground</title>

<!-- Linking external CSS file for admin time slot page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminTimeSlots.css">
</head>
<body>

<!-- Main admin layout wrapper -->
<div class="admin-layout">

    <!-- Including common admin sidebar navigation -->
    <%@ include file="/includes/adminSidebar.jsp" %>

    <!-- Main admin time slot management content -->
    <main class="admin-main">

        <!-- Admin page title section -->
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Time Slot Management</h1>
        </div>

        <!-- Error message section displayed only when errorMessage exists -->
        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <!-- Time slot management card -->
        <div class="admin-card">
            <div class="admin-card-head">
                <h2>Manage Time Slots</h2>
                <p>View and manage slot availability for all courts.</p>
            </div>

            <!-- Time slot table section -->
            <div class="admin-table-wrap">
                <table class="admin-timeslot-table">

                    <!-- Time slot table headings -->
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Court</th>
                            <th>Sport</th>
                            <th>Date</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <!-- Looping through timeSlotList and displaying each time slot record -->
                        <c:forEach var="slot" items="${timeSlotList}">
                            <tr>
                                <td>${slot.timeSlotId}</td>
                                <td>${slot.courtName}</td>
                                <td>${slot.sportName}</td>
                                <td>${slot.slotDate}</td>
                                <td>${slot.startTime}</td>
                                <td>${slot.endTime}</td>

                                <!-- Time slot status badge -->
                                <td>
                                    <span class="slot-status-badge ${slot.slotStatus}">
                                        ${slot.slotStatus}
                                    </span>
                                </td>

                                <!-- Time slot action buttons -->
                                <td>
                                    <div class="table-action-group">

                                        <!-- Action shown for available time slots -->
                                        <c:if test="${slot.slotStatus == 'available'}">
                                            <form action="${pageContext.request.contextPath}/adminTimeSlots" method="post">
                                                <input type="hidden" name="action" value="deactivate">
                                                <input type="hidden" name="timeSlotId" value="${slot.timeSlotId}">
                                                <button type="submit" class="table-btn deactivate-btn">Deactivate</button>
                                            </form>
                                        </c:if>

                                        <!-- Action shown for inactive time slots -->
                                        <c:if test="${slot.slotStatus == 'inactive'}">
                                            <form action="${pageContext.request.contextPath}/adminTimeSlots" method="post">
                                                <input type="hidden" name="action" value="activate">
                                                <input type="hidden" name="timeSlotId" value="${slot.timeSlotId}">
                                                <button type="submit" class="table-btn activate-btn">Activate</button>
                                            </form>
                                        </c:if>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        <!-- Empty message shown when no time slot records are available -->
                        <c:if test="${empty timeSlotList}">
                            <tr>
                                <td colspan="8" class="empty-row">No time slots found.</td>
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
