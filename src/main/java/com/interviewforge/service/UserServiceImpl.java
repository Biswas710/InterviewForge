package com.interviewforge.service;

import com.interviewforge.dto.RegisterRequest;
import com.interviewforge.entity.Role;
import com.interviewforge.entity.User;
import com.interviewforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .enabled(true)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}