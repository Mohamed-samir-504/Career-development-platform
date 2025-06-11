package org.sumerge.userservice.dto;

import lombok.Builder;
import lombok.Data;
import org.sumerge.userservice.entity.User;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private User.Role role;
    private UUID managerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
