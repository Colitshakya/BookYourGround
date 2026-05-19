package com.dao;

import com.model.ContactModel;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContactDAO {

    public void insertMessage(ContactModel contact) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO contact_message (full_name, email, subject, message) VALUES (?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, contact.getFullName());
        pst.setString(2, contact.getEmail());
        pst.setString(3, contact.getSubject());
        pst.setString(4, contact.getMessage());

        pst.executeUpdate();

        pst.close();
        con.close();
    }
}