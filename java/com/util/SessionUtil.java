package com.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static void setAttribute(HttpServletRequest request, String name, Object value, int seconds) {
        HttpSession session = request.getSession(true);
        session.setAttribute(name, value);
        session.setMaxInactiveInterval(seconds);
    }

    public static Object getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getAttribute(name) : null;
    }

    public static void removeAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(name);
        }
    }

    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}