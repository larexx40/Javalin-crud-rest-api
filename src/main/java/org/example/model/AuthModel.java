package org.example.model;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.UpdateQuery;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.example.config.DatabaseConfig;
import org.example.dto.auth.RegisterRequest;
import org.example.dto.auth.SignUpRequest;
import org.example.entity.User;
import org.example.mapper.UserMapper;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

public class AuthModel {
    private final FluentJdbc fluentJdbc;

    public AuthModel() {
        this.fluentJdbc = DatabaseConfig.getFluentJdbc();
    }

    public Optional<User> getUserByEmail(final String email) {
        return fluentJdbc.query()
                .select("SELECT * FROM users_new WHERE  email = :email;")
                .namedParam("email", email)
                .firstResult(UserMapper::map);
    }

    public Optional<User> getUserByUsername(final String username) {
        return fluentJdbc.query()
                .select("SELECT * FROM users_new WHERE  username = :username;")
                .namedParam("username", username)
                .firstResult(UserMapper::map);
    }

    public boolean signup(final SignUpRequest user) throws Exception {
        try {

            // Log values before creating the query
            System.out.println("At the auth model2");
            System.out.println("Email: " + user.getEmail());
            System.out.println("Before accessing optional values");
            if(user.getPhoneNumber().isEmpty() || user.getPhoneNumber() == null) {
                System.out.println("Phone Number absent");
            }

            String sql = "INSERT INTO users_new (id, firstname, lastname, password, email, username, phonenumber, address, dob, occupation) " +
                    "VALUES (:id, :firstname, :lastname, :password, :email, :username, :phoneNumber, :address, :dob, :occupation)";

            UpdateQuery query;
            query = fluentJdbc.query().update(sql)
                    .namedParam("id", UUID.randomUUID())
                    .namedParam("firstname", user.getFirstName())
                    .namedParam("lastname", user.getLastName())
                    .namedParam("password", user.getPassword())
                    .namedParam("email", user.getEmail())
                    .namedParam("username", user.getUsername())
                    .namedParam("phoneNumber", user.getPhoneNumber().isEmpty() ? user.getPhoneNumber() : null)
                    .namedParam("address", user.getAddress().isEmpty() ? user.getAddress() : null )
                    .namedParam("dob", user.getDateOfBirth())
                    .namedParam("occupation", user.getOccupation().isEmpty()? user.getOccupation() : null);

            // Execute the query
            UpdateResult result = query.run();
            return result.affectedRows() > 0;

        } catch (Exception e) {
            // Detailed error logging
            System.out.println("Exception occurred in AuthModel.signup:");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(final RegisterRequest user) throws Exception {
        try {
            System.out.println("At the auth model2");
            System.out.println("Email: " + user.getEmail());

            String sql = "INSERT INTO users_new (id, firstname, lastname, password, email, username, phonenumber, address, dob, occupation) " +
                    "VALUES (:id, :firstname, :lastname, :password, :email, :username, :phoneNumber, :address, :dob, :occupation)";

            UpdateQuery query = fluentJdbc.query().update(sql)
                    .namedParam("id", UUID.randomUUID())
                    .namedParam("firstname", user.getFirstName())
                    .namedParam("lastname", user.getLastName())
                    .namedParam("password", user.getPassword())
                    .namedParam("email", user.getEmail())
                    .namedParam("username", user.getUsername())
                    .namedParam("phoneNumber", user.getPhoneNumber().orElse(null))
                    .namedParam("address", user.getAddress().orElse(null))
                    .namedParam("dob", user.getDateOfBirth().orElse(null))
                    .namedParam("occupation", user.getOccupation().orElse(null));

            UpdateResult result = query.run();
            return result.affectedRows() > 0;

        } catch (Exception e) {
            System.out.println("Exception occurred in AuthModel.signup:");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
