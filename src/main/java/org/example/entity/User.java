package org.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String username;   // Optional field
    private String phoneNumber;

    // Default constructor for deserialization
//    public User() {
//        this.id = UUID.randomUUID().toString(); // Auto-generate UUID for id
//        this.username = Optional.empty();
//        this.phoneNumber = Optional.empty();
//    }

    // Parameterized constructor for convenience
    @JsonCreator
    public User(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("username") String username,
            @JsonProperty("phoneNumber") String phoneNumber
    ) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
