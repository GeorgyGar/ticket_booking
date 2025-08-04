package ru.stmLabs.ticket.model;

public class User {
    private long id;
    private String userLogin;
    private String password;
    private String fullName;
    private UserRole role;

    public enum UserRole {
        BUYER, ADMIN
    }
    public User(long id, String fullName, String userLogin, String password, UserRole role) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.userLogin = userLogin;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }
}
