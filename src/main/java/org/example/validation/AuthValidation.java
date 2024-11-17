package org.example.validation;

import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.dto.auth.SignUpRequest;

import java.util.ArrayList;
import java.util.List;

public class AuthValidation {
    public static List<String> login(LoginRequest request){
        List<String> errors = new ArrayList<String>();

        if(request.getEmail() == null || request.getPassword().isEmpty()){
            errors.add("Email or password is empty");
        }
        if(request.getPassword() == null || request.getPassword().isEmpty()){
            errors.add("Password is empty");
        }

        return errors;
    }

    public static  List<String> signUp(SignUpRequest request){
        List<String> errors = new ArrayList<String>();
        if(request.getFirstName() == null || request.getFirstName().isEmpty()){
            errors.add("First name is required");
        }

        if(request.getLastName() == null || request.getLastName().isEmpty()){
            errors.add("Last name is required");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty() ){
            errors.add("Username is required");
        }

        if(request.getEmail() == null || request.getEmail().isEmpty()){
            errors.add("Email is required");
        }

        if(request.getPassword() == null || request.getPassword().isEmpty()){
            errors.add("Password is empty");
        } else if (!isValidPassword(request.getPassword())) {
            errors.add("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
        }
        return errors;
    }

    public static  List<String> register(RegisterRequest request){
        List<String> errors = new ArrayList<String>();
        if(request.getFirstName() == null || request.getFirstName().isEmpty()){
            errors.add("First name is required");
        }

        if(request.getLastName() == null || request.getLastName().isEmpty()){
            errors.add("Last name is required");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty() ){
            errors.add("Username is required");
        }

        if(request.getEmail() == null || request.getEmail().isEmpty()){
            errors.add("Email is required");
        }

        if(request.getPassword() == null || request.getPassword().isEmpty()){
            errors.add("Password is empty");
        } else if (!isValidPassword(request.getPassword())) {
            errors.add("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
        }
        return errors;
    }


    // Validates password with a regex
    private static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}
