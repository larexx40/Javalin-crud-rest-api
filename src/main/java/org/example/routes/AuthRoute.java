package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.AuthController;

public class AuthRoute {
    private final AuthController authController;

    public AuthRoute() {
        this.authController = new AuthController();
    }

    public void routes(Javalin app) {
        app.post("/auth/login", authController::login);
        app.post("auth/signup", authController::signup);
    }
}
