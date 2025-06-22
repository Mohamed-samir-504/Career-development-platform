package org.sumerge.userservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.sumerge.userservice.dto.CreateUserRequest;
import org.sumerge.userservice.dto.UpdateUserRequest;
import org.sumerge.userservice.dto.UserResponse;
import org.sumerge.userservice.entity.User;
import org.sumerge.userservice.repository.UserRepository;
import org.sumerge.userservice.service.UserService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {

            userResponses.add(mapToDto(user));
        }
        return userResponses;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(User.Role.EMPLOYEE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return mapToDto(user);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return mapToDto(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getManagerId() != null) {
            user.setManager(userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found")));
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return mapToDto(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToDto(user);
    }

    private UserResponse mapToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .managerId(user.getManager() != null ? user.getManager().getId() : null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @KafkaListener(topics = "user-create", groupId = "user-group")
    public void consume(CreateUserRequest request) {
        System.out.println("📥 Received from Kafka: " + request.getEmail());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(User.Role.EMPLOYEE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
