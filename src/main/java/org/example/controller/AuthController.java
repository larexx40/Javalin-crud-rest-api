package org.example.controller;

import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.dto.auth.SignUpRequest;
import org.example.entity.User;
import org.example.response.CustomResponse;
import org.example.service.AuthService;

import io.javalin.http.Context;
import org.example.validation.AuthValidation;

import java.util.ArrayList;
import java.util.List;


public class AuthController {
    private final AuthService authService;
    public AuthController() {
        this.authService = new AuthService();
    }

    public void login(Context ctx) {
        System.out.println("login Req Body: " + ctx.body());
        if(ctx.body().isEmpty()){
            CustomResponse response = CustomResponse.error("Login input is required");
            response.setStatusCode(400);
            ctx.json(response).status(400);
            return;
        }
        LoginRequest request = ctx.bodyAsClass(LoginRequest.class);

        authService.login(request);
    }

    public void signup(Context ctx) {
        if(ctx.body().isEmpty()){
            CustomResponse response = CustomResponse.error("Signup input is required");
            response.setStatusCode(400);
            ctx.json(response).status(400);
            return;
        }

        RegisterRequest request = ctx.bodyAsClass(RegisterRequest.class);
        //validate input
        List<String> validationError = AuthValidation.register(request);
        if(!validationError.isEmpty()){
            CustomResponse response = CustomResponse.error("Input validation error", validationError);
            response.setStatusCode(400);
            ctx.json(response).status(400);
            return;
        }
        System.out.println("Before signup Controller");
        CustomResponse result = authService.signup(request);
        int statusCode = result.getStatusCode() > 0 ? result.getStatusCode() : (result.isStatus() ? 200 : 500);
        ctx.json(result).status(statusCode);





    }


}
