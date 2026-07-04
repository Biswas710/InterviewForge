package com.interviewforge.service;

import com.interviewforge.dto.LoginRequest;
import com.interviewforge.dto.RegisterRequest;
import jakarta.validation.Valid;

public interface UserService {

    String register(RegisterRequest request);

    String login(@Valid LoginRequest request);
}