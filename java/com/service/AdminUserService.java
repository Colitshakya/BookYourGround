package com.service;

import com.dao.UserDAO;
import com.model.UserModel;

import java.util.List;

public class AdminUserService {

    public List<UserModel> getAllUsers() throws Exception {
        UserDAO dao = new UserDAO();
        return dao.getAllUsers();
    }

    public void updateUserStatus(int userId, String status) throws Exception {
        UserDAO dao = new UserDAO();
        dao.updateUserStatus(userId, status);
    }
}