package ru.edu.taskflow.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.taskflow.dto.request.LoginRequest;
import ru.edu.taskflow.dto.request.RegisterRequest;
import ru.edu.taskflow.dto.response.AuthResponse;
import ru.edu.taskflow.dto.response.UserResponse;
import ru.edu.taskflow.entity.Role;
import ru.edu.taskflow.entity.RoleName;
import ru.edu.taskflow.entity.User;
import ru.edu.taskflow.mapper.UserMapper;
import ru.edu.taskflow.repository.RoleRepository;
import ru.edu.taskflow.repository.UserRepository;
import ru.edu.taskflow.security.JwtService;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public AuthService(UserMapper userMapper,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       JwtService jwtService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        Role defaultRole = roleRepository.findByName(RoleName.EMPLOYEE)
                .orElseThrow(() -> new IllegalStateException("Default role EMPLOYEE not found"));

        user.setRole(defaultRole);
        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("Incorrect email or password"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Incorrect email or password");
        }

        if (!user.isEnabled()) {
            throw new IllegalStateException("Account is disabled");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                "Bearer",
                user.getEmail(),
                user.getRole().getName()
        );
    }
}
