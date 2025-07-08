package org.sumerge.authservice.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.sumerge.authservice.Dto.*;
import org.sumerge.authservice.Entity.UserAccount;
import org.sumerge.authservice.Repository.UserAccountRepository;
import org.sumerge.shared.utils.JwtUtil;


@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KafkaTemplate<String, CreateUserRequest> kafkaTemplate;

    AuthService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder,
                JwtUtil jwtUtil, KafkaTemplate<String, CreateUserRequest> kafkaTemplate) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.kafkaTemplate = kafkaTemplate;
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
            kafkaTemplate.send("user-create", userRequest);
        } catch (Exception e) {
            userAccountRepository.deleteById(user.getId()); // rollback user account
            throw new RuntimeException("Failed to send message to Kafka: " + e.getMessage(), e);
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
        long expiresIn = jwtUtil.getExpiration();

        return new LoginResponse(user.getId(),user.getEmail(), token,expiresIn);
    }

}
