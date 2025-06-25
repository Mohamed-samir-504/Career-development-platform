package org.sumerge.userservice.service;



import org.sumerge.userservice.dto.CreateUserRequest;
import org.sumerge.userservice.dto.UpdateUserRequest;
import org.sumerge.userservice.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(UUID id);
    UserResponse updateUser(UUID id, UpdateUserRequest request);
    List<UserResponse>getAllUsers();
    UserResponse getUserByEmail(String email);
    List<UserResponse> getUsersByManagerId(UUID managerId);
}
