<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Users - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminUsers.css">
</head>
<body>

<div class="admin-layout">

    <%@ include file="/includes/adminSidebar.jsp" %>

    <main class="admin-main">
        <div class="admin-topbar">
            <p class="admin-page-tag">ADMIN PANEL</p>
            <h1>Manage Users</h1>
        </div>

        <div class="admin-message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <div class="admin-card">
            <div class="admin-card-head">
                <h2>User Management</h2>
                <p>Approve, deactivate, and manage registered users.</p>
            </div>

            <div class="admin-table-wrap">
                <table class="admin-user-table">
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
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.userId}</td>
                                <td>${user.firstName} ${user.lastName}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>
                                <td>
                                    <span class="user-status-badge ${user.status}">
                                        ${user.status}
                                    </span>
                                </td>
                                <td>
                                    <div class="table-action-group">

                                        <c:if test="${user.status == 'pending'}">
                                            <form action="${pageContext.request.contextPath}/adminUsers" method="post">
                                                <input type="hidden" name="action" value="approve">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <button type="submit" class="table-btn approve-btn">Approve</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${user.status == 'active'}">
                                            <form action="${pageContext.request.contextPath}/adminUsers" method="post">
                                                <input type="hidden" name="action" value="deactivate">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <button type="submit" class="table-btn deactivate-btn">Deactivate</button>
                                            </form>
                                        </c:if>

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