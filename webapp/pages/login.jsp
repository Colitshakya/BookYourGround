<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - Book Your Ground</title>

<!-- Linking external CSS file for login page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

<!-- Including common website header -->
<%@ include file="/includes/header.jsp" %>

<!-- Main login page content -->
<main class="login-page">
    <div class="container">
        <div class="login-shell">

            <!-- Left side welcome section -->
            <section class="login-left">
                <div class="left-overlay"></div>

                <div class="left-content">
                    <h1>Welcome Back!</h1>
                    <p>
                        Sign in to access your dashboard, view your bookings,
                        and find the perfect court for your next match.
                    </p>
                </div>
            </section>

            <!-- Right side login form section -->
            <section class="login-right">
                <h2>Login to Book Your Ground</h2>
                <p class="subtext">Enter your credentials to continue.</p>

                <!-- Error message section displayed only when errorMessage exists -->
                <div class="message error-message"
                     style="${empty errorMessage ? 'display:none;' : 'display:block;'}">
                    ${errorMessage}
                </div>

                <!-- Success message section displayed only when successMessage exists -->
                <div class="message success-message"
                     style="${empty successMessage ? 'display:none;' : 'display:block;'}">
                    ${successMessage}
                </div>

                <!-- Login form submitted to LoginController -->
                <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">

                    <!-- Email input field -->
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <div class="input-wrap">
                            <span class="input-icon">✉</span>
                            <input type="email" id="email" name="email"
                                   value="${email}" placeholder="name@example.com" required>
                        </div>
                    </div>

                    <!-- Password input field -->
                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="input-wrap">
                            <span class="input-icon">🔒︎</span>
                            <input type="password" id="password" name="password"
                                   placeholder="••••••••" required>
                        </div>
                    </div>

                    <!-- Login submit button -->
                    <button type="submit" class="sign-in-btn">Sign In</button>
                </form>

                <!-- Registration link for new users -->
                <p class="bottom-text">
                    Don’t have an account?
                    <a href="${pageContext.request.contextPath}/register">Create one</a>
                </p>
            </section>

        </div>
    </div>
</main>

<!-- Including common website footer -->
<%@ include file="/includes/footer.jsp" %>

</body>
</html>
