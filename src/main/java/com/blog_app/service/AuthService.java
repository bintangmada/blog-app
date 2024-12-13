package com.blog_app.service;

import com.blog_app.payload.LoginDto;
import com.blog_app.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
