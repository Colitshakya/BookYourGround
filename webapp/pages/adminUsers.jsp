<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!-- JSTL core tag library used for loops and conditions -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Users - Book Your Ground</title>

<!-- Linking external CSS file for admin users page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminUsers.css">
</head>
<body>

<!-- Main admin layout wrapper -->
<div class="admin-layout">

    <!-- Including common admin sidebar navigation -->
    <%@ include file="/includes/adminSidebar.jsp" %>

    <!-- Main admin user management content -->
    <main class="admin-main">

        <!-- Admin page title section -->
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Manage Users</h1>
        </div>

        <!-- Error message section displayed only when errorMessage exists -->
        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <!-- User management card -->
        <div class="admin-card">
            <div class="admin-card-head">
                <h2>User Management</h2>
                <p>Approve, deactivate, and manage registered users.</p>
            </div>

            <!-- User table section -->
            <div class="admin-table-wrap">
                <table class="admin-user-table">

                    <!-- User table headings -->
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <!-- Looping through userList and displaying each user record -->
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.userId}</td>
                                <td>${user.firstName} ${user.lastName}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>

                                <!-- User status badge -->
                                <td>
                                    <span class="user-status-badge ${user.status}">
                                        ${user.status}
                                    </span>
                                </td>

                                <!-- User action buttons -->
                                <td>
                                    <div class="table-action-group">

                                        <!-- Action shown for pending users -->
                                        <c:if test="${user.status == 'pending'}">
                                            <form action="${pageContext.request.contextPath}/adminUsers" method="post">
                                                <input type="hidden" name="action" value="approve">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <button type="submit" class="table-btn approve-btn">Approve</button>
                                            </form>
                                        </c:if>

                                        <!-- Action shown for active users -->
                                        <c:if test="${user.status == 'active'}">
                                            <form action="${pageContext.request.contextPath}/adminUsers" method="post">
                                                <input type="hidden" name="action" value="deactivate">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <button type="submit" class="table-btn deactivate-btn">Deactivate</button>
                                            </form>
                                        </c:if>

                                        <!-- Action shown for inactive users -->
                                        <c:if test="${user.status == 'inactive'}">
                                            <form action="${pageContext.request.contextPath}/adminUsers" method="post">
                                                <input type="hidden" name="action" value="reactivate">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <button type="submit" class="table-btn approve-btn">Reactivate</button>
                                            </form>
                                        </c:if>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        <!-- Empty message shown when no user records are available -->
                        <c:if test="${empty userList}">
                            <tr>
                                <td colspan="6" class="empty-row">No users found.</td>
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
