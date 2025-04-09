package ru.dima.naumenjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dima.naumenjava.entity.User;
import ru.dima.naumenjava.exception.UserNotFoundException;
import ru.dima.naumenjava.repository.UserRepository;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String userName, String password, String email) {
        User user = new User(userName, password, email);
        userRepository.save(user);
    }

    public User findByUserName(String userName) {
        Optional<User> optionalUser = userRepository.findAll()
                .stream()
                .filter(u -> u.getUserName().equalsIgnoreCase(userName))
                .findFirst();

        return optionalUser.orElseThrow(() -> new UserNotFoundException("Пользователь с именем " + userName
                + " не найден"));
    }
}
