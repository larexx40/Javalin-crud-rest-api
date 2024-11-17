package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.json.JsonMapper;
import org.example.controller.UserController;
import org.example.mapper.JavalinGsonMapper;
import org.example.response.CustomResponse;
import org.example.routes.AuthRoute;
import org.example.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.http.Context;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        // Configure Gson
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonMapper gsonMapper = new JsonMapper() {
            @Override
            public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                return gson.toJson(obj, type);
            }

            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                return gson.fromJson(json, targetType);
            }
        };

        var app = Javalin.create( config -> config.jsonMapper(gsonMapper))
        .get("/", ctx -> ctx.result("Hello World"))
        .start(7070);


        UserService userService = new UserService();
        UserController userController = new UserController();
        userController.userRoutes(app);

        //Register the auth routes
        AuthRoute authRoute= new AuthRoute();
        authRoute.routes(app);
        
        app.exception(Exception.class, (e, ctx) -> handleException(e, ctx));
    }

    public static  void handleException(Exception e, Context ctx) {
        logger.error(e.getMessage(), e);
        List<String> errors = Collections.singletonList(e.getMessage());
        CustomResponse response = CustomResponse.error("An unexpected error occur", errors);
        int statusCode = response.getStatusCode() > 0 ? response.getStatusCode() : 500;
        ctx.json(response).status(statusCode);
    }

}