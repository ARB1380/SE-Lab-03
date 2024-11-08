package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import ir.selab.tdd.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        UserRepository userRepository = new UserRepository(Arrays.asList());
        userService = new UserService(userRepository);
        userService.registerUser("admin", "1234");
        userService.registerUser("ali", "qwert");
    }

    @Test
    public void createNewValidUser__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUser__ShouldFail() {
        String username = "ali";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertFalse(b);
    }

    @Test
    public void loginWithValidUsernameAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithUsername("admin", "1234");
        assertTrue(login);
    }

    @Test
    public void loginWithValidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("admin", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("ahmad", "abcd");
        assertFalse(login);
    }

    @Test
    public void removeUserWithCorrectUsername__ShouldSucceed() {
        boolean removed = userService.removeUser("ali");
        assertTrue(removed);
    }

    @Test
    public void removeUserWithIncorrectUsername__ShouldFail() {
        boolean removed = userService.removeUser("ali1");
        assertFalse(removed);
    }

    @Test
    public void getAllUsers__ShouldGetListOfCorrectLength() {
        List<User> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());
    }

    @Test
    public void getAllUsers__ShouldContainAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        String firstUsername = allUsers.get(0).getUsername();
        String secondUsername = allUsers.get(1).getUsername();

        // We should not be able to register user with existing username
        assertFalse(userService.registerUser(firstUsername, "somePassword"));
        assertFalse(userService.registerUser(secondUsername, "somePassword"));
    }
}
