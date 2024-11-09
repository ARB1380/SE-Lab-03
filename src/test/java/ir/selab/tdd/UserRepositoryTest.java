package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    private UserRepository repository;

    @Before
    public void setUp() {
        List<User> userList = Arrays.asList(
                new User("admin", "1234","b@gmail.com"),
                new User("ali", "qwert", "c@gmail.com"),
                new User("mohammad", "123asd", "d@gmail.com"));
        repository = new UserRepository(userList);
    }

    @Test
    public void getContainingUser__ShouldReturn() {
        User ali = repository.getUserByUsername("ali");
        assertNotNull(ali);
        assertEquals("ali", ali.getUsername());
        assertEquals("qwert", ali.getPassword());
    }

    @Test
    public void getNotContainingUser__ShouldReturnNull() {
        User user = repository.getUserByUsername("reza");
        assertNull(user);
    }

    @Test
    public void createRepositoryWithDuplicateUsers__ShouldThrowException() {
        User user1 = new User("ali", "1234");
        User user2 = new User("ali", "4567");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(Arrays.asList(user1, user2));
        });
    }

    @Test
    public void addNewUser__ShouldIncreaseUserCount() {
        int oldUserCount = repository.getUserCount();

        // Given
        String username = "reza";
        String password = "123abc";
        String email = "reza@sharif.edu";
        User newUser = new User(username, password);

        // When
        repository.addUser(newUser);

        // Then
        assertEquals(oldUserCount + 1, repository.getUserCount());
    }

    @Test
    public void removeUser__ShouldDecreaseUserCountByOneWhenUserExists() {
        int oldUserCount = repository.getUserCount();

        // Given
        String username = "mohammad";

        // When
        repository.removeUser(username);

        // Then
        assertEquals(oldUserCount - 1, repository.getUserCount());
    }

    @Test
    public void removeUser__ShouldRemoveUserWithUsername() {

        // Given
        String username = "ali";

        // When
        repository.removeUser(username);

        // Then
        assertNull(repository.getUserByUsername(username));
    }

    @Test
    public void removeUser__ShouldReturnTrueWhenUserExists() {

        // Given
        String username = "ali";

        // When
        boolean removed = repository.removeUser(username);

        // Then
        assertTrue(removed);
    }

    @Test
    public void removeUser__ShouldReturnFalseWhenUserDoesNotExist() {

        // Given
        String username = "ali123";

        // When
        boolean removed = repository.removeUser(username);

        // Then
        assertFalse(removed);
    }

    @Test
    public void getUserByEmail__WhenEmailExists(){

        //Given
        String email = "b@gmail.com";

        //When
        User user = repository.getUserByEmail(email);

        //Then
        assertNotNull(user);
    }

    @Test
    public void getUserByEmail__WhenEmailNotExists(){

        //Given
        String email = "a@gmail.com";

        //When
        User user = repository.getUserByEmail(email);

        //Then
        assertNull(user);
    }

    @Test
    public void AddUser__FailBecauseOfDuplicateEmail(){

        //Given
        String username = "aliryete";
        String password = "123";
        String email = "b@gmail.com";
        User user = new User(username, password, email);

        //When
        boolean result = repository.addUser(user);

        //Then
        assertFalse(result);

    }


}
