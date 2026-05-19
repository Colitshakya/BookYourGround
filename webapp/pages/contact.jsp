<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contact Us - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contact.css">
</head>
<body>

<%@ include file="/includes/header.jsp" %>

<main class="contact-page">
    <div class="contact-container">

        <section class="contact-hero">
            <p class="contact-tag">CONTACT US</p>
            <h1>Get In Touch</h1>
            <p class="contact-text">
                Have questions, feedback, or need help with booking? Fill out the form below
                and we will get back to you as soon as possible.
            </p>
        </section>

        <div class="success-message"
             style="${empty successMessage ? 'display:none;' : 'display:block;'}">
            ${successMessage}
        </div>

        <div class="error-message"
             style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
            ${errorMessage}
        </div>

        <section class="contact-content">
            <div class="contact-info-card">
                <h2>Contact Information</h2>
                <p>
                    We are here to help you with court booking, account issues, and general inquiries.
                </p>

                <div class="contact-info-box">
                    <h3>Email</h3>
                    <p>support@bookyourground.com</p>
                </div>

                <div class="contact-info-box">
                    <h3>Phone</h3>
                    <p>+977 9821567256</p>
                </div>

                <div class="contact-info-box">
                    <h3>Location</h3>
                    <p>Kathmandu, Nepal</p>
                </div>
            </div>

            <div class="contact-form-card">
                <h2>Send a Message</h2>

                <form action="${pageContext.request.contextPath}/contact" method="post" class="contact-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="fullName">Full Name</label>
                            <input type="text" id="fullName" name="fullName"
                                   value="${fullName}" placeholder="Enter your full name">
                        </div>

                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" id="email" name="email"
                                   value="${email}" placeholder="Enter your email">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="subject">Subject</label>
                        <input type="text" id="subject" name="subject"
                               value="${subject}" placeholder="Enter subject">
                    </div>

                    <div class="form-group">
                        <label for="message">Message</label>
                        <textarea id="message" name="message" rows="7"
                                  placeholder="Write your message here...">${message}</textarea>
                    </div>

                    <button type="submit" class="contact-btn">Send Message</button>
                </form>
            </div>
        </section>

    </div>
</main>

<%@ include file="/includes/footer.jsp" %>

</body>
</html>