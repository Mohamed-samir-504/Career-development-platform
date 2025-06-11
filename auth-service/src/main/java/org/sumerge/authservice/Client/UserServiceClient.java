package org.sumerge.authservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sumerge.authservice.Common.ApiResponse;
import org.sumerge.authservice.Model.DTO.CreateUserRequest;

@FeignClient(
        name = "UserServiceClient",
        url = "${userService.url}"
)
public interface UserServiceClient {

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> createUser(@RequestBody CreateUserRequest requestBody);

}