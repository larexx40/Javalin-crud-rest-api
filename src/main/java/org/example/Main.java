package org.example;

import io.javalin.Javalin;

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

        app.get("/welcome/{name}", ctx -> {
            String name = ctx.pathParam("name");

            //create json response object
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("name", name);
            response.put("message", "Hello " + name);

            //return response;
            ctx.json(response);
        });

        app.post("/signup", ctx->{
            // Create the response structure
            Map<String, Object> response = new HashMap<>();

            try {
                User user = ctx.bodyAsClass(User.class);

                //validate user
                List<String> validationErrors = userInputValidator(user);
                if (!validationErrors.isEmpty()) {
                    ctx.status(400);
                    response.put("status", "error");
                    response.put("message", "Invalid data submitted");
                    response.put("data", null);
                    response.put("errors", validationErrors);
                }else {
                    response.put("status", "success");
                    response.put("message", "Data submitted");
                    response.put("data", user);
                }

            }catch (Exception e){
                ctx.status(400);
                response.put("status", "error");
                response.put("message", "Missing or invalid data");
                response.put("data", null);
            }
            ctx.json(response);
        });
    }

    public static class  User{
        public String name;
        public String email;
        public String password;
        public String username;

    }

    public static List<String> userInputValidator(User user){
        List<String> errors = new ArrayList<>();
        if(user.name == null || user.name.isEmpty()){
            errors.add( "Name is required");
        }
        if(user.email == null || user.email.isEmpty()){
            errors.add( "Email is required");
        }
        if(user.password == null || user.password.isEmpty()){
            errors.add( "Password is required");
        } else if (user.password.length() < 8) {
            errors.add( "Password must be at least 8 characters");
        }

        if(user.username == null || user.username.isEmpty()){
            errors.add( "Username is required");
        }
        return errors;
    }
}