package org.example.validation;

import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserValidation {
    // Validates a User object and returns a list of error messages
    public static List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();

        if (user.getName() == null || user.getName().isEmpty()) {
            errors.add("Name is required.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.add("Email is required.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.add("Password is required.");
        } else if (!isValidPassword(user.getPassword())) {
            errors.add("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
        }

        // Optional fields validation (if they are non-null)
//        user.getUsername().ifPresent(username -> {
//            if (username.isEmpty()) {
//                errors.add("Username cannot be empty.");
//            }
//        });



        return errors;
    }

    // Validates password with a regex
    private static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}
