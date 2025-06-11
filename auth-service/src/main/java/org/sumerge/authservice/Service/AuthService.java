package org.sumerge.authservice.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.sumerge.authservice.Client.UserServiceClient;
import org.sumerge.authservice.Model.DTO.*;
import org.sumerge.authservice.Model.UserAccount;
import org.sumerge.authservice.Repository.UserAccountRepository;
import org.sumerge.shared.utils.JwtUtil;


@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    AuthService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder,
                JwtUtil jwtUtil, UserServiceClient userServiceClient) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userServiceClient = userServiceClient;
    }

    public SignupResponse signup(SignupRequest request) {
        if (userAccountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        UserAccount user = UserAccount.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userAccountRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());

        CreateUserRequest userRequest = new CreateUserRequest(
                user.getId(),request.getName(),user.getEmail(),token);

        try {
            userServiceClient.createUser(userRequest);

        } catch (Exception e) {

            userAccountRepository.deleteById(user.getId());
            throw new RuntimeException("Failed to create user in UserService: " + e.getMessage(), e);
        }

        return new SignupResponse(user.getId(),user.getEmail(), token);
    }



    public LoginResponse login(LoginRequest request) {
        UserAccount user = userAccountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(user.getId(),user.getEmail(), token);
    }

}
