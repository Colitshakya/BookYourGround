<aside class="admin-sidebar">
    <div class="admin-sidebar-top">
        <a href="${pageContext.request.contextPath}/home" class="admin-logo">
            Book<span>YourGround</span>
        </a>
        <p class="admin-panel-text">Admin Panel</p>
    </div>

    <nav class="admin-side-nav">
        <a href="${pageContext.request.contextPath}/adminDashboard"
           class="admin-nav-link ${activePage == 'dashboard' ? 'active' : ''}">
            <span>Dashboard</span>
        </a>

        <a href="${pageContext.request.contextPath}/adminUsers"
           class="admin-nav-link ${activePage == 'users' ? 'active' : ''}">
            <span>Manage Users</span>
        </a>

        <a href="${pageContext.request.contextPath}/adminCourts"
           class="admin-nav-link ${activePage == 'courts' ? 'active' : ''}">
            <span>Court Management</span>
        </a>

        <a href="${pageContext.request.contextPath}/adminBookings"
           class="admin-nav-link ${activePage == 'bookings' ? 'active' : ''}">
            <span>Booking Management</span>
        </a>

        <a href="${pageContext.request.contextPath}/adminTimeSlots"
           class="admin-nav-link ${activePage == 'timeslots' ? 'active' : ''}">
            <span>Time Slot Management</span>
        </a>

        <a href="${pageContext.request.contextPath}/adminReports"
           class="admin-nav-link ${activePage == 'reports' ? 'active' : ''}">
            <span>Reports</span>
        </a>
    </nav>

    <div class="admin-sidebar-bottom">
        <a href="${pageContext.request.contextPath}/home" class="admin-side-btn admin-side-btn-outline">
            Back to Site
        </a>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="admin-side-btn admin-side-btn-danger">
                Logout
            </button>
        </form>
    </div>
</aside>