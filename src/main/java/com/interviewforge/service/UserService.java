package com.interviewforge.service;

import com.interviewforge.dto.LoginRequest;
import com.interviewforge.dto.LoginResponse;
import com.interviewforge.dto.RegisterRequest;

public interface UserService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}