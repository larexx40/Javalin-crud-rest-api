package org.example.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.entity.User;
import org.example.model.UserModel;
import org.example.response.CustomResponse;
import org.example.validation.UserValidation;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserService {
    private final UserModel userModel;

    public UserService() {
        this.userModel = new UserModel();
    }

    public CustomResponse getUsers() {
        List<User> users = this.userModel.getUsers();

        return CustomResponse.success("User fetched successfully", users);
    }

    public CustomResponse addUser(User user) {
        List<String> errors = UserValidation.validateUser(user);
        if (!errors.isEmpty()) {
            return CustomResponse.error("Invalid user input", errors);
        }

        try{
            //generate unique id for the user
            UUID userId = UUID.randomUUID();
            System.out.println("in User service, Adding new user: " + userId);

            // Run email and username checks concurrently
            CompletableFuture<Optional<User>> emailCheck = CompletableFuture.supplyAsync(() -> userModel.getUserByEmail(user.getEmail()));
            CompletableFuture<Optional<User>> usernameCheck = CompletableFuture.supplyAsync(() -> userModel.getUserByUsername(user.getUsername()));

            // Wait for both checks to complete
            Optional<User> userWithMail = emailCheck.get();
            Optional<User> userWithUsername = usernameCheck.get();

            if (userWithMail.isPresent()) {
                String email = userWithMail.get().getEmail();
                CustomResponse error = CustomResponse.error("User with " + email + " already exists");
                error.setStatusCode(400);
                return error;
            }

            if (userWithUsername.isPresent()) {
                CustomResponse error = CustomResponse.error("Username already exists");
                error.setStatusCode(400);
                return error;
            }

            user.setId(userId);
            //hash password using Favre's BCrypt library
            String newPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(newPassword);

            boolean result = this.userModel.addUser(user);
            if(result){
                return  CustomResponse.success("User added successfully", user);
            }else {
                return  CustomResponse.error("Error adding user", null);
            }

        }catch (Exception e) {
            e.printStackTrace();
            errors.add(e.getMessage());
            CustomResponse response = CustomResponse.error("Error connecting to database", errors);
            response.setStatusCode(500);
            return  response;
        }

    }

    public CustomResponse updateUser(String id , User newUser) {
        //validate user for error
        List<String> errors = UserValidation.validateUser(newUser);
        if (!errors.isEmpty()) {
            return CustomResponse.error("Invalid user input", errors);
        }

        try {
            //first get the user and return appropriate error
            Optional<User> existingUser = this.userModel.getUserById(UUID.fromString(id));
            if (existingUser.isEmpty()) {
                return CustomResponse.error("User with id " + id + " not found", null);
            }

            newUser.setId(UUID.fromString(id));
            boolean result = this.userModel.updateUser(UUID.fromString(id), newUser);
            if (result) {
                return CustomResponse.success("User updated successfully", newUser);
            } else {
                return CustomResponse.error("Error updating user", null);
            }
        }catch (Exception e) {
            errors.add(e.getMessage());
            CustomResponse response = CustomResponse.error("Error connecting to database", errors);
            response.setStatusCode(500);
            return  response;
        }

    }

    public CustomResponse deleteUserById(String id) {
        try{
            UUID userId = UUID.fromString(id);
            Optional<User> existingUser = this.userModel.getUserById(UUID.fromString(id));
            if (existingUser.isEmpty()) {
                return CustomResponse.error("User with id " + id + " not found", null);
            }
            System.out.print("User exist, proceed o delete");
            boolean result = this.userModel.deleteUser(userId);
            if (result) {
                return CustomResponse.success("User deleted successfully", null);
            }
            return CustomResponse.error("Error deleting user", null);
        } catch (IllegalArgumentException e) {
            // Handle the case where UUID format is invalid
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Invalid user id passed " + id, errors);
            response.setStatusCode(500);
            return  response;
        }catch (Exception e) {
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Error deleting user from database", errors);
            response.setStatusCode(500);
            return  response;


        }
    }


    public CustomResponse getUserById(String id){
        try {
            UUID userId = UUID.fromString(id);
            Optional<User> user = this.userModel.getUserById(userId);
            if(user.isEmpty()){
                return CustomResponse.error("User with id " + id + " not found", null);
            }
            return CustomResponse.success("User fetched successfully", user);

        }catch (IllegalArgumentException e) {
            // Handle the case where UUID format is invalid
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Invalid user id passed " + id, errors);
            response.setStatusCode(500);
            return response;
        }catch (Exception e) {
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Unable to fetch user with id", errors);
            response.setStatusCode(500);
            return  response;
        }

    }
}
