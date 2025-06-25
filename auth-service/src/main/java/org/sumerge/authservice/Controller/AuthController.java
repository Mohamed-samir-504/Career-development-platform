package org.sumerge.authservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.authservice.Common.ApiResponse;
import org.sumerge.authservice.Model.DTO.LoginRequest;
import org.sumerge.authservice.Model.DTO.LoginResponse;
import org.sumerge.authservice.Model.DTO.SignupRequest;
import org.sumerge.authservice.Model.DTO.SignupResponse;
import org.sumerge.authservice.Service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse responseData = authService.login(request);
            ApiResponse<LoginResponse> response = ApiResponse.success("Login successful", 200, responseData);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<LoginResponse> error = ApiResponse.error(e.getMessage(), 401);
            return ResponseEntity.status(401).body(error);

        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse data = authService.signup(request);

            return ResponseEntity.status(201).body(
                    ApiResponse.success("User registered successfully",
                    201,
                            data));

        } catch (RuntimeException e) {
            ApiResponse<SignupResponse> error = ApiResponse.error(e.getMessage(), 400);
            return ResponseEntity.badRequest().body(error);
        }
    }


}
