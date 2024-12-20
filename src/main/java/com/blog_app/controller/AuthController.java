package com.blog_app.controller;

import com.blog_app.payload.JWTAuthResponse;
import com.blog_app.payload.LoginDto;
import com.blog_app.payload.RegisterDto;
import com.blog_app.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v2")
@Tag(
        name = "CRUD Rest APIs for Auth Controller"
)
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"login", "sign in"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);

    }

    @PostMapping(value = {"signup", "register"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        String result = authService.register(registerDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
