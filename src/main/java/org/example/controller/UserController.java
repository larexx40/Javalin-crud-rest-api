package org.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.entity.User;
import org.example.model.ApiResponse;
import org.example.model.UserModel;
import org.example.response.CustomResponse;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserController {
    private  final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    private void  getAllUsers(Context ctx){
        CustomResponse response = userService.getUsers();
        ctx.json(response).status(HttpStatus.OK);
    }

    private void getUserById(Context ctx){
        String userId = ctx.pathParam("id");
        CustomResponse response = userService.getUserById(userId);
        int statusCode = response.getStatusCode() > 0 ? response.getStatusCode()
                : (response.isStatus() ? HttpStatus.OK.getCode() : HttpStatus.NOT_FOUND.getCode());

        ctx.json(response).status(statusCode);
    }

    private void createUser(Context ctx){
        User user = ctx.bodyAsClass(User.class);
        CustomResponse response = this.userService.addUser(user);
        int statusCode = response.getStatusCode() > 0? response.getStatusCode()
                : (response.isStatus() ? HttpStatus.OK.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        ctx.json(response).status(statusCode);

    }

    private void deleteUser(Context ctx){
        String userId = ctx.pathParam("id");
        CustomResponse response = this.userService.deleteUserById(userId);
        int statusCode = response.getStatusCode() > 0? response.getStatusCode()
                : (response.isStatus() ? HttpStatus.OK.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        ctx.json(response).status(statusCode);
    }

    private void updateUser(Context ctx){
        String userId = ctx.pathParam("id");
        User newUser = ctx.bodyAsClass(User.class); //convert jason input to class

        CustomResponse response = this.userService.updateUser(userId, newUser);
        int statusCode = response.getStatusCode() > 0? response.getStatusCode()
                : (response.isStatus() ? HttpStatus.OK.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        ctx.json(response).status(statusCode);

    }

    public void userRoutes(Javalin app) {
        app.get("/users", this::getAllUsers);
        app.get("/users/{id}", this::getUserById);
        app.post("/users", this::createUser);
        app.delete("/users/{id}", this::deleteUser);
        app.put("/users/{id}", this::updateUser);
    }

}
