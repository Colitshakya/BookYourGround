package com.controller.servlet;

import com.model.UserModel;
import com.service.LoginService;
import com.util.CookieUtil;
import com.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String registered = request.getParameter("registered");

        if ("pending".equalsIgnoreCase(registered)) {
            request.setAttribute("successMessage",
                    "Registration successful. Please wait for admin approval before logging in.");
        }

        request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            request.setAttribute("email", email);

            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Email is required.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Password is required.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            }

            LoginService service = new LoginService();
            UserModel user = service.loginUser(email, password);

            if (user != null) {
                SessionUtil.setAttribute(request, "loggedInUser", user, 3600);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                String loginTime = now.format(formatter);

                CookieUtil.addCookie(response, "last_login", loginTime, 3600);

                if ("admin".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/adminDashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
}