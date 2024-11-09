package org.example.mapper;

import org.example.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserMapper {
    public static User map(ResultSet rs) throws SQLException {
        return  new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                Optional.ofNullable(rs.getString("username")),
                Optional.ofNullable(rs.getString("phoneNumber"))
        );
    }
}
