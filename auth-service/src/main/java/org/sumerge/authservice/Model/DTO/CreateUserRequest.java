package org.sumerge.authservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    private UUID id;
    private String name;
    private String email;
    private String token;
}
