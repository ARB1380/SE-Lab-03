package ir.selab.tdd.repository;

import ir.selab.tdd.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    private final Map<String, User> usersByUserName;
    private final Map<String, User> usersByEmail;

    public UserRepository(List<User> users) {
        this.usersByUserName = users.stream().collect(Collectors.toMap(User::getUsername, u -> u, (u1, u2) -> {
            throw new IllegalArgumentException("Two users can not have the same username");
        }));


        List<User> usersWithEmails = users.stream().filter(user -> user.getEmail() != null && !user.getEmail().isEmpty()).collect(Collectors.toList());
        this.usersByEmail = usersWithEmails.stream().collect(Collectors.toMap(User::getEmail, u -> u, (u1, u2) -> {
            throw new IllegalArgumentException("Two users can not have the same email");
        }));

    }

    public User getUserByUsername(String username) {
        return usersByUserName.get(username);
    }

    public User getUserByEmail(String email) {
        if(usersByEmail.containsKey(email)){
            return usersByEmail.get(email);
        }
        return null;
    }

    public boolean addUser(User user) {
        if (usersByUserName.containsKey(user.getUsername())) {
            return false;
        }

        if(user.getEmail() != null && !user.getEmail().isEmpty()){
            if(usersByEmail.containsKey(user.getEmail())){
                return false;
            }
            usersByEmail.put(user.getEmail(), user);
        }
        usersByUserName.put(user.getUsername(), user);
        return true;
    }

    public boolean removeUser(String username) {
        return usersByUserName.remove(username) != null;
    }

    public int getUserCount() {
        return usersByUserName.size();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersByUserName.values());
    }

    public List<User> getAllUsersWithEmail(){
        return new ArrayList<>(usersByEmail.values());
    }

    public boolean changeEmail(String username, String email) {
        if (!usersByUserName.containsKey(username)) {
            return false;
        }

        if (usersByEmail.containsKey(email)) {
            return false;
        }

        return true;
    }
}
