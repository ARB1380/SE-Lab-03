package ir.selab.tdd.service;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public boolean loginWithUsername(String username, String password) {
        User userByUsername = repository.getUserByUsername(username);
        if (userByUsername == null) {
            return false;
        }
        return userByUsername.getPassword().equals(password);
    }

    public boolean loginWithEmail(String username, String password) {
        User userByUsername = repository.getUserByUsername(username);
        if (userByUsername == null) {
            return false;
        }

        if (!userByUsername.getPassword().equals(password)){
            return false;
        }

        if(userByUsername.getEmail() == null || userByUsername.getEmail().isEmpty()){
            return false;
        }

        return true;
    }

    public boolean registerUser(String username, String password) {
        User user = new User(username, password);
        return repository.addUser(user);
    }

    public boolean registerUser(String username, String password, String email) {
        User user = new User(username, password);
        user.setEmail(email);
        return repository.addUser(user);
    }

    public boolean removeUser(String username) {
        return repository.removeUser(username);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public boolean changeUserEmail(String username, String newEmail) {
        return repository.changeEmail(username, newEmail);
    }
}
