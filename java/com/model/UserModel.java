package com.model;

/**
 * UserModel is a POJO/model class used to store user information.
 *
 * This class represents user details such as:
 * - User ID
 * - First name
 * - Last name
 * - Email
 * - Phone number
 * - Password
 * - Status
 * - Role
 */
public class UserModel {

    // Stores user ID
    private int userId;

    // Stores user's first name
    private String firstName;

    // Stores user's last name
    private String lastName;

    // Stores user's email address
    private String email;

    // Stores user's phone number
    private String phone;

    // Stores user's password
    private String password;

    // Stores user status, such as active, inactive, pending, or deleted
    private String status;

    // Stores user role, such as admin or user
    private String role;

    /**
     * Gets user ID.
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user ID.
     *
     * @param userId user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * @param email user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone number.
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone number.
     *
     * @param phone user's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets user status.
     *
     * @return user status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets user status.
     *
     * @param status user status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets user role.
     *
     * @return user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets user role.
     *
     * @param role user role
     */
    public void setRole(String role) {
        this.role = role;
    }
}
