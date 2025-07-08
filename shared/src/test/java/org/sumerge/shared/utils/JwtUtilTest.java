package org.sumerge.shared.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();

        Field secretField = JwtUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, "supersecretkey12345678901234567890");

        Field expirationField = JwtUtil.class.getDeclaredField("expiration");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, 3600000L); // 1 hour
    }

    @Test
    void testGenerateAndValidateToken() {
        String email = "user@example.com";
        String token = jwtUtil.generateToken(email);

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
        assertEquals(email, jwtUtil.extractEmail(token));
    }

    @Test
    void testInvalidTokenShouldFailValidation() {
        String invalidToken = "invalid.jwt.token";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    void testExtractEmailFromValidToken() {
        String email = "test@domain.com";
        String token = jwtUtil.generateToken(email);

        String extracted = jwtUtil.extractEmail(token);
        assertEquals(email, extracted);
    }
}
