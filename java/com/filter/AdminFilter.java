package com.filter;

import com.model.UserModel;
import com.util.SessionUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/adminDashboard",
        "/adminUsers",
        "/adminCourts",
        "/adminBookings",
        "/adminTimeSlots",
        "/adminReports"
})
public class AdminFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"admin".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        chain.doFilter(request, response);
    }
}