package org.softuni.fdmc.data.repos;

import org.softuni.fdmc.data.models.User;

import javax.faces.annotation.ManagedProperty;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class UserRepository {

    @ManagedProperty(value = "users")
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public User getByUsername(String username) {
        return this
                .getAllUsers()
                .stream()
                .filter(x -> x.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return this.users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public boolean userExists(String username) {
        return this.getByUsername(username) != null;
    }

    public boolean isValidCredentials(String username, String password) {
        return this.userExists(username) &&
                this.getByUsername(username).getPassword().equals(password);
    }
}
