package org.example.service;

import org.example.entity.User;
import org.example.model.ApiResponse;
import org.example.model.UserModel;
import org.example.response.CustomResponse;
import org.example.validation.UserValidation;

import java.awt.image.PixelGrabber;
import java.util.*;

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
            user.setId(UUID.randomUUID().toString());

            boolean result = this.userModel.addUser(user);
            if(result){
                return  CustomResponse.success("User added successfully", user);
            }else {
                return  CustomResponse.error("Error adding user", null);
            }

        }catch (Exception e) {
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
            Optional<User> existingUser = this.userModel.getUserById(id);
            if (existingUser.isEmpty()) {
                return CustomResponse.error("User with id " + id + " not found", null);
            }

            newUser.setId(id);
            boolean result = this.userModel.updateUser(id, newUser);
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
            Optional<User> existingUser = this.userModel.getUserById(id);
            if (existingUser.isEmpty()) {
                return CustomResponse.error("User with id " + id + " not found", null);
            }
            boolean result = this.userModel.deleteUser(id);
            if (result) {
                return CustomResponse.success("User deleted successfully", null);
            }
            return CustomResponse.error("Error deleting user", null);
        }catch (Exception e) {
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Error connecting to database", errors);
            response.setStatusCode(500);
            return  response;


        }
    }


    public CustomResponse getUserById(String id){
        Optional<User> user = this.userModel.getUserById(id);
        if(user.isEmpty()){
            return CustomResponse.error("User with id " + id + " not found", null);
        }
        return CustomResponse.success("User fetched successfully", user);
    }
}
