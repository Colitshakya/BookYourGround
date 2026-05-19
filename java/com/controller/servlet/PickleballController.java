package com.controller.servlet;

import com.service.PublicCourtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pickleball")
public class PickleballController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PickleballController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PublicCourtService service = new PublicCourtService();
            request.setAttribute("courtList", service.getCourtsBySport("Pickleball"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load pickleball courts.");
        }

        request.setAttribute("pageTitle", "Pickleball Courts");
        request.getRequestDispatcher("/pages/pickleball.jsp").forward(request, response);
    }
}