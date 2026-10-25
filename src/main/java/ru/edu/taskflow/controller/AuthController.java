package ru.edu.taskflow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.edu.taskflow.dto.request.LoginRequest;
import ru.edu.taskflow.dto.request.RegisterRequest;
import ru.edu.taskflow.dto.response.AuthResponse;
import ru.edu.taskflow.dto.response.UserResponse;
import ru.edu.taskflow.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }

}
