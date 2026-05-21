<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up - Book Your Ground</title>

<!-- Linking external CSS file for register page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>

<!-- Including common website header -->
<%@ include file="/includes/header.jsp" %>

<!-- Main register page content -->
<main class="register-page">
    <div class="container">
        <div class="register-shell">

            <!-- Left side promotional section -->
            <section class="register-left">
                <div class="left-overlay"></div>

                <div class="left-content">
                    <h1>Join Book Your Ground</h1>
                    <p>
                        Create your account to explore courts, view available time slots,
                        and book your next match in minutes.
                    </p>
                </div>
            </section>

            <!-- Right side registration form section -->
            <section class="register-right">
                <div class="tag">CREATE ACCOUNT</div>

                <h2>Register to Book Your Ground</h2>
                <p class="subtext">Fill in your details to get started.</p>

                <!-- Error message section displayed only when errorMessage exists -->
                <div class="message error-message"
                     style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
                    ${errorMessage}
                </div>

                <!-- Success message section displayed only when successMessage exists -->
                <div class="message success-message"
                     style="${empty successMessage ? 'display:none;' : 'display:block;'}">
                    ${successMessage}
                    
                    <a href="${pageContext.request.contextPath}/login" class="message-link">Go to Login</a>
                </div>

                <!-- Registration form submitted to RegisterController -->
                <form action="${pageContext.request.contextPath}/register" method="post" class="register-form">

                    <!-- First name and last name input row -->
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <div class="input-wrap">
                                <span class="input-icon">👤</span>
                                <input type="text" id="firstName" name="first_name"
                                       value="${firstName}" placeholder="First name" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <div class="input-wrap">
                                <span class="input-icon">👤</span>
                                <input type="text" id="lastName" name="last_name"
                                       value="${lastName}" placeholder="Last name" required>
                            </div>
                        </div>
                    </div>

                    <!-- Email input field -->
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <div class="input-wrap">
                            <span class="input-icon">✉</span>
                            <input type="email" id="email" name="email"
                                   value="${email}" placeholder="name@example.com" required>
                        </div>
                    </div>

                    <!-- Phone number input field -->
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <div class="input-wrap">
                            <span class="input-icon">☎</span>
                            <input type="text" id="phone" name="phone"
                                   value="${phone}" placeholder="+977 98XXXXXXXX" required>
                        </div>
                    </div>

                    <!-- Password and confirm password input row -->
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="password">Password</label>
                            <div class="input-wrap">
                                <span class="input-icon">🔒︎</span>
                                <input type="password" id="password" name="password"
                                       placeholder="••••••••" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password</label>
                            <div class="input-wrap">
                                <span class="input-icon">🔒︎</span>
                                <input type="password" id="confirmPassword" name="confirm_password"
                                       placeholder="••••••••" required>
                            </div>
                        </div>
                    </div>

                    <!-- Terms and conditions checkbox -->
                    <label class="agree-box">
                        <input type="checkbox" name="agree" required>
                        <span>I agree to the Terms and Conditions</span>
                    </label>

                    <!-- Register submit button -->
                    <button type="submit" class="register-btn">Create Account</button>
                </form>

                <!-- Login link for existing users -->
                <p class="bottom-text">
                    Already have an account?
                    <a href="${pageContext.request.contextPath}/login">Log In</a>
                </p>
            </section>

        </div>
    </div>
</main>

<!-- Including common website footer -->
<%@ include file="/includes/footer.jsp" %>
</body>
</html>
