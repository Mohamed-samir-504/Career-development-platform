package org.sumerge.authservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.sumerge.authservice.Model.DTO.*;
import org.sumerge.authservice.Model.UserAccount;
import org.sumerge.authservice.Service.AuthService;
import org.sumerge.authservice.Repository.UserAccountRepository;
import org.sumerge.shared.utils.JwtUtil;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private KafkaTemplate<String, CreateUserRequest> kafkaTemplate;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void signup_successful() {

        SignupRequest request = new SignupRequest("User Name", "user@example.com", "123456");

        when(userAccountRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hashedPass");

        UserAccount savedUser = UserAccount.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .passwordHash("hashedPass")
                .build();

        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(savedUser);
        when(jwtUtil.generateToken(request.getEmail())).thenReturn("fake-jwt");

        SignupResponse response = authService.signup(request);

        assertEquals(request.getEmail(), response.getEmail());
        assertEquals("fake-jwt", response.getToken());

        verify(kafkaTemplate, times(1)).send(eq("user-create"), any(CreateUserRequest.class));
    }

    @Test
    void signup_emailAlreadyExists_shouldThrow() {
        SignupRequest request = new SignupRequest("User Name", "user@example.com", "123456");
        when(userAccountRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new UserAccount()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.signup(request);
        });

        assertEquals("Email is already registered.", exception.getMessage());
    }

    @Test
    void signup_kafkaSuccess_shouldSendCreateUserRequest() {

        SignupRequest request = new SignupRequest("User Name", "user@example.com", "123456");

        when(userAccountRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("hashedPassword");

        UUID uuid = UUID.randomUUID();

        //simulate JPA setting the ID during save
        when(userAccountRepository.save(any(UserAccount.class))).thenAnswer(invocation -> {
            UserAccount user = invocation.getArgument(0);
            user.setId(uuid);
            return user;
        });

        when(jwtUtil.generateToken(request.getEmail()))
                .thenReturn("jwt-token");

        SignupResponse response = authService.signup(request);

        assertEquals("user@example.com", response.getEmail());
        assertEquals("jwt-token", response.getToken());
        assertNotNull(response.getId());

        ArgumentCaptor<CreateUserRequest> captor = ArgumentCaptor.forClass(CreateUserRequest.class);
        verify(kafkaTemplate, times(1)).send(eq("user-create"), captor.capture());

        CreateUserRequest sentMessage = captor.getValue();
        assertEquals(response.getId(), sentMessage.getId());
        assertEquals("User Name", sentMessage.getName());
        assertEquals("user@example.com", sentMessage.getEmail());
        assertEquals("jwt-token", sentMessage.getToken());
    }


    @Test
    void signup_kafkaFails_shouldRollbackAndThrow() {
        SignupRequest request = new SignupRequest("User Name", "user@example.com", "123456");

        when(userAccountRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hashedPass");

        UUID uuid = UUID.randomUUID();
        UserAccount savedUser = UserAccount.builder()
                .id(uuid)
                .email(request.getEmail())
                .passwordHash("hashedPass")
                .build();

        when(userAccountRepository.save(any(UserAccount.class))).thenAnswer(invocation -> {
            UserAccount user = invocation.getArgument(0);
            user.setId(uuid);
            return user;
        });
        when(jwtUtil.generateToken(request.getEmail())).thenReturn("jwt");

        doThrow(new RuntimeException("Kafka error")).when(kafkaTemplate).send(anyString(), any());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.signup(request);
        });

        assertTrue(exception.getMessage().contains("Failed to send message to Kafka"));
        verify(userAccountRepository).deleteById(savedUser.getId());

    }


    @Test
    void login_successful() {
        LoginRequest request = new LoginRequest("user@example.com", "123456");

        UserAccount user = UserAccount.builder()
                .id(UUID.randomUUID())
                .email("user@example.com")
                .passwordHash("hashedPass")
                .build();

        when(userAccountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "hashedPass")).thenReturn(true);
        when(jwtUtil.generateToken("user@example.com")).thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertEquals("user@example.com", response.getEmail());
        assertEquals("jwt-token", response.getToken());
        assertEquals(user.getId(), response.getId());
    }

    @Test
    void login_invalidEmail_shouldThrow() {
        LoginRequest request = new LoginRequest("notfound@example.com", "pass");
        when(userAccountRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void login_invalidPassword_shouldThrow() {
        LoginRequest request = new LoginRequest("user@example.com", "wrongpass");

        UserAccount user = UserAccount.builder()
                .email("user@example.com")
                .passwordHash("correctHash")
                .build();

        when(userAccountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "correctHash")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("Invalid email or password", ex.getMessage());
    }
}
