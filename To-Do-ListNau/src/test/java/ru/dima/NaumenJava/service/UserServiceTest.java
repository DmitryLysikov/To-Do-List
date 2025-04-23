package ru.dima.naumenjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dima.naumenjava.entity.User;
import ru.dima.naumenjava.exception.UserNotFoundException;
import ru.dima.naumenjava.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser_ShouldSaveUser() {
        String userName = "testUser";
        String password = "password123";
        String email = "test@example.com";

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.createUser(userName, password, email);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void findByUserName_WhenUserExists_ShouldReturnUser() {
        String userName = "existingUser";
        User expectedUser = new User(userName, "pass", "user@example.com");
        List<User> users = Collections.singletonList(expectedUser);

        when(userRepository.findAll()).thenReturn(users);

        User actualUser = userService.findByUserName(userName);

        assertNotNull(actualUser);
        assertEquals(userName, actualUser.getUserName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findByUserName_WhenUserExistsWithDifferentCase_ShouldReturnUser() {
        String userName = "ExistingUser";
        String searchName = "existinguser";
        User expectedUser = new User(userName, "pass", "user@example.com");
        List<User> users = Collections.singletonList(expectedUser);

        when(userRepository.findAll()).thenReturn(users);


        User actualUser = userService.findByUserName(searchName);

        assertNotNull(actualUser);
        assertEquals(userName, actualUser.getUserName());
    }

    @Test
    public void findByUserName_WhenMultipleUsersExist_ShouldReturnCorrectUser() {
        String targetUserName = "targetUser";
        User user1 = new User("user1", "pass1", "user1@example.com");
        User user2 = new User(targetUserName, "pass2", "user2@example.com");
        User user3 = new User("user3", "pass3", "user3@example.com");
        List<User> users = Arrays.asList(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(users);

        User actualUser = userService.findByUserName(targetUserName);

        assertNotNull(actualUser);
        assertEquals(targetUserName, actualUser.getUserName());
    }

    @Test
    public void findByUserName_WhenUserDoesNotExist_ShouldThrowException() {

        String userName = "nonExistingUser";
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.findByUserName(userName));

        assertEquals("Пользователь с именем " + userName + " не найден", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findByUserName_WhenUserNameIsNull_ShouldThrowException() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class,
                () -> userService.findByUserName(null));
    }

    @Test
    public void findByUserName_WhenUserNameIsEmpty_ShouldThrowException() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class,
                () -> userService.findByUserName(""));
    }
}
