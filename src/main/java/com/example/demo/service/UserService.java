package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password) throws Exception {
        if (userRepository.findByUsername(username) != null) {
            throw new Exception("Пользователь с таким именем уже существует");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
