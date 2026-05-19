package com.filter;

import com.util.SessionUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/login", "/register"})
public class GuestFilter extends HttpFilter {

    private static final long serialVersionUID = 1L;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        boolean isLoggedIn = SessionUtil.getAttribute(httpRequest, "loggedInUser") != null;

        if (isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
        } else {
            chain.doFilter(request, response);
        }
    }
}