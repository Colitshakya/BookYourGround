<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Futsal Courts</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/courts.css">
</head>
<body>

<%@ include file="/includes/header.jsp" %>

<main class="courts-page">
    <div class="courts-container">

        <section class="courts-hero">
            <p class="courts-hero-tag">BASKETBALL COURTS</p>
<h1>Choose your basketball court</h1>
<p class="courts-hero-text">Browse available basketball courts and book your next game.</p>
        </section>

        <div class="message error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <section class="courts-list-section">
            <div class="court-grid">
                <c:forEach var="court" items="${courtList}">
                    <div class="court-card">
                        <div class="court-card-image-wrap">
                            <img src="${empty court.imagePath or court.imagePath == '' ? pageContext.request.contextPath.concat('/photos/default-court.jpg') : pageContext.request.contextPath.concat('/photos/').concat(court.imagePath)}"
                                 alt="${court.courtName}">
                            <span class="court-sport-tag">${court.sportName}</span>
                        </div>

                        <div class="court-card-body">
                            <div class="court-card-top">
                                <h3>${court.courtName}</h3>
                                <span class="court-status-badge ${court.courtStatus}">${court.courtStatus}</span>
                            </div>

                            <p class="court-location">${empty court.venueName ? 'Location not set' : court.venueName}</p>
                            <p class="court-description">${empty court.surfaceType ? 'Well-maintained court.' : court.surfaceType}</p>

                            <div class="court-meta-grid">
                                <div class="court-meta-box">
                                    <span>Price</span>
                                    <strong>Rs. ${court.pricePerHour}/hr</strong>
                                </div>
                                <div class="court-meta-box">
                                    <span>Capacity</span>
                                    <strong>${court.courtCapacity} Players</strong>
                                </div>
                            </div>

                            <a href="${pageContext.request.contextPath}/courtDetails?courtId=${court.courtId}"
                               class="court-view-btn">View & Book</a>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty courtList}">
                    <div class="no-court-box">No futsal courts available right now.</div>
                </c:if>
            </div>
        </section>
    </div>
</main>

<%@ include file="/includes/footer.jsp" %>
</body>
</html>