package com.controller.servlet;

import com.service.PublicCourtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/tennis")
public class TennisController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TennisController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PublicCourtService service = new PublicCourtService();
            request.setAttribute("courtList", service.getCourtsBySport("Tennis"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load tennis courts.");
        }

        request.setAttribute("pageTitle", "Tennis Courts");
        request.getRequestDispatcher("/pages/tennis.jsp").forward(request, response);
    }
}