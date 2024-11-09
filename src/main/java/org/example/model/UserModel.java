package org.example.model;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.example.entity.User;
import org.example.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

public class UserModel {
    private  final FluentJdbc fluentJdbc;

    public UserModel(final FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    public List<User> getUsers() {
        return fluentJdbc.query().select("SELECT * FROM users").listResult(UserMapper::map);
    }

    public Optional<User> getUserById(final String id) {
        return fluentJdbc.query()
                .select("SELECT * FROM users WHERE  id = :id;")
                .namedParam("id", id)
                .firstResult(UserMapper::map);
    }

    public void addUser(final User user) {
        fluentJdbc.query()
                .update("INSERT INTO users (id, name, password, email, username, phoneNumber) VALUES (:id, :name, :password, :email, :username, :phoneNumber) ")
                .namedParam("id", user.getId())
                .namedParam("name", user.getName())
                .namedParam("password", user.getPassword())
                .namedParam("email", user.getEmail())
                .namedParam("username", user.getUsername().orElse(null))
                .namedParam("phoneNumber", user.getPhoneNumber().orElse(null))
                .run();
    }

    public boolean updateUser(String id, User newUser) {
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
    }

    public boolean deleteUser(final String id) {
        UpdateResult result = fluentJdbc.query()
                .update("DELETE  FROM users WHERE id = :id")
                .namedParam("id", id)
                .run();
        return result.affectedRows() > 0;
    }


}

