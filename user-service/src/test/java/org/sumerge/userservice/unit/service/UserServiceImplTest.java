package org.sumerge.userservice.unit.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.userservice.dto.CreateUserRequest;
import org.sumerge.userservice.dto.UpdateUserRequest;
import org.sumerge.userservice.dto.UserResponse;
import org.sumerge.userservice.entity.User;
import org.sumerge.userservice.repository.UserRepository;
import org.sumerge.userservice.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        UUID userId = UUID.randomUUID();
        CreateUserRequest request = new CreateUserRequest("John Doe", "john@example.com");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(userId);
            return user;
        });

        UserResponse response = userService.createUser(request);

        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(User.Role.EMPLOYEE, response.getRole());
        assertEquals(userId, response.getId());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_success() {
        List<User> mockUsers = List.of(
                User.builder().id(UUID.randomUUID()).name("User 1").email("u1@mail.com").role(User.Role.EMPLOYEE).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                User.builder().id(UUID.randomUUID()).name("User 2").email("u2@mail.com").role(User.Role.MANAGER).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build()
        );

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserResponse> responses = userService.getAllUsers();

        assertEquals(2, responses.size());
        assertEquals("User 1", responses.get(0).getName());
        assertEquals("User 2", responses.get(1).getName());
    }

    @Test
    void getUserById_success() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("John Doe")
                .email("John@mail.com")
                .role(User.Role.EMPLOYEE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(id);

        assertEquals("John Doe", response.getName());
        assertEquals(id, response.getId());
    }

    @Test
    void getUserById_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(id);
        });
    }

    @Test
    void updateUser_success() {
        UUID id = UUID.randomUUID();
        User existingUser = User.builder()
                .id(id)
                .name("Old Name")
                .email("old@mail.com")
                .role(User.Role.EMPLOYEE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        UpdateUserRequest request = new UpdateUserRequest("New Name", "new@mail.com", User.Role.MANAGER,null, null);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.updateUser(id, request);

        assertEquals("New Name", response.getName());
        assertEquals("new@mail.com", response.getEmail());
        assertEquals(User.Role.MANAGER, response.getRole());
    }

    @Test
    void getUserByEmail_success() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("John Doe")
                .email("John@mail.com")
                .role(User.Role.EMPLOYEE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userRepository.findByEmail("John@mail.com")).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserByEmail("John@mail.com");

        assertEquals("John Doe", response.getName());
        assertEquals("John@mail.com", response.getEmail());
    }

    @Test
    void getUserByEmail_notFound_shouldThrow() {
        when(userRepository.findByEmail("John@mail.com")).thenReturn(Optional.empty());

        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, () -> {
            userService.getUserByEmail("John@mail.com");
        });
    }

    @Test
    void getUsersByManagerId_success() {
        UUID managerId = UUID.randomUUID();
        List<User> mockUsers = List.of(
                User.builder().id(UUID.randomUUID()).name("Emp 1").email("e1@mail.com").role(User.Role.EMPLOYEE).build(),
                User.builder().id(UUID.randomUUID()).name("Emp 2").email("e2@mail.com").role(User.Role.EMPLOYEE).build()
        );

        when(userRepository.findByManager_Id(managerId)).thenReturn(mockUsers);

        List<UserResponse> responses = userService.getUsersByManagerId(managerId);

        assertEquals(2, responses.size());
        assertEquals("Emp 1", responses.get(0).getName());
        assertEquals("Emp 2", responses.get(1).getName());
    }

    @Test
    void consumeKafkaMessage_shouldSaveUser() {
        UUID generatedId = UUID.randomUUID();
        CreateUserRequest request = new CreateUserRequest("Kafka User", "kafka@mail.com");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(generatedId);
            return user;
        });

        userService.consume(request);

        verify(userRepository, times(1)).save(any(User.class));
    }
}

