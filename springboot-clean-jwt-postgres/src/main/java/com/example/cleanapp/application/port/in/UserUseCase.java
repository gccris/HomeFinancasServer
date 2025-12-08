package com.example.cleanapp.application.port.in;

import com.example.cleanapp.application.dto.AuthRequest;
import com.example.cleanapp.domain.model.User;

import java.util.List;

public interface UserUseCase {
    User authenticate(AuthRequest authRequest);
    User getUserById(Long id);
    List<User> getAllUsers();
    User createUser(User user);
}