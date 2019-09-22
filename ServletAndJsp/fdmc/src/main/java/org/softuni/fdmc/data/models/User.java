package org.softuni.fdmc.data.models;

public class User {
    private String username;

    private String password;

    private UserRole role;

    public User(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
