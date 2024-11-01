package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.ApiResponse;
import org.example.model.User;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserController {
    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private void  getAllUsers(Context ctx){
        List<User> users = userService.getUsers();
        ctx.json(new ApiResponse<List<User>>( "Users fetched successfully", users));
    }

    private void getUserById(Context ctx){
        String userId = ctx.pathParam("id");
        Optional<User> user = userService.getUserById(userId);
        if(user.isPresent()){
            ctx.json(new ApiResponse<User>( "User fetched successfully", user.get()));
        }else{
            ctx.status(404).json(ApiResponse.error("User not found", null));
        }
    }

    private void createUser(Context ctx){
        User user = ctx.bodyAsClass(User.class);

        //validate user
        List<String> validationErrors = userInputValidator(user);
        if(!validationErrors.isEmpty()){
            ctx.status(400).json(new ApiResponse<>( "Validation errors", validationErrors));
            return;
        }

        userService.addUser(user);
        ctx.status(201).json(new ApiResponse<User>( "User added successfully", user));
    }

    private void deleteUser(Context ctx){
        String userId = ctx.pathParam("id");
        boolean delete = userService.deleteUserById(userId);
        if(delete){
            ctx.status(204).json(new ApiResponse("User deleted successfully", null));
        }else {
            ctx.status(404).json(ApiResponse.error("User not found", null));
        }
    }

    private void updateUser(Context ctx){
        String userId = ctx.pathParam("id");
        User updateUser = ctx.bodyAsClass(User.class); //convert jason input to clas

        //validate input
        List<String> validationErrors = userInputValidator(updateUser);
        if(!validationErrors.isEmpty()){
            ctx.status(400).json(new ApiResponse<>( "Validation errors", validationErrors));
            return;
        }
        boolean update = userService.updateUser(userId, updateUser);
        if(update){
            ctx.status(200).json(new ApiResponse<User>("User updated successfully", updateUser));
        }else {
            ctx.status(404).json(ApiResponse.error("User Not Found", null));
        }

    }

    public void userRoutes(Javalin app) {
        app.get("/users", this::getAllUsers);
        app.get("/users/{id}", this::getUserById);
        app.post("/users", this::createUser);
        app.delete("/users/{id}", this::deleteUser);
        app.put("/users/{id}", this::updateUser);
    }

    public static List<String> userInputValidator(User user){
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Pattern passwordPattern =  Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        List<String> errors = new ArrayList<>();
        if(user.getName() == null || user.getName().isEmpty()){
            errors.add( "Name is required");
        } else if (user.getName().length() < 3) {
            errors.add( "Name must be at least 3 characters");
        }

        //check for email patter;
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            errors.add( "Email is required");
        }else if(user.getEmail().length() < 5){
            errors.add( "Email must be at least 5 characters");
        } else if (!emailPattern.matcher(user.getEmail()).matches()) {
            errors.add( "Email is invalid");
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()){
            errors.add( "Password is required");
        } else if (user.getPassword().length() < 8) {
            errors.add( "Password must be at least 8 characters");
        } else if (!passwordPattern.matcher(user.getPassword()).matches()) {
            errors.add("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character. ");
        }

        if(user.getUsername() == null || user.getUsername().isEmpty()){
            errors.add( "Username is required");
        }
        return errors;
    }
}
