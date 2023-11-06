package com.training.wafi.UserAppAccess;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String organization;
    private String role;
    private String truckNumber;

    // Required default constructor for Firebase object mapping
    public User() { }

    public User(String firstName, String lastName, String email, String organization, String role, String truckNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.organization = organization;
        this.role = role;
        this.truckNumber = truckNumber;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", role='" + role + '\'' +
                ", truckNumber='" + truckNumber + '\'' +
                '}';
    }
}
