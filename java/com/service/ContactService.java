package com.service;

// Importing ContactDAO to save contact messages into the database
import com.dao.ContactDAO;

// Importing ContactModel to store contact form data
import com.model.ContactModel;

/**
 * ContactService contains business logic for contact form submission.
 *
 * This service validates contact form data before sending it to ContactDAO.
 *
 * It is responsible for:
 * - Checking empty contact form fields
 * - Saving valid contact messages
 */
public class ContactService {

    /**
     * Validates and saves a contact message.
     *
     * @param contact ContactModel object containing contact form data
     * @throws Exception if validation fails or message cannot be saved
     */
    public void saveMessage(ContactModel contact) throws Exception {
        // Checks whether any contact form field is empty
        if (contact.getFullName() == null || contact.getFullName().trim().isEmpty()
                || contact.getEmail() == null || contact.getEmail().trim().isEmpty()
                || contact.getSubject() == null || contact.getSubject().trim().isEmpty()
                || contact.getMessage() == null || contact.getMessage().trim().isEmpty()) {
            throw new Exception("Please fill in all fields.");
        }

        // Creating ContactDAO object to save contact message
        ContactDAO dao = new ContactDAO();

        // Inserting contact message into database
        dao.insertMessage(contact);
    }
}
