package com.controller.servlet;

import com.model.ContactModel;
import com.service.ContactService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/contact")
public class ContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ContactController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ContactModel contact = new ContactModel();
            contact.setFullName(request.getParameter("fullName"));
            contact.setEmail(request.getParameter("email"));
            contact.setSubject(request.getParameter("subject"));
            contact.setMessage(request.getParameter("message"));

            request.setAttribute("fullName", contact.getFullName());
            request.setAttribute("email", contact.getEmail());
            request.setAttribute("subject", contact.getSubject());
            request.setAttribute("message", contact.getMessage());

            ContactService service = new ContactService();
            service.saveMessage(contact);

            request.setAttribute("successMessage", "Your message has been submitted successfully.");

            request.setAttribute("fullName", "");
            request.setAttribute("email", "");
            request.setAttribute("subject", "");
            request.setAttribute("message", "");

            request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);
        }
    }
}