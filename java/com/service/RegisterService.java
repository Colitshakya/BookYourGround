package com.service;

import com.dao.UserDAO;
import com.model.UserModel;
import com.util.PasswordUtil;

public class RegisterService {

    public void addUser(UserModel user) throws Exception {
        UserDAO dao = new UserDAO();

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() ||
            user.getLastName() == null || user.getLastName().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty() ||
            user.getPhone() == null || user.getPhone().trim().isEmpty() ||
            user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new Exception("All fields are required.");
        }

        if (!isValidPhone(user.getPhone())) {
            throw new Exception("Phone number must be exactly 10 digits.");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new Exception("Password must contain at least one uppercase letter, one number, and one special character.");
        }

        if (dao.isEmailExists(user.getEmail())) {
            throw new Exception("Email already exists.");
        }

        if (dao.isPhoneExists(user.getPhone())) {
            throw new Exception("Phone number already exists.");
        }

        String hashedPassword = PasswordUtil.getHashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        if (user.getStatus() == null || user.getStatus().isEmpty()) {
            user.setStatus("active");
        }
        user.setStatus("pending");
        user.setRole("user");
        dao.insertUser(user);
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$");
    }
}