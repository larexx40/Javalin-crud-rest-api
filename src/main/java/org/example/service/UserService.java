package org.example.service;

import org.example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public User addUser(User user) {
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        return user;
    }

    public boolean updateUser(String id ,User newUser) {
        Optional<User> existingUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
//            users.add(user);
            return true;
        }
        return false;
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public boolean deleteUserById(String id){
        return users.removeIf(user -> user.getId().equals(id));
    }

    public Optional<User> getUserById(String id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }
}
