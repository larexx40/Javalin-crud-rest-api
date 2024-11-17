package org.example.mapper;

import org.example.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserMapper {
    public static User map(ResultSet rs) throws SQLException {
        return  new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("username"),
                rs.getString("phoneNumber")
        );
    }
}
