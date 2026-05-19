package com.filter;

import com.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {
	    "/userProfile",
	    "/bookCourt",
	    "/payment",
	    "/logout",
	    "/admin-dashboard"
	})
public class AuthenticationFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    public AuthenticationFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        boolean isLoggedIn = SessionUtil.getAttribute(httpRequest, "loggedInUser") != null;

        if (isLoggedIn) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}