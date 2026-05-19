package com.controller.servlet;

import com.model.UserModel;
import com.service.UserProfileService;
import com.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/myBookings")
public class MyBookingsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyBookingsController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            UserModel loggedInUser = (UserModel) SessionUtil.getAttribute(request, "loggedInUser");

            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            UserProfileService service = new UserProfileService();
            request.setAttribute("bookingHistoryList", service.getUserBookings(loggedInUser.getUserId()));

            request.getRequestDispatcher("/pages/myBookings.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load bookings.");
            request.getRequestDispatcher("/pages/myBookings.jsp").forward(request, response);
        }
    }
}