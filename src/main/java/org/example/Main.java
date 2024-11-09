package org.example;

import io.javalin.Javalin;
import org.example.controller.UserController;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);

        UserService userService = new UserService();
        UserController userController = new UserController();
        userController.userRoutes(app);
    }


}