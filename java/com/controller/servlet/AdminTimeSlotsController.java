package com.controller.servlet;

import com.service.AdminTimeSlotService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/adminTimeSlots")
public class AdminTimeSlotsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminTimeSlotsController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdminTimeSlotService service = new AdminTimeSlotService();

            request.setAttribute("activePage", "timeslots");
            request.setAttribute("timeSlotList", service.getAllTimeSlots());

            request.getRequestDispatcher("/pages/adminTimeSlots.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "timeslots");
            request.setAttribute("errorMessage", "Unable to load time slots.");
            request.getRequestDispatcher("/pages/adminTimeSlots.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));

            AdminTimeSlotService service = new AdminTimeSlotService();

            if ("activate".equals(action)) {
                service.updateTimeSlotStatus(timeSlotId, "available");
            } else if ("deactivate".equals(action)) {
                service.updateTimeSlotStatus(timeSlotId, "inactive");
            }

            response.sendRedirect(request.getContextPath() + "/adminTimeSlots");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("activePage", "timeslots");
            request.setAttribute("errorMessage", "Unable to update time slot status.");
            doGet(request, response);
        }
    }
}