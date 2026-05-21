<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Us - Book Your Ground</title>

<!-- Linking external CSS file for About page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/about.css">
</head>
<body>

<!-- Including common website header -->
<%@ include file="/includes/header.jsp" %>

<!-- Main About page content -->
<main class="about-page">
    <div class="about-container">

        <!-- About page hero section -->
        <section class="about-hero">
            <p class="about-tag">ABOUT US</p>
            <h1>Meet Our Team</h1>
            <p class="about-text">
                Book Your Ground is a sports court booking platform created to make it easy for users
                to discover courts, check time slots, and book their favorite games online.
                This project was developed by our team members below.
            </p>
        </section>

        <!-- Purpose and system description section -->
        <section class="about-mission">
            <div class="about-card">
                <h2>Our Purpose</h2>
                <p>
                    Our goal is to provide a simple and user-friendly platform where players can
                    explore available sports courts, choose their preferred time slots, and confirm
                    bookings without hassle.
                </p>
            </div>

            <div class="about-card">
                <h2>What We Built</h2>
                <p>
                    The system supports multiple sports such as futsal, basketball, tennis, and
                    pickleball, along with user registration, admin approval, slot selection,
                    booking management, and court management features.
                </p>
            </div>
        </section>

        <!-- Team members section -->
        <section class="team-section">
            <div class="section-heading">
                <p class="small-tag">TEAM MEMBERS</p>
                <h2>The People Behind the Project</h2>
            </div>

            <!-- Team member cards -->
            <div class="team-grid">
                <div class="team-card">
                    <div class="team-photo-wrap">
                        <img src="${pageContext.request.contextPath}/photos/colit.jpeg" alt="Colit Shakya" class="team-photo">
                    </div>
                    <h3>Colit Shakya</h3>
                    <p>Team Leader</p>
                </div>

                <div class="team-card">
                    <div class="team-photo-wrap">
                        <img src="${pageContext.request.contextPath}/photos/amman.jpeg" alt="Amman Uddin" class="team-photo">
                    </div>
                    <h3>Amman Uddin</h3>
                    <p>Team Member</p>
                </div>

                <div class="team-card">
                    <div class="team-photo-wrap">
                        <img src="${pageContext.request.contextPath}/photos/ajar.jpeg" alt="Ajar Shrestha" class="team-photo">
                    </div>
                    <h3>Ajar Shrestha</h3>
                    <p>Team Member</p>
                </div>

                <div class="team-card">
                    <div class="team-photo-wrap">
                        <img src="${pageContext.request.contextPath}/photos/anda.jpeg" alt="Shreyansh Bhandari" class="team-photo">
                    </div>
                    <h3>Shreyansh Bhandari</h3>
                    <p>Team Member</p>
                </div>

                <div class="team-card">
                    <div class="team-photo-wrap">
                        <img src="${pageContext.request.contextPath}/photos/anugya1.jpeg" alt="Anugya Manandhar" class="team-photo">
                    </div>
                    <h3>Anugya Manandhar</h3>
                    <p>Team Member</p>
                </div>
            </div>
        </section>

    </div>
</main>

<!-- Including common website footer -->
<%@ include file="/includes/footer.jsp" %>

</body>
</html>
