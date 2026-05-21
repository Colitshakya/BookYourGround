package com.model;

// Importing Serializable so ContactModel objects can be stored or transferred safely
import java.io.Serializable;

// Importing Timestamp to store message creation date and time
import java.sql.Timestamp;

/**
 * ContactModel is a POJO/model class used to store contact form data.
 *
 * This class represents contact message details such as:
 * - Contact ID
 * - Full name
 * - Email
 * - Subject
 * - Message
 * - Created date and time
 */
public class ContactModel implements Serializable {

    // Used to maintain version compatibility during serialization
    private static final long serialVersionUID = 1L;

    // Stores contact message ID
    private int contactId;

    // Stores sender's full name
    private String fullName;

    // Stores sender's email address
    private String email;

    // Stores contact message subject
    private String subject;

    // Stores contact message body
    private String message;

    // Stores date and time when message was created
    private Timestamp createdAt;

    /**
     * Gets contact ID.
     *
     * @return contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact ID.
     *
     * @param contactId contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets full name.
     *
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName sender's full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email address.
     *
     * @param email sender's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets message subject.
     *
     * @return message subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets message subject.
     *
     * @param subject message subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets message body.
     *
     * @return message text
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message body.
     *
     * @param message message text
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets message creation timestamp.
     *
     * @return created date and time
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets message creation timestamp.
     *
     * @param createdAt created date and time
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
