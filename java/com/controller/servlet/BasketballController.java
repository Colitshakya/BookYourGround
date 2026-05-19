package com.controller.servlet;

import com.service.PublicCourtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/basketball")
public class BasketballController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BasketballController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PublicCourtService service = new PublicCourtService();
            request.setAttribute("courtList", service.getCourtsBySport("Basketball"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load basketball courts.");
        }

        request.setAttribute("pageTitle", "Basketball Courts");
        request.getRequestDispatcher("/pages/basketball.jsp").forward(request, response);
    }
}