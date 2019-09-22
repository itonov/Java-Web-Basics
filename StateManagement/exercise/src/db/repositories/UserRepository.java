package db.repositories;

import db.entities.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String USERS_FILE_PATH = System.getProperty("user.dir") + "/src/db/files/users.txt";

    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
        seedUsersFromFile();
    }

    public void addUser(User user) {
        this.users.add(user);
        this.saveDb();
    }

    public User getUserByEmail(String email) {
        for (User user : this.users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        throw new IllegalArgumentException("no such user");
    }

    public boolean isUserEmailPresentInDb(String userEmail) {
        for (User registeredUser : this.users) {
            if (registeredUser.getEmail().equals(userEmail)) {
                return true;
            }
        }

        return false;
    }

    private void saveDb() {
        try {
            Files.deleteIfExists(Paths.get(USERS_FILE_PATH));
            Files.createFile(Paths.get(USERS_FILE_PATH));
            StringBuilder fileContent = new StringBuilder();
            for (User user : this.users) {
                fileContent.append(user.toString()).append(System.lineSeparator());
            }

            Files.write(Paths.get(USERS_FILE_PATH), fileContent.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void seedUsersFromFile() {
        List<String> usersAsString = new ArrayList<>();
        try {
            usersAsString = Files.readAllLines(Paths.get(USERS_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : usersAsString) {
            String[] userTokens = line.split(";");
            User user = new User(userTokens[1].split("=")[1], userTokens[2].split("=")[1]);
            user.setId(userTokens[0].split("=")[1]);
            this.addUser(user);
        }

    }
}
