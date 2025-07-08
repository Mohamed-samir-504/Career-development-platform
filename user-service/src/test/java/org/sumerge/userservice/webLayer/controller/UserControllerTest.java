package org.sumerge.userservice.webLayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.userservice.config.TestSecurityConfig;
import org.sumerge.userservice.controller.UserController;
import org.sumerge.userservice.dto.CreateUserRequest;
import org.sumerge.userservice.dto.UpdateUserRequest;
import org.sumerge.userservice.dto.UserResponse;
import org.sumerge.userservice.entity.User;
import org.sumerge.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {UserController.class, TestSecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private UserResponse mockUserResponse(UUID id, String name, String email, User.Role role) {
        return UserResponse.builder()
                .id(id)
                .name(name)
                .email(email)
                .role(role)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createUser_success() throws Exception {
        UUID userId = UUID.randomUUID();
        CreateUserRequest request = new CreateUserRequest("Test User", "test@example.com");
        UserResponse response = mockUserResponse(userId, "Test User", "test@example.com", User.Role.EMPLOYEE);

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void getAllUsers_success() throws Exception {
        List<UserResponse> mockList = List.of(
                mockUserResponse(UUID.randomUUID(), "Alice", "alice@mail.com", User.Role.EMPLOYEE),
                mockUserResponse(UUID.randomUUID(), "Bob", "bob@mail.com", User.Role.MANAGER)
        );

        when(userService.getAllUsers()).thenReturn(mockList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getUserById_success() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponse response = mockUserResponse(id, "Charlie", "charlie@mail.com", User.Role.EMPLOYEE);

        when(userService.getUserById(id)).thenReturn(response);

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.email").value("charlie@mail.com"));
    }


    @Test
    void getUsersByManager_success() throws Exception {
        UUID managerId = UUID.randomUUID();
        List<UserResponse> responses = List.of(
                mockUserResponse(UUID.randomUUID(), "Emp 1", "emp1@mail.com", User.Role.EMPLOYEE),
                mockUserResponse(UUID.randomUUID(), "Emp 2", "emp2@mail.com", User.Role.EMPLOYEE)
        );

        when(userService.getUsersByManagerId(managerId)).thenReturn(responses);

        mockMvc.perform(get("/users/by-manager/" + managerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateUser_success() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateUserRequest request = new UpdateUserRequest("Updated", "updated@mail.com", User.Role.MANAGER,null , null);
        UserResponse response = mockUserResponse(id, "Updated", "updated@mail.com", User.Role.MANAGER);

        when(userService.updateUser(eq(id), any(UpdateUserRequest.class))).thenReturn(response);

        mockMvc.perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.role").value("MANAGER"));
    }
}
