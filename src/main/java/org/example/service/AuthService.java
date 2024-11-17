package org.example.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.LoginResponse;
import org.example.dto.auth.RegisterRequest;
import org.example.dto.auth.SignUpRequest;
import org.example.entity.User;
import org.example.model.AuthModel;
import org.example.model.UserModel;
import org.example.response.CustomResponse;
import org.example.validation.AuthValidation;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class AuthService {
    private  final UserModel userModel;
    private final AuthModel authModel;

    public AuthService() {
        this.authModel = new AuthModel();
        this.userModel = new UserModel();
    }

    //login
    public CustomResponse login(LoginRequest input) {
        try{
            //validate login input
            List<String> errors = AuthValidation.login(input);
            if (errors.isEmpty()) {
                CustomResponse response = CustomResponse.error("Validation error", errors);
                response.setStatusCode(400);
                return response;
            }

            //get user with email provided
            Optional<User> user = this.userModel.getUserByEmail(input.getEmail());
            if(!user.isPresent()) {
                CustomResponse response = CustomResponse.error("Incorrect email");
                response.setStatusCode(400);
                return response;
            }

            //verify password with saved hashed
            String hashedPassword = user.get().getPassword();
            BCrypt.Result isValid = BCrypt.verifyer().verify(input.getPassword().toCharArray(), hashedPassword.toCharArray());
            if(!isValid.verified) {
                CustomResponse response = CustomResponse.error("Incorrect password");
                response.setStatusCode(400);
                return response;
            }

            //custom data
            LoginResponse responseData = new LoginResponse("access-token", "refresh-token", user.get());
            CustomResponse response = CustomResponse.success("Login successful", responseData);
            response.setStatusCode(200);
            return response;
        }catch (Exception e) {
            e.printStackTrace();
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Error connecting to database", errors);
            response.setStatusCode(500);
            return  response;
        }
    }

    //signup
    public CustomResponse signup(RegisterRequest input) {
        try{
            System.out.println("Signup Request: " + input);
            String email = input.getEmail();
            String username = input.getUsername();

            CompletableFuture<Optional<User>> emailCheck = CompletableFuture.supplyAsync(()-> this.authModel.getUserByEmail(email));
            CompletableFuture<Optional<User>> usernameCheck = CompletableFuture.supplyAsync(()-> this.authModel.getUserByUsername(username));

            Optional<User> userWithEmail = emailCheck.get();
            Optional<User> userWithUsername = usernameCheck.get();

            if(userWithEmail.isPresent()){
                CustomResponse response = CustomResponse.error("Email already in use");
                response.setStatusCode(400);
                return response;
            }

            if(userWithUsername.isPresent()){
                CustomResponse response = CustomResponse.error("Username already in use");
                response.setStatusCode(400);
                return response;
            }

            //hash password using Favre's BCrypt library
            String newPassword = BCrypt.withDefaults().hashToString(12, input.getPassword().toCharArray());
            input.setPassword(newPassword);

            System.out.println("Before signup model " + input);
            boolean result = this.authModel.registerUser(input);
            if(result){
                return  CustomResponse.success("User added successfully", input);
            }else {
                return  CustomResponse.error("Error adding user", null);
            }




        }catch (Exception e) {
            e.printStackTrace();
            List<String> errors = Collections.singletonList(e.getMessage());
            CustomResponse response = CustomResponse.error("Error connecting to database", errors);
            response.setStatusCode(500);
            return  response;
        }
    }
    //forgot-password
    //reset-password
}
