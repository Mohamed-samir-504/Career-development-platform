package org.sumerge.authservice.Model.DTO;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

