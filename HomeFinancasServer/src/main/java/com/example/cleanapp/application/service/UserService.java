package com.example.cleanapp.application.service;

import com.example.cleanapp.application.dto.AuthRequest;
import com.example.cleanapp.application.port.in.UserUseCase;
import com.example.cleanapp.adapters.outbound.persistence.repository.SpringDataUserRepository;
import com.example.cleanapp.adapters.outbound.persistence.mapper.UserMapper;
import com.example.cleanapp.domain.model.User;
import com.example.cleanapp.adapters.outbound.persistence.entity.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserUseCase {

    private final SpringDataUserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(SpringDataUserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User authenticate(AuthRequest authRequest) {
        UserEntity entity = repository.findByUsername(authRequest.getUsername());
        if (entity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(authRequest.getPassword(), entity.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return mapper.toDomain(entity);
    }

    @Override
    public User getUserById(Long userId) {
        return repository.findById(userId).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }
}