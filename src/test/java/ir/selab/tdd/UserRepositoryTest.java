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

    @Test
    public void createRepositoryWithDuplicateEmails__ShouldThrowException() {
        User user1 = new User("alireza", "1234","a@gmail.com");
        User user2 = new User("ali", "4567","a@gmail.com");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(Arrays.asList(user1, user2));
        });
    }

    @Test
    public void createRepositoryWithSomeNullEmails_ShouldNotAddToHashmap(){
        User user1 = new User("alireza", "1234","a@gmail.com");
        User user2 = new User("ali", "4567","b@gmail.com");
        User user3 = new User("alire", "1234","c@gmail.com");
        User user4 = new User("alirez", "4567");
        UserRepository repo = new UserRepository(Arrays.asList(user1, user2, user3, user4));
        assertEquals(3, repo.getAllUsersWithEmail().size());



    }

    @Test
    public void addUserWithEmptyEmail_shouldNotBeAddedInEmailList(){
        //Given
        String username = "aliryete";
        String password = "123";
        String email = "";
        User user = new User(username, password, email);

        //When
        repository.addUser(user);

        //Then
        assertEquals(3,repository.getAllUsersWithEmail().size());

    }

    @Test
    public void changeEmail_shouldNotChangeEmail_WhenEmailIsAlreadyUsed(){
        //Given
        String username1 = "ali";
        String password1 = "123";
        String email1 = "ali@a.com";
        User user1 = new User(username1, password1, email1);
        repository.addUser(user1);

        String username2 = "ali";
        String password2 = "123";
        String email2 = "amir@a.com";
        User user2 = new User(username2, password2, email2);
        repository.addUser(user2);

        //When
        boolean succeeded = repository.changeEmail(username1, email2);

        //Then
        assertFalse(succeeded);
        assertEquals(email1, repository.getUserByUsername(username1).getEmail());
    }
}
