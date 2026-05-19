package com.service;

import com.dao.ContactDAO;
import com.model.ContactModel;

public class ContactService {

    public void saveMessage(ContactModel contact) throws Exception {
        if (contact.getFullName() == null || contact.getFullName().trim().isEmpty()
                || contact.getEmail() == null || contact.getEmail().trim().isEmpty()
                || contact.getSubject() == null || contact.getSubject().trim().isEmpty()
                || contact.getMessage() == null || contact.getMessage().trim().isEmpty()) {
            throw new Exception("Please fill in all fields.");
        }

        ContactDAO dao = new ContactDAO();
        dao.insertMessage(contact);
    }
}