<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile - Book Your Ground</title>

<!-- Linking external CSS file for user profile page styling -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userProfile.css">
</head>
<body>

<!-- Including common website header -->
<%@ include file="/includes/header.jsp" %>

<!-- Main user profile page content -->
<main class="profile-page">
    <div class="container">

        <!-- Profile hero section with avatar, user name, and booking summary -->
        <section class="profile-hero">
            <div class="profile-hero-left">

                <!-- Profile image upload and fallback avatar section -->
                <div class="profile-avatar-wrap hero-avatar">
                    <img class="profile-avatar-image"
                         src="${pageContext.request.contextPath}/getImage?name=${profileUser.userId}"
                         alt="Profile Photo"
                         onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                    <div class="profile-avatar-fallback">
                       ${empty profileUser.firstName ? 'U' : profileUser.firstName.substring(0,1)}
                    </div>
                </div>

                <!-- User profile heading and photo upload form -->
                <div class="hero-text">
                    <p class="profile-tag">MY ACCOUNT</p>
                    <h1>${profileUser.firstName} ${profileUser.lastName}</h1>
                    <p class="profile-subtext">Manage your personal details, password, and booking info.</p>

                    <form class="hero-photo-form" action="${pageContext.request.contextPath}/userProfile" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="uploadPhoto">

                        <div class="hero-upload-row">
                            <input type="file" id="profileImage" name="profileImage" accept="image/*" required>
                            <button type="submit" class="save-btn">Upload Photo</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Booking summary cards -->
            <div class="profile-hero-right">
                <div class="mini-stat">
                    <h3>${totalBookings}</h3>
                    <p>Total Bookings</p>
                </div>
                <div class="mini-stat">
                    <h3>${upcomingBookings}</h3>
                    <p>Upcoming</p>
                </div>
                <a href="${pageContext.request.contextPath}/myBookings" class="mini-stat mini-stat-link">
                    <h3>📆</h3>
                    <p>View Bookings ➜</p>
                </a>
            </div>
        </section>

        <!-- Profile information, summary, password, and account actions grid -->
        <section class="profile-grid">

            <!-- Personal information update card -->
            <div class="profile-card large-card">
                <div class="card-head">
                    <h2>Personal Information</h2>
                    <p>Update your account details below.</p>
                </div>

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

                <!-- Profile update form -->
                <form class="profile-form" action="${pageContext.request.contextPath}/userProfile" method="post">
                    <input type="hidden" name="action" value="updateProfile">

                    <div class="form-grid">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="first_name" value="${profileUser.firstName}" required>
                        </div>

                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input type="text" id="lastName" name="last_name" value="${profileUser.lastName}" required>
                        </div>
                    </div>

                    <div class="form-grid">
                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" id="email" name="email" value="${profileUser.email}" required>
                        </div>

                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="text" id="phone" name="phone" value="${profileUser.phone}" required>
                        </div>
                    </div>

                    <button type="submit" class="save-btn">Save Changes</button>
                </form>
            </div>

            <!-- Profile summary card -->
            <div class="profile-card side-card">
                <div class="card-head">
                    <h2>Profile Summary</h2>
                </div>

                <div class="summary-list">
                    <div class="summary-item">
                        <span>Account Status</span>
                        <strong class="status-active">${profileUser.status}</strong>
                    </div>
                   
                    <div class="summary-item">
                        <span>Email</span>
                        <strong>${profileUser.email}</strong>
                    </div>
                    <div class="summary-item">
                        <span>Phone</span>
                        <strong>${profileUser.phone}</strong>
                    </div>
                    <div class="summary-item">
                        <span>User ID</span>
                        <strong>${profileUser.userId}</strong>
                    </div>
                </div>
            </div>

            <!-- Change password card -->
            <div class="profile-card large-card">
                <div class="card-head">
                    <h2>Change Password</h2>
                    <p>Keep your account secure.</p>
                </div>

                <!-- Password change form -->
                <form class="profile-form" action="${pageContext.request.contextPath}/userProfile" method="post">
                    <input type="hidden" name="action" value="changePassword">

                    <div class="form-group">
                        <label for="currentPassword">Current Password</label>
                        <input type="password" id="currentPassword" name="current_password" placeholder="Enter current password" required>
                    </div>

                    <div class="form-grid">
                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="new_password" placeholder="Enter new password" required>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password</label>
                            <input type="password" id="confirmPassword" name="confirm_password" placeholder="Confirm new password" required>
                        </div>
                    </div>

                    <button type="submit" class="save-btn">Update Password</button>
                </form>
            </div>

            <!-- Account action card -->
            <div class="profile-card side-card">
                <div class="card-head">
                    <h2>Account Actions</h2>
                    <p>Manage your session and account access.</p>
                </div>
                
                <!-- Delete account error message section -->
                <div class="message error-message"
                     style="${empty deleteErrorMessage ? 'display:none;' : 'display:block;'}">
                    ${deleteErrorMessage}
                </div>

                <!-- Logout and delete account actions -->
                <div class="account-action-list">
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button type="submit" class="account-action-btn logout-btn">
                            Logout
                        </button>
                    </form>

                    <form action="${pageContext.request.contextPath}/userProfile" method="post"
                          onsubmit="return confirm('Are you sure you want to delete your account?');">
                        <input type="hidden" name="action" value="deleteAccount">
                        <button type="submit" class="account-action-btn delete-btn">
                            Delete Your Account
                        </button>
                    </form>
                </div>
            </div>

        </section>

    </div>
</main>

<!-- Including common website footer -->
<%@ include file="/includes/footer.jsp" %>

</body>
</html>
