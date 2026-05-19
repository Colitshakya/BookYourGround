<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time Slot Management - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminTimeSlots.css">
</head>
<body>

<div class="admin-layout">

    <%@ include file="/includes/adminSidebar.jsp" %>

    <main class="admin-main">
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Time Slot Management</h1>
        </div>

        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <div class="admin-card">
            <div class="admin-card-head">
                <h2>Manage Time Slots</h2>
                <p>View and manage slot availability for all courts.</p>
            </div>

            <div class="admin-table-wrap">
                <table class="admin-timeslot-table">
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
                        <c:forEach var="slot" items="${timeSlotList}">
                            <tr>
                                <td>${slot.timeSlotId}</td>
                                <td>${slot.courtName}</td>
                                <td>${slot.sportName}</td>
                                <td>${slot.slotDate}</td>
                                <td>${slot.startTime}</td>
                                <td>${slot.endTime}</td>
                                <td>
                                    <span class="slot-status-badge ${slot.slotStatus}">
                                        ${slot.slotStatus}
                                    </span>
                                </td>
                                <td>
                                    <div class="table-action-group">

                                        <c:if test="${slot.slotStatus == 'available'}">
                                            <form action="${pageContext.request.contextPath}/adminTimeSlots" method="post">
                                                <input type="hidden" name="action" value="deactivate">
                                                <input type="hidden" name="timeSlotId" value="${slot.timeSlotId}">
                                                <button type="submit" class="table-btn deactivate-btn">Deactivate</button>
                                            </form>
                                        </c:if>

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