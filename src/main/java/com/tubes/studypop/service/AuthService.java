package com.tubes.studypop.service;

import com.tubes.studypop.model.User;
import com.tubes.studypop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // Tambahkan ini untuk digunakan di studentDashboard
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}