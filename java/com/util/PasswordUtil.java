package com.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int COST = 10;

    public static String getHashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
    }

    public static boolean checkPassword(String inputPassword, String storedHashedPassword) {
        return BCrypt.checkpw(inputPassword, storedHashedPassword);
    }
}