package com.service;

import com.dao.UserDAO;
import com.model.UserModel;
import com.util.PasswordUtil;

public class LoginService {

    public UserModel loginUser(String email, String password) throws Exception {
        UserDAO dao = new UserDAO();
        UserModel user = dao.getUserByEmail(email);

        if (user == null) {
            throw new Exception("Invalid email or password.");
        }

        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new Exception("Invalid email or password.");
        }

        if ("pending".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("Your account is waiting for admin approval.");
        }

        if ("inactive".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("Your account is currently inactive. Please contact admin.");
        }

        if ("deleted".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("This account is no longer available.");
        }

        if (!"active".equalsIgnoreCase(user.getStatus())) {
            throw new Exception("Your account cannot be used right now.");
        }

        return user;
    }
}