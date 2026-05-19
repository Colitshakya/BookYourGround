<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Court Management - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminCourts.css">
</head>
<body>

<div class="admin-layout">

    <%@ include file="/includes/adminSidebar.jsp" %>

    <main class="admin-main">
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Court Management</h1>
        </div>

        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <div class="admin-card">
            <div class="admin-card-head">
                <h2>Manage Courts</h2>
                <p>View and manage court availability and information.</p>
            </div>
            <div class="add-court-section">
    <h3>Add New Court</h3>

    <form action="${pageContext.request.contextPath}/adminCourts"
          method="post"
          enctype="multipart/form-data"
          class="add-court-form">

        <input type="hidden" name="action" value="addCourt">

        <div class="court-form-grid">
            <input type="text" name="courtName" placeholder="Court Name" required>
            <input type="text" name="courtNumber" placeholder="Court Number" required>
            <input type="number" name="courtCapacity" placeholder="Capacity" required>
            <input type="text" name="surfaceType" placeholder="Surface Type" required>
            <input type="number" step="0.01" name="pricePerHour" placeholder="Price Per Hour" required>

            <select name="courtStatus" required>
                <option value="">Select Status</option>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
            </select>

            <input type="number" name="venueId" placeholder="Venue ID" required>
            <input type="number" name="sportTypeId" placeholder="Sport Type ID" required>
            <input type="number" name="staffId" placeholder="Staff ID" required>

            <input type="file" name="courtImage" accept="image/*">
        </div>

        <button type="submit" class="add-court-btn">Add Court</button>
    </form>
</div>

            <div class="admin-table-wrap">
                <table class="admin-court-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Court Name</th>
                            <th>Court No.</th>
                            <th>Sport</th>
                            <th>Capacity</th>
                            <th>Surface</th>
                            <th>Price/Hour</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="court" items="${courtList}">
                            <tr>
                                <td>${court.courtId}</td>
                                <td>${court.courtName}</td>
                                <td>${court.courtNumber}</td>
                                <td>${court.sportName}</td>
                                <td>${court.courtCapacity}</td>
                                <td>${empty court.surfaceType ? '-' : court.surfaceType}</td>
                                <td>Rs. ${court.pricePerHour}</td>
                                <td>
                                    <span class="court-status-badge ${court.courtStatus}">
                                        ${court.courtStatus}
                                    </span>
                                </td>
                                <td>
                                    <div class="table-action-group">

                                        <c:if test="${court.courtStatus == 'active'}">
                                            <form action="${pageContext.request.contextPath}/adminCourts" method="post">
                                                <input type="hidden" name="action" value="deactivate">
                                                <input type="hidden" name="courtId" value="${court.courtId}">
                                                <button type="submit" class="table-btn deactivate-btn">Deactivate</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${court.courtStatus == 'inactive'}">
                                            <form action="${pageContext.request.contextPath}/adminCourts" method="post">
                                                <input type="hidden" name="action" value="activate">
                                                <input type="hidden" name="courtId" value="${court.courtId}">
                                                <button type="submit" class="table-btn activate-btn">Activate</button>
                                            </form>
                                        </c:if>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty courtList}">
                            <tr>
                                <td colspan="9" class="empty-row">No courts found.</td>
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