package ru.sber.edu.yetanotherchat.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.sber.edu.yetanotherchat.entity.User;
import ru.sber.edu.yetanotherchat.exception.UserNotExists;
import ru.sber.edu.yetanotherchat.repository.UserRepository;

@AllArgsConstructor
@Service
@Log
public class UserService {

    private UserRepository userRepository;

    public User createIfNotExist(String email, String name) {
        var optionalUser = userRepository.findByLogin(email);

        return optionalUser.orElseGet(() -> createUser(email, name));
    }

    public User createUser(String email, String name) {
        User user = new User(email, name);
        user = userRepository.save(user);
        return user;
    }

    public User getUser(String email) {
        var user = userRepository.findByLogin(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserNotExists(email);
    }

}
