package com.controller.servlet;

import com.model.AdminReportModel;
import com.service.AdminReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/adminReports")
public class AdminReportsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminReportsController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdminReportService service = new AdminReportService();
            AdminReportModel report = service.getReportData();

            request.setAttribute("activePage", "reports");
            request.setAttribute("report", report);

            request.getRequestDispatcher("/pages/adminReports.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "reports");
            request.setAttribute("errorMessage", "Unable to load reports.");
            request.getRequestDispatcher("/pages/adminReports.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}