package com.dao;

// Importing ContactModel to get contact form details
import com.model.ContactModel;

// Importing DBConnection to create a connection with the database
import com.util.DBConnection;

// Importing SQL classes needed for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * ContactDAO handles database operations related to contact messages.
 *
 * This class is responsible for:
 * - Inserting contact form messages into the contact_message table
 */
public class ContactDAO {

    /**
     * Inserts a new contact message into the database.
     *
     * This method receives a ContactModel object containing the user's
     * full name, email, subject, and message, then stores the data in
     * the contact_message table.
     *
     * @param contact ContactModel object containing contact form data
     * @throws Exception if database connection or insert operation fails
     */
    public void insertMessage(ContactModel contact) throws Exception {

        // Creating database connection using DBConnection utility class
        Connection con = DBConnection.getConnection();

        // SQL query to insert contact message details into contact_message table
        String sql = "INSERT INTO contact_message (full_name, email, subject, message) VALUES (?, ?, ?, ?)";

        // Preparing SQL statement to prevent SQL injection
        PreparedStatement pst = con.prepareStatement(sql);

        // Setting full name value from ContactModel
        pst.setString(1, contact.getFullName());

        // Setting email value from ContactModel
        pst.setString(2, contact.getEmail());

        // Setting subject value from ContactModel
        pst.setString(3, contact.getSubject());

        // Setting message value from ContactModel
        pst.setString(4, contact.getMessage());

        // Executing insert query to save the message in the database
        pst.executeUpdate();

        // Closing PreparedStatement resource
        pst.close();

        // Closing database connection
        con.close();
    }
}
