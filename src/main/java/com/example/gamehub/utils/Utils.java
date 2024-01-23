package com.example.gamehub.utils;

public class Utils {
    public static String passwordValidator(String password) throws IllegalArgumentException {

        if (!password.matches("^(?=.[A-Z])(?=.[a-z])(?=.\\d)(?=.[!@#$%&])[A-Z a-z\\d!@#$%&]{8,}$")) {
            throw new IllegalArgumentException("Password should have at least one uppercase letter, one lowercase " +
                    "letter, one number and one special character (!@#$%&) and should be at least 8 characters long.");
        }
        return password;
    }
}
