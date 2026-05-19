package com.controller.servlet;

import com.model.AdminModel;
import com.service.AdminDashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/adminDashboard")
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminDashboardController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdminDashboardService service = new AdminDashboardService();
            AdminModel dashboard = service.getDashboardData();

            request.setAttribute("activePage", "dashboard");
            request.setAttribute("dashboard", dashboard);

            request.getRequestDispatcher("/pages/adminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "dashboard");
            request.setAttribute("errorMessage", "Unable to load admin dashboard.");
            request.getRequestDispatcher("/pages/adminDashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}