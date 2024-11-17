package org.example.dto.auth;


import java.time.LocalDate;
import java.util.Optional;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private Optional<LocalDate> dateOfBirth = Optional.empty(); // Optional date of birth
    private Optional<String> occupation = Optional.empty();      // Optional occupation
    private Optional<String> address = Optional.empty();         // Optional address
    private Optional<String> phoneNumber = Optional.empty();     // Optional phone number

    // Constructor for required fields only
    public RegisterRequest(String firstName, String lastName, String email, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Constructor for all fields (required and optional)
    public RegisterRequest(String firstName, String lastName, String email, String password, String username,
                         LocalDate dateOfBirth, String occupation, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
        this.occupation = Optional.ofNullable(occupation);
        this.address = Optional.ofNullable(address);
        this.phoneNumber = Optional.ofNullable(phoneNumber);
    }

    // Getters and Setters for Optional fields
    public Optional<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
    }

    public Optional<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = Optional.ofNullable(occupation);
    }

    public Optional<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = Optional.ofNullable(address);
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Optional.ofNullable(phoneNumber);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
