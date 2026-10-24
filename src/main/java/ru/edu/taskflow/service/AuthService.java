package ru.edu.taskflow.service;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
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
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public UserResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already in use");
        }

        User user = userMapper.toEntity(request);

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        Role defaultRole = roleRepository.findByName(RoleName.EMPLOYEE)
                        .orElseThrow(() -> new IllegalStateException("Default role EMPLOYEE not found"));

        user.setRole(defaultRole);

        userRepository.save(user);

        return userMapper.toResponse(user);

    }
    public AuthResponse login (LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Incorrect email or password"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPasswordHash() )){
            throw new IllegalArgumentException("Incorrect email or password");
        }

        if (!user.isEnabled()){
            throw new IllegalStateException("Account is disabled");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .email(user.getEmail())
                .roleName(user.getRole().getName())
                .build();

    }


}
