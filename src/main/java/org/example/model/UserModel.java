package org.example.model;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.UpdateQuery;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.example.config.DatabaseConfig;
import org.example.dto.auth.SignUpRequest;
import org.example.entity.User;
import org.example.mapper.UserMapper;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserModel {
    private final FluentJdbc fluentJdbc;

    public UserModel() {
        this.fluentJdbc = DatabaseConfig.getFluentJdbc();
    }

    public List<User> getUsers() {
        return fluentJdbc.query().select("SELECT * FROM users").listResult(UserMapper::map);
    }

    public Optional<User> getUserById(final UUID id) {
        return fluentJdbc.query()
                .select("SELECT * FROM users WHERE  id = :id")
                .namedParam("id", id)
                .firstResult(UserMapper::map);
    }

    public Optional<User> getUserByEmail(final String email) {
        return fluentJdbc.query()
                .select("SELECT * FROM users WHERE  email = :email")
                .namedParam("email", email)
                .firstResult(UserMapper::map);
    }

    public Optional<User> getUserByUsername(final String username) {
        return fluentJdbc.query()
                .select("SELECT * FROM users WHERE  username = :username")
                .namedParam("username", username)
                .firstResult(UserMapper::map);
    }

    public boolean addUser(final User user) {
        try {
            UpdateResult result = fluentJdbc.query()
                    .update("INSERT INTO users (id, name, password, email, username, phoneNumber) VALUES (:id, :name, :password, :email, :username, :phoneNumber) ")
                    .namedParam("id", user.getId())
                    .namedParam("name", user.getName())
                    .namedParam("password", user.getPassword())
                    .namedParam("email", user.getEmail())
                    .namedParam("username", user.getUsername())
                    .namedParam("phoneNumber", user.getPhoneNumber())
                    .run();
            return result.affectedRows() > 0;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(UUID id, User newUser) {
        try {
            UpdateResult result = fluentJdbc.query()
                    .update("UPDATE users SET name = :name, email = :email, password = :password, username = :username, phoneNumber = :phoneNumber WHERE id = :id")
                    .namedParam("name", newUser.getName())
                    .namedParam("email", newUser.getEmail())
                    .namedParam("password", newUser.getPassword())
                    .namedParam("username", newUser.getUsername())
                    .namedParam("phoneNumber", newUser.getPhoneNumber())
                    .namedParam("id", id)
                    .run();
            return result.affectedRows() > 0;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(final UUID id) {
        try {
            UpdateResult result = fluentJdbc.query()
                    .update("DELETE  FROM users WHERE id = :id")
                    .namedParam("id", id)
                    .run();
            return result.affectedRows() > 0;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signup(final SignUpRequest user) {
        try {
            UpdateQuery query = fluentJdbc.query()
                    .update("INSERT INTO users_new (id, firstname, lastname, password, email, username) VALUES (:id, :firstname, :lastname, :password, :email, :username) ")
                    .namedParam("id", UUID.randomUUID())
                    .namedParam("firstname", user.getFirstName())
                    .namedParam("lastname", user.getLastName())
                    .namedParam("password", user.getPassword())
                    .namedParam("email", user.getEmail())
                    .namedParam("username", user.getUsername());

            // Execute the query
            UpdateResult result = query.run();
            return result.affectedRows() > 0;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





}

