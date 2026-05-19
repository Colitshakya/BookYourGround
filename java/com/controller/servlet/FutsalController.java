package com.controller.servlet;

import com.service.PublicCourtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/futsal")
public class FutsalController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FutsalController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PublicCourtService service = new PublicCourtService();
            request.setAttribute("courtList", service.getCourtsBySport("Futsal"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load futsal courts.");
        }

        request.setAttribute("pageTitle", "Futsal Courts");
        request.getRequestDispatcher("/pages/futsal.jsp").forward(request, response);
    }
}