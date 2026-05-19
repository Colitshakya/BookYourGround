package com.controller.servlet;

import com.service.AdminUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/adminUsers")
public class AdminUsersController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminUsersController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdminUserService service = new AdminUserService();

            request.setAttribute("activePage", "users");
            request.setAttribute("userList", service.getAllUsers());

            request.getRequestDispatcher("/pages/adminUsers.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "users");
            request.setAttribute("errorMessage", "Unable to load users.");
            request.getRequestDispatcher("/pages/adminUsers.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            int userId = Integer.parseInt(request.getParameter("userId"));

            AdminUserService service = new AdminUserService();

            if ("approve".equals(action)) {
                service.updateUserStatus(userId, "active");
            } else if ("deactivate".equals(action)) {
                service.updateUserStatus(userId, "inactive");
            } else if ("reactivate".equals(action)) {
                service.updateUserStatus(userId, "active");
            }

            response.sendRedirect(request.getContextPath() + "/adminUsers");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "users");
            request.setAttribute("errorMessage", "Unable to update user status.");
            doGet(request, response);
        }
    }
}